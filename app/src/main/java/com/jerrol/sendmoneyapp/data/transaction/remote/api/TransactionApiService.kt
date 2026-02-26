package com.jerrol.sendmoneyapp.data.transaction.remote.api

import com.jerrol.sendmoneyapp.data.transaction.remote.dto.CreateTransactionRequestDto
import com.jerrol.sendmoneyapp.data.transaction.remote.dto.TransactionPostDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApiService {
    @GET("posts")
    suspend fun getTransactions(): List<TransactionPostDto>

    @POST("posts")
    suspend fun createTransaction(
        @Body body: CreateTransactionRequestDto
    ): TransactionPostDto
}
