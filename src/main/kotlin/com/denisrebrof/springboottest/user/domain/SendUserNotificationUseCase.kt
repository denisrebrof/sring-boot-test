package com.denisrebrof.springboottest.user.domain

import com.denisrebrof.springboottest.commands.domain.model.Notification
import com.denisrebrof.springboottest.commands.domain.model.NotificationContent
import com.denisrebrof.springboottest.commands.gateways.WSNotificationService
import com.denisrebrof.springboottest.user.domain.repositories.IWSUserSessionRepository
import com.denisrebrof.springboottest.user.domain.repositories.IWSUserSessionRepository.UserSessionState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.safeCast

@Service
class SendUserNotificationUseCase @Autowired constructor(
    private val notificationService: WSNotificationService,
    private val userSessionRepository: IWSUserSessionRepository
) {
    fun send(
        userId: Long,
        commandId: Long,
        content: NotificationContent
    ) {
        val sessionId = userSessionRepository
            .getUserSession(userId)
            .let(UserSessionState.Exists::class::safeCast)
            ?.session
            ?.id
            ?: return

        Notification(sessionId, commandId, content).let(notificationService::send)
    }
}