package com.jerrol.sendmoneyapp.domain.auth.model

sealed interface LoginResult {
    data class Success(val user: AuthUser) : LoginResult
    data class Error(val message: String) : LoginResult
}
