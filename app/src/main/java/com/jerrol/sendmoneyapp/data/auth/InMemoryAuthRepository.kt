package com.jerrol.sendmoneyapp.data.auth

import com.jerrol.sendmoneyapp.domain.auth.model.AuthUser
import com.jerrol.sendmoneyapp.domain.auth.model.LoginResult
import com.jerrol.sendmoneyapp.domain.auth.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryAuthRepository @Inject constructor() : AuthRepository {

    private var currentUser: AuthUser? = null

    override suspend fun login(username: String, password: String): LoginResult {
        val normalizedUsername = username.trim()
        val normalizedPassword = password.trim()

        return if (normalizedUsername == DEMO_USERNAME && normalizedPassword == DEMO_PASSWORD) {
            val user = AuthUser(normalizedUsername)
            currentUser = user
            LoginResult.Success(user)
        } else {
            LoginResult.Error("Invalid username or password.")
        }
    }

    override fun logout() {
        currentUser = null
    }

    override fun isLoggedIn(): Boolean {
        return currentUser != null
    }

    override fun currentUsername(): String? {
        return currentUser?.username
    }

    companion object {
        const val DEMO_USERNAME = "demo"
        const val DEMO_PASSWORD = "password123"
    }
}
