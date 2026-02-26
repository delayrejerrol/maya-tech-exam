package com.jerrol.sendmoneyapp.di

import com.jerrol.sendmoneyapp.data.transaction.TransactionRepositoryImpl
import com.jerrol.sendmoneyapp.domain.transaction.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository
}
