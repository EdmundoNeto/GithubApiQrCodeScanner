package com.edmundo.domain.model

data class User (
    val id: Int,
    val name: String,
    val avatar_url: String,
    val public_repos: Int,
    val login: String
)