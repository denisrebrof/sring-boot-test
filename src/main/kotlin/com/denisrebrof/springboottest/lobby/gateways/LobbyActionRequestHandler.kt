package com.denisrebrof.springboottest.lobby.gateways

import com.denisrebrof.springboottest.commands.domain.model.WSCommand
import com.denisrebrof.springboottest.commands.domain.model.ResponseState
import com.denisrebrof.springboottest.lobby.domain.LobbyRepository
import com.denisrebrof.springboottest.user.gateways.WSUserRequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LobbyActionRequestHandler @Autowired constructor(
    private val lobbyRepository: LobbyRepository
) : WSUserRequestHandler<LobbyActionRequestHandler.Intent>(WSCommand.LobbyAction.id) {

    override fun parseData(data: String): Intent {
        val intentCode = data.toLong()
        return Intent.values().first { it.code == intentCode }
    }

    override fun handleMessage(userId: Long, data: Intent): ResponseState {
        when (data) {
            Intent.Join -> lobbyRepository.add(userId)
            Intent.Leave -> lobbyRepository.remove(userId)
        }
        return ResponseState.NoResponse
    }

    enum class Intent(val code: Long) {
        Join(0L),
        Leave(1L)
    }
}