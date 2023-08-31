package com.denisrebrof.springboottest.user.domain.model

sealed class LoginResult {
    data class Success(val token: String, val userId: Long) : LoginResult()
    object Failed : LoginResult()
}