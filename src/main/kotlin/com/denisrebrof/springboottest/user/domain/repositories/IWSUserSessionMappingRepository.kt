package com.denisrebrof.springboottest.user.domain.repositories

interface IWSUserSessionMappingRepository {
    fun getMapping(sessionId: String): Long?
    fun addMapping(userId: Long, sessionId: String)
    fun removeMapping(sessionId: String)
}