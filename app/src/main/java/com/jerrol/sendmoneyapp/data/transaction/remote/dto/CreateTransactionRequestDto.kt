package com.jerrol.sendmoneyapp.data.transaction.remote.dto

data class CreateTransactionRequestDto(
    val userId: Int,
    val title: String,
    val body: String
)
