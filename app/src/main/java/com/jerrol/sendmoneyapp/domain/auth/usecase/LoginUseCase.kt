package com.jerrol.sendmoneyapp.domain.auth.usecase

import com.jerrol.sendmoneyapp.domain.auth.model.LoginResult
import com.jerrol.sendmoneyapp.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): LoginResult {
        return authRepository.login(username, password)
    }
}
