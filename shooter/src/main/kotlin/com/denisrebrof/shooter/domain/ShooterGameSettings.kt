package com.denisrebrof.shooter.domain

import com.denisrebrof.games.Transform

data class ShooterGameSettings(
    val redTeamSpawnPos: List<Transform> = listOf(
        Transform(16f, 2f, 16f, 180f),
        Transform(5f, 2f, 5f, 180f),
    ),

    val blueTeamSpawnPos: List<Transform> = listOf(
        Transform(17f, 2f, -45f, 0f),
        Transform(32f, -2f, -32f, 0f),
    ),
    val respawnDelay: Long,
    val prepareDelay: Long,
    val gameDuration: Long,
    val completeDelay: Long,
)