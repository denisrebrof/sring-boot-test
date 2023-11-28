package com.denisrebrof.matches.domain.model

data class Match(
    val id: String,
    val participants: Set<Long>,
    val createdTime: Long,
)