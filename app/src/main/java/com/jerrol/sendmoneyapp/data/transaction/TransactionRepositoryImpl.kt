package com.jerrol.sendmoneyapp.data.transaction

import com.jerrol.sendmoneyapp.data.transaction.remote.api.TransactionApiService
import com.jerrol.sendmoneyapp.data.transaction.remote.dto.CreateTransactionRequestDto
import com.jerrol.sendmoneyapp.domain.transaction.model.Transaction
import com.jerrol.sendmoneyapp.domain.transaction.repository.TransactionRepository
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionApiService: TransactionApiService
) : TransactionRepository {

    private val createdTransactions = mutableListOf<Transaction>()

    override suspend fun getTransactions(username: String): Result<List<Transaction>> {
        return runCatching {
            val remoteTransactions = try {
                transactionApiService.getTransactions()
                    .take(15)
                    .map { post ->
                        val amount = ((post.id % 20) + 1) * 10.0
                        Transaction(
                            id = post.id,
                            username = "user-${post.userId}",
                            amount = amount,
                            source = "remote"
                        )
                    }
            } catch (_: Exception) {
                emptyList()
            }

            (createdTransactions + remoteTransactions)
                .sortedByDescending { it.id }
        }
    }

    override suspend fun createTransaction(username: String, amount: Double): Result<Transaction> {
        return runCatching {
            val requestBody = CreateTransactionRequestDto(
                userId = 1,
                title = "send_money:$username",
                body = String.format(Locale.US, "%.2f", amount)
            )
            val response = transactionApiService.createTransaction(requestBody)

            val transaction = Transaction(
                id = response.id,
                username = username,
                amount = amount,
                source = "created"
            )
            createdTransactions.add(0, transaction)
            transaction
        }
    }
}
