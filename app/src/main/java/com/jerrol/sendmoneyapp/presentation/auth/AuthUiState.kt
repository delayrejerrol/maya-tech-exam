package com.jerrol.sendmoneyapp.presentation.auth

import com.jerrol.sendmoneyapp.presentation.transaction.TransactionUiItem

data class AuthUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentUsername: String? = null,
    val walletBalanceAmount: Double = 500.00,
    val walletBalance: String = "500.00",
    val isBalanceVisible: Boolean = true,
    val sendAmountInput: String = "",
    val showSendResultSheet: Boolean = false,
    val sendWasSuccessful: Boolean = false,
    val sendResultMessage: String = "",
    val isTransactionsLoading: Boolean = false,
    val transactionsErrorMessage: String? = null,
    val transactions: List<TransactionUiItem> = emptyList(),
    val errorMessage: String? = null
)
