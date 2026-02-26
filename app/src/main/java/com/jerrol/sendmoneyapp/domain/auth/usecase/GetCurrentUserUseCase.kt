package com.jerrol.sendmoneyapp.domain.auth.usecase

import com.jerrol.sendmoneyapp.domain.auth.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): String? {
        return authRepository.currentUsername()
    }
}
