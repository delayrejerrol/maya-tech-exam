package com.jerrol.sendmoneyapp.domain.transaction.repository

import com.jerrol.sendmoneyapp.domain.transaction.model.Transaction

interface TransactionRepository {
    suspend fun getTransactions(username: String): Result<List<Transaction>>
    suspend fun createTransaction(username: String, amount: Double): Result<Transaction>
}
