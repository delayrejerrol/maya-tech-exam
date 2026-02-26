package com.jerrol.sendmoneyapp.di

import com.jerrol.sendmoneyapp.data.auth.InMemoryAuthRepository
import com.jerrol.sendmoneyapp.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        inMemoryAuthRepository: InMemoryAuthRepository
    ): AuthRepository
}
