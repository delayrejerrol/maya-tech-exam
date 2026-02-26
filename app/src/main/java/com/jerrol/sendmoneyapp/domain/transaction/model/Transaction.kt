package com.jerrol.sendmoneyapp.domain.transaction.model

data class Transaction(
    val id: Int,
    val username: String,
    val amount: Double,
    val source: String
)
