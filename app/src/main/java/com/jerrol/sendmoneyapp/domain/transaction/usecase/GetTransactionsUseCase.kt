package com.jerrol.sendmoneyapp.domain.transaction.usecase

import com.jerrol.sendmoneyapp.domain.transaction.model.Transaction
import com.jerrol.sendmoneyapp.domain.transaction.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(username: String): Result<List<Transaction>> {
        return transactionRepository.getTransactions(username)
    }
}
