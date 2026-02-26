package com.jerrol.sendmoneyapp.presentation.auth

import com.jerrol.sendmoneyapp.domain.transaction.model.Transaction
import com.jerrol.sendmoneyapp.domain.transaction.repository.TransactionRepository

class FakeTransactionRepository : TransactionRepository {
    private val transactions = mutableListOf<Transaction>()
    var failCreate = false
    var failGet = false
    private var nextId = 1

    override suspend fun getTransactions(username: String): Result<List<Transaction>> {
        return if (failGet) {
            Result.failure(IllegalStateException("get failed"))
        } else {
            Result.success(transactions.toList())
        }
    }

    override suspend fun createTransaction(username: String, amount: Double): Result<Transaction> {
        return if (failCreate) {
            Result.failure(IllegalStateException("create failed"))
        } else {
            val transaction = Transaction(
                id = nextId++,
                username = username,
                amount = amount,
                source = "created"
            )
            transactions.add(0, transaction)
            Result.success(transaction)
        }
    }
}
