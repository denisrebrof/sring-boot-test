package com.denisrebrof.shooter.domain.model

import arrow.optics.Optional
import arrow.optics.dsl.index
import arrow.optics.optics
import arrow.optics.typeclasses.Index

@optics
sealed interface ShooterGameState {
    companion object
}

@optics
data class Preparing(
    val pendingPlayers: Map<Long, ShooterPlayerData>,
) : ShooterGameState {
    companion object
}

@optics
data class PlayingState(
    val players: Map<Long, ShooterPlayerState>,
    val bots: Map<Long, ShooterBotState>,
    val teamData: Map<PlayerTeam, TeamPlayingData>,
    val startTime: Long = System.currentTimeMillis(),
) : ShooterGameState {

    val participantIds: Set<Long>
        get() = players.keys + bots.keys

    val botStates: Map<Long, ShooterPlayerState>
        get() = bots.mapValues { (_, data) -> data.playerState }

    val participants: Map<Long, ShooterPlayerState>
        get() = players + botStates

    companion object {
        fun getPlayerStateOptional(playerId: Long): Optional<PlayingState, ShooterPlayerState> = when {
            playerId > 0 -> PlayingState
                .players
                .index(Index.map(), playerId)

            else -> getBotStateOptional(playerId).playerState
        }

        fun getBotStateOptional(playerId: Long): Optional<PlayingState, ShooterBotState> = PlayingState
            .bots
            .index(Index.map(), playerId)
    }
}

@optics
data class TeamPlayingData(
    val lastSpawnIndex: Int,
    val kills: Int = 0,
) {
    companion object
}

@optics
data class Finished(
    val finishedPlayers: Map<Long, ShooterPlayerData>,
    val finishedBots: Map<Long, ShooterPlayerData>,
    val winnerTeam: PlayerTeam,
    val teamKills: Map<PlayerTeam, Int>,
) : ShooterGameState {
    companion object
}

val ShooterGameState.participantIds: Set<Long>
    get() = when (this) {
        is Finished -> finishedPlayers.keys.plus(finishedBots.keys)
        is PlayingState -> players.keys.plus(bots.keys)
        is Preparing -> pendingPlayers.keys
    }

val ShooterGameState.playerIds: Set<Long>
    get() = when (this) {
        is Finished -> finishedPlayers.keys
        is PlayingState -> players.keys
        is Preparing -> pendingPlayers.keys
    }

enum class GameStateTypeResponse(val code: Long) {
    Preparing(1L),
    Playing(2L),
    Finished(3L),
}

val ShooterGameState.responseType: GameStateTypeResponse
    get() = when (this) {
        is Preparing -> GameStateTypeResponse.Preparing
        is PlayingState -> GameStateTypeResponse.Playing
        is Finished -> GameStateTypeResponse.Finished
    }