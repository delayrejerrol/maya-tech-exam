package com.jerrol.sendmoneyapp.domain.auth.usecase

import com.jerrol.sendmoneyapp.domain.auth.repository.AuthRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.isLoggedIn()
    }
}
