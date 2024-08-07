package com.denisrebrof.shooter.domain.usecases

import com.denisrebrof.shooter.domain.game.ShooterGame
import com.denisrebrof.shooter.domain.model.ShooterGameSettings
import com.denisrebrof.shooter.domain.model.playerIds
import com.denisrebrof.utils.subscribeDefault
import io.reactivex.rxjava3.core.Flowable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CreateShooterGameUseCase @Autowired constructor(
    private val notificationsUseCase: ShooterGameNotificationsUseCase,
) {

    private val stateSyncDelayMs = 200L

    fun create(
        playerIds: List<Long>,
        settings: ShooterGameSettings
    ) = ShooterGame
        .create(playerIds, settings)
        .also(::createStateHandler)
        .also(::createActionsHandler)
        .also(ShooterGame::start)

    private fun createStateHandler(game: ShooterGame) = Flowable
        .interval(stateSyncDelayMs, TimeUnit.MILLISECONDS)
        .startWithItem(0L)
        .map { game.state }
        .subscribeDefault(notificationsUseCase::notifyStateChanged)
        .let(game::add)

    private fun createActionsHandler(game: ShooterGame) = game
        .actions
        .subscribeDefault { action ->
            val receivers = game.state.playerIds.toLongArray()
            notificationsUseCase.notifyAction(action, *receivers)
        }.let(game::add)
}