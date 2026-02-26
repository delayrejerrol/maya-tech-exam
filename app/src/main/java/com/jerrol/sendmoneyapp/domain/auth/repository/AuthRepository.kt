package com.jerrol.sendmoneyapp.domain.auth.repository

import com.jerrol.sendmoneyapp.domain.auth.model.LoginResult

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResult
    fun logout()
    fun isLoggedIn(): Boolean
    fun currentUsername(): String?
}
