package com.jerrol.sendmoneyapp.data.transaction.remote.dto

data class TransactionPostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
