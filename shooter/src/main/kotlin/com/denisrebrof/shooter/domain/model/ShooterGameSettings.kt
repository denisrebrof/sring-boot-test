package com.denisrebrof.shooter.domain.model

import com.denisrebrof.games.Transform
import com.denisrebrof.shooter.domain.model.PlayerTeam.Blue
import com.denisrebrof.shooter.domain.model.PlayerTeam.Red

data class ShooterGameSettings(
    private val redTeamSpawnPos: List<Transform> = listOf(
        Transform(-40f, -12f, 24.5f, 180f),
        Transform(-40f, -12f, 25f, 180f),
        Transform(-40f, -12f, 25.5f, 180f),
        Transform(-40f, -12f, 26f, 180f),
        Transform(-40f, -12f, 26.5f, 180f),
        Transform(-40f, -12f, 27f, 180f),
        Transform(-40f, -12f, 27.5f, 180f),
        Transform(-40f, -12f, 28f, 180f),
    ),

    private val blueTeamSpawnPos: List<Transform> = listOf(
        Transform(33f, -12f, 27.5f, 0f),
        Transform(33f, -12f, 28f, 0f),
        Transform(33f, -12f, 28.5f, 0f),
        Transform(33f, -12f, 29f, 0f),
        Transform(33f, -12f, 29.5f, 0f),
        Transform(33f, -12f, 30f, 0f),
        Transform(33f, -12f, 30.5f, 0f),
        Transform(33f, -12f, 31f, 0f),
    ),
    val botSettings: BotSettings,
    val defaultHp: Int,
    val respawnDelay: Long,
    val prepareDelay: Long,
    val gameDuration: Long,
    val completeDelay: Long,
) {

    fun getSpawnPos(team: PlayerTeam) = when (team) {
        Red -> redTeamSpawnPos
        Blue -> blueTeamSpawnPos
    }

    data class BotSettings(
        val botSpeed: Float = 4f,
        val botReachWaypointDist: Float = 0.5f,
        val botUpdateLoopDelayMs: Long = 100L,
        val visibilityMaskBufferingDelay: Long = 1000L,
        val shootingMaxRange: Float = 10f,
        val defaultWeaponId: Long = 1L,
        val fillWithBotsToTeamSize: Int = 0,
        val redTeamRoutes: List<List<Transform>> = listOf(
            listOf(
                Transform(-40f, -12f, 25f, 0f),
                Transform(-35.72f, -12f, 17.55f, 0f),
                Transform(-28.73f, -12f, 15.22f, 0f),
                Transform(-21.82f, -12f, 12.21f, 0f),
                Transform(-14.75f, -12f, 10.11f, 0f),
                Transform(-7.74f, -12f, 11.79f, 0f),
                Transform(-2.42f, -12f, 13.75f, 0f),
                Transform(1.07f, -12f, 14.36f, 0f),
                Transform(6.85f, -12f, 11.72f, 0f),
                Transform(12.34f, -12f, 11.06f, 0f),
                Transform(20.83f, -12f, 10.21f, 0f),
                Transform(27.78f, -12f, 10.85f, 0f),
                Transform(29.73f, -12.013f, 16.28f, 0f),
            ),
            listOf(
                Transform(-40f, -12f, 26f, 0f),
                Transform(-33.72f, -12f, 25.61f, 0f),
                Transform(-27.96f, -12f, 25.27f, 0f),
                Transform(-20.61f, -12f, 26.01f, 0f),
                Transform(-18.2f, -12f, 23.14f, 0f),
                Transform(-17.62f, -12f, 19.87f, 0f),
                Transform(-10.43f, -12f, 18.68f, 0f),
                Transform(-5.58f, -12f, 16.92f, 0f),
                Transform(-0.97f, -12f, 20.3f, 0f),
                Transform(5.68f, -12f, 22.19f, 0f),
                Transform(11.74f, -12f, 21.35f, 0f),
                Transform(17.18f, -12f, 22.73f, 0f),
                Transform(22.18f, -12f, 28.63f, 0f),
            ),
            listOf(
                Transform(-40f, -12f, 27f, 0f),
                Transform(-33.65f, -12f, 30.7f, 0f),
                Transform(-26.61f, -12f, 33.15f, 0f),
                Transform(-22.32f, -12f, 36.29f, 0f),
                Transform(-16.25f, -12f, 42.4f, 0f),
                Transform(-12.07f, -12f, 44.54f, 0f),
                Transform(-7.16f, -12f, 45.07f, 0f),
                Transform(-2.82f, -12f, 45.02f, 0f),
                Transform(1.75f, -12f, 45.39f, 0f),
                Transform(7.87f, -12f, 48.87f, 0f),
                Transform(15.16f, -12f, 43.68f, 0f),
                Transform(21.19f, -12f, 40.56f, 0f),
                Transform(24.29f, -12f, 35.4f, 0f),
            ),
            listOf(
                Transform(-40f, -12f, 28f, 0f),
                Transform(-39.35f, -12f, 39.12f, 0f),
                Transform(-30.7f, -12f, 43.18f, 0f),
                Transform(-22.5f, -12f, 40.32f, 0f),
                Transform(-18.39f, -12f, 40.99f, 0f),
                Transform(-15.08f, -12f, 48.09f, 0f),
                Transform(-8.69f, -12f, 48.88f, 0f),
                Transform(-1.55f, -12f, 47.97f, 0f),
                Transform(0.89f, -12f, 46.83f, 0f),
                Transform(3.14f, -12f, 43.44f, 0f),
                Transform(7.25f, -12f, 36.54f, 0f),
                Transform(11.54f, -12f, 30.53f, 0f),
                Transform(12.5f, -12f, 30.35f, 0f),
                Transform(13.59f, -12f, 31.87f, 0f),
                Transform(17.65f, -12f, 35.61f, 0f),
                Transform(21.59f, -12f, 35.22f, 0f),
                Transform(24.48f, -12f, 33.07f, 0f),
            ),
        ),
        val blueTeamRoutes: List<List<Transform>> = listOf(
            listOf(
                Transform(33f, -12f, 28f, 0f),
                Transform(32.3f, -12f, 22.78f, 0f),
                Transform(29.12f, -12f, 17.06f, 0f),
                Transform(26.98f, -12f, 11.41f, 0f),
                Transform(24.49f, -12f, 8.86f, 0f),
                Transform(20.88f, -12f, 8.58f, 0f),
                Transform(16.75f, -12f, 10.91f, 0f),
                Transform(11.56f, -12f, 10.05f, 0f),
                Transform(8.11f, -12f, 6.97f, 0f),
                Transform(3.5f, -12f, 10.11f, 0f),
                Transform(-4.66f, -12f, 9.85f, 0f),
                Transform(-12.38f, -12f, 9.8f, 0f),
                Transform(-17.37f, -12f, 10.1f, 0f),
                Transform(-19.95f, -12f, 12.56f, 0f),
                Transform(-26.24f, -12f, 14.9f, 0f),
                Transform(-31.31f, -12f, 16.63f, 0f),
                Transform(-27.05f, -12f, 21.88f, 0f),
                Transform(-26.03f, -12f, 25.72f, 0f),
                Transform(-29.72f, -12f, 25.85f, 0f),
                Transform(-34.97f, -12f, 25.31f, 0f),
            ),
            listOf(
                Transform(33f, -12f, 29f, 0f),
                Transform(28.81f, -12f, 30.55f, 0f),
                Transform(24.04f, -12f, 29.69f, 0f),
                Transform(20.05f, -12f, 24.39f, 0f),
                Transform(16.62f, -12f, 24.62f, 0f),
                Transform(13.11f, -12f, 27.23f, 0f),
                Transform(10.8f, -12f, 27.58f, 0f),
                Transform(8.16f, -12f, 24.88f, 0f),
                Transform(4.94f, -12f, 19.3f, 0f),
                Transform(-0.01f, -12f, 21.74f, 0f),
                Transform(-2.06f, -12f, 22.98f, 0f),
                Transform(-4.91f, -12f, 21.27f, 0f),
                Transform(-7.91f, -12f, 18.62f, 0f),
                Transform(-9.98f, -12f, 19.06f, 0f),
                Transform(-10.26f, -12f, 24.49f, 0f),
                Transform(-10.34f, -12f, 30.82f, 0f),
                Transform(-10.07f, -12f, 34.59f, 0f),
                Transform(-14.18f, -12f, 35.9f, 0f),
                Transform(-16.86f, -12f, 34.46f, 0f),
                Transform(-17.07f, -12f, 29.14f, 0f),
                Transform(-18.44f, -12f, 26.5f, 0f),
                Transform(-21.49f, -12f, 26.59f, 0f),
                Transform(-24.05f, -12f, 27.96f, 0f),
                Transform(-26.53f, -12f, 29.9f, 0f),
                Transform(-29.5f, -12f, 29.96f, 0f),
            ),
            listOf(
                Transform(33f, -12f, 30f, 0f),
                Transform(29.33f, -12f, 30.85f, 0f),
                Transform(25.82f, -12f, 32.06f, 0f),
                Transform(23.18f, -12f, 35.59f, 0f),
                Transform(22.14f, -12f, 39.96f, 0f),
                Transform(18.07f, -12f, 41.87f, 0f),
                Transform(13.6f, -12f, 42.5f, 0f),
                Transform(9.54f, -12f, 39.79f, 0f),
                Transform(4.69f, -12f, 39.1f, 0f),
                Transform(3.38f, -12f, 42.39f, 0f),
                Transform(1.97f, -12f, 44.39f, 0f),
                Transform(-1.21f, -12f, 43.25f, 0f),
                Transform(-6.08f, -12f, 38.21f, 0f),
                Transform(-10.63f, -12f, 35.3f, 0f),
                Transform(-14.39f, -12f, 37f, 0f),
                Transform(-19.25f, -12f, 37.25f, 0f),
                Transform(-20.86f, -12f, 37.04f, 0f),
                Transform(-23.43f, -12f, 35.16f, 0f),
                Transform(-25.91f, -12f, 35.66f, 0f),
                Transform(-26.894f, -12f, 39.819f, 0f),
                Transform(-28.18f, -12f, 41.67f, 0f),
                Transform(-31.84f, -12f, 41.32f, 0f),
                Transform(-34.99f, -12f, 38.87f, 0f),
            ),
            listOf(
                Transform(33f, -12f, 31f, 0f),
                Transform(31.39f, -12f, 35.58f, 0f),
                Transform(28.91f, -12f, 36.09f, 0f),
                Transform(25.31f, -12f, 34.67f, 0f),
                Transform(22.67f, -12f, 36.04f, 0f),
                Transform(20.25f, -12f, 39.96f, 0f),
                Transform(15.95f, -12f, 42.36f, 0f),
                Transform(10.92f, -12f, 45.84f, 0f),
                Transform(7.18f, -12f, 45.77f, 0f),
                Transform(2.42f, -12f, 46.63f, 0f),
                Transform(-1.31f, -12f, 47.61f, 0f),
                Transform(-7.32f, -12f, 47.85f, 0f),
                Transform(-13.19f, -12f, 48.59f, 0f),
                Transform(-15.83f, -12f, 45.39f, 0f),
                Transform(-19.38f, -12f, 41.64f, 0f),
                Transform(-22.7f, -12f, 40.66f, 0f),
                Transform(-27.98f, -12f, 42.36f, 0f),
                Transform(-34.02f, -12f, 43.08f, 0f),
                Transform(-36.06f, -12f, 40.17f, 0f),
            ),
        )

    ) {
        fun findCloseRouteIndex(origin: Transform, team: PlayerTeam): Int? = when (team) {
            Red -> redTeamRoutes.findCloseRouteIndex(origin)
            Blue -> blueTeamRoutes.findCloseRouteIndex(origin)
        }

        private fun List<List<Transform>>.findCloseRouteIndex(origin: Transform): Int? {
            if (isEmpty())
                return null

            if (size == 1)
                return 0

            var minDist = Float.MAX_VALUE
            var closestRouteIndex: Int? = null

            forEachIndexed { index, route ->
                val distance = route
                    .firstOrNull()
                    ?.getSquaredDistanceTo(origin)
                    ?: return@forEachIndexed

                if (minDist <= distance)
                    return@forEachIndexed

                minDist = distance
                closestRouteIndex = index
            }

            return closestRouteIndex
        }
    }
}