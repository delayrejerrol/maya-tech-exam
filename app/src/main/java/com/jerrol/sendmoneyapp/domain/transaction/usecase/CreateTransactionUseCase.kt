package com.jerrol.sendmoneyapp.domain.transaction.usecase

import com.jerrol.sendmoneyapp.domain.transaction.model.Transaction
import com.jerrol.sendmoneyapp.domain.transaction.repository.TransactionRepository
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(username: String, amount: Double): Result<Transaction> {
        return transactionRepository.createTransaction(username, amount)
    }
}
