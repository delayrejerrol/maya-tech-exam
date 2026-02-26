package com.jerrol.sendmoneyapp.presentation.transaction

data class TransactionUiItem(
    val id: Int,
    val username: String,
    val amountDisplay: String,
    val source: String
)
