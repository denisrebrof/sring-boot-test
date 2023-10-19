import arrow.optics.dsl.index
import arrow.optics.typeclasses.Index
import io.reactivex.rxjava3.core.Completable
import model.*
import model.PlayerTeam.Blue
import model.PlayerTeam.Red
import model.ShooterGameIntents
import java.util.concurrent.TimeUnit
import model.ShooterGameActions as Action
import model.ShooterGameIntents as Intent
import model.ShooterGameState as State

class ShooterGame private constructor(
    private val settings: ShooterGameSettings,
    players: Map<Long, ShooterPlayerData>
) : MVIGameHandler<State, Intent, Action>(
    initialState = Preparing(players)
) {

    fun start() = stateFlow
        .ofType(Preparing::class.java)
        .firstElement()
        .delay(settings.prepareDelay, TimeUnit.MILLISECONDS)
        .map(::createPlayingState)
        .doAfterSuccess(::setState)
        .delay(settings.gameDuration, TimeUnit.MILLISECONDS)
        .map { state }
        .ofType(PlayingState::class.java)
        .map(::createFinishedState)
        .doAfterSuccess(::setState)
        .delay(settings.completeDelay, TimeUnit.MILLISECONDS)
        .subscribeDefault { send(Action.LifecycleCompleted) }
        .let(::add)

    override fun onIntentReceived(intent: Intent) = when (intent) {
        is Intent.SelectWeapon -> selectWeapon(intent)
        is Intent.Shoot -> shoot(intent)
        is Intent.UpdatePos -> updatePos(intent)
        is ShooterGameIntents.Hit -> hit(intent)
    }

    private fun selectWeapon(intent: Intent.SelectWeapon) = state.copyAndSet {
        getPlayerStateOptional(intent.playerId).selectedWeaponId set intent.weaponId
    }

    private fun hit(intent: Intent.Hit) = state.copyAndSet {
        val shooter = getPlayerStateOptional(intent.shooterId)
        val shooterPlaying = shooter.dynamicState.playing
        if (shooterPlaying.isEmpty(state))
            return@copyAndSet

        val receiverId = intent.receiverId ?: return@copyAndSet
        val receiver = getPlayerStateOptional(receiverId)
        val receiverPlaying = receiver.dynamicState.playing
        val receiverState = receiverPlaying.getOrNull(state) ?: return@copyAndSet
        val newHp = receiverState.hp - intent.damage
        val killed = newHp < 1
        Action.Hit(intent.shooterId, receiverId, intent.damage, killed).let(::send)
        if (!killed)
            return@copyAndSet receiverPlaying.hp set newHp

        shooter.data.kills transform { it + 1 }
        receiver.data.death transform { it + 1 }
        receiver.dynamicState set Killed(receiverState.transform)

        Completable
            .timer(settings.respawnDelay, TimeUnit.MILLISECONDS)
            .doOnComplete { revivePlayer(receiverId) }
            .subscribeDefault()
            .let(::add)
    }

    private fun shoot(intent: Intent.Shoot) = state.copyAndSet {
        val shooter = getPlayerStateOptional(intent.shooterId)
        val shooterPlaying = shooter.dynamicState.playing
        if (shooterPlaying.isEmpty(state))
            return@copyAndSet

        shooter.selectedWeaponId set intent.weaponId
        Action.Shoot(intent.shooterId, intent.weaponId, intent.direction).let(::send)
    }

    private fun revivePlayer(playerId: Long) = state.copyAndSet {
        val playerState = getPlayerStateOptional(playerId)
        val playerTeam = playerState.data.team.getOrNull(state) ?: return@copyAndSet
        getPlayerStateOptional(playerId).dynamicState transform transform@{ dynamicState ->
            if (dynamicState !is Killed)
                return@transform dynamicState

            Playing(
                hp = 100,
                transform = getSpawn(playerTeam),
                verticalLookAngle = 0f
            )
        }
    }

    private fun getSpawn(team: PlayerTeam) = when (team) {
        Red -> settings.redTeamSpawnPos
        Blue -> settings.blueTeamSpawnPos
    }

    private fun updatePos(intent: Intent.UpdatePos) = state.copyAndSet {
        getPlayerPlayingStateOptional(intent.playerId).apply {
            transform set intent.pos
            verticalLookAngle set intent.verticalLookAngle
        }
    }

    private fun getPlayerStateOptional(playerId: Long) = State
        .playingState
        .players
        .index(Index.map(), playerId)

    private fun getPlayerPlayingStateOptional(playerId: Long) = getPlayerStateOptional(playerId)
        .dynamicState
        .playing

    private fun createFinishedState(playing: PlayingState): Finished {
        val players = playing.players.mapValues { (_, data) -> data.data }
        return Finished(
            finishedPlayers = players,
            winnerTeam = players
                .values
                .groupBy { it.team }
                .maxBy { (_, data) -> data.sumOf { it.kills } }
                .key
        )
    }

    private fun createPlayingState(preparing: Preparing) = PlayingState(
        players = preparing.pendingPlayers.mapValues { (_, data) ->
            val dynamicState = Playing(
                100,
                getSpawn(data.team),
                0f
            )
            ShooterPlayerState(data, 0L, dynamicState)
        }
    )

    companion object {
        fun create(
            playerIds: List<Long>,
            settings: ShooterGameSettings
        ): ShooterGame {
            val teams = listOf(Red, Blue)
            val players = playerIds
                .mapIndexed { index, playerId ->
                    val team = teams[index % teams.size]
                    playerId to ShooterPlayerData(team)
                }
                .toMap()
            return ShooterGame(settings, players)

        }
    }
}