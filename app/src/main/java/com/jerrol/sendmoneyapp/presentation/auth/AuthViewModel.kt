package com.jerrol.sendmoneyapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerrol.sendmoneyapp.domain.auth.model.LoginResult
import com.jerrol.sendmoneyapp.domain.auth.usecase.GetCurrentUserUseCase
import com.jerrol.sendmoneyapp.domain.auth.usecase.IsLoggedInUseCase
import com.jerrol.sendmoneyapp.domain.auth.usecase.LoginUseCase
import com.jerrol.sendmoneyapp.domain.auth.usecase.LogoutUseCase
import com.jerrol.sendmoneyapp.domain.transaction.usecase.CreateTransactionUseCase
import com.jerrol.sendmoneyapp.domain.transaction.usecase.GetTransactionsUseCase
import com.jerrol.sendmoneyapp.presentation.transaction.TransactionUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            isLoggedIn = isLoggedInUseCase(),
            currentUsername = getCurrentUserUseCase()
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onUsernameChanged(value: String) {
        _uiState.update { state ->
            state.copy(username = value, errorMessage = null)
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { state ->
            state.copy(password = value, errorMessage = null)
        }
    }

    fun login() {
        val username = _uiState.value.username.trim()
        val password = _uiState.value.password.trim()

        if (username.isEmpty() || password.isEmpty()) {
            _uiState.update { state ->
                state.copy(errorMessage = "Username and password are required.")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true, errorMessage = null)
            }

            when (val result = loginUseCase(username, password)) {
                is LoginResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            currentUsername = result.user.username,
                            password = "",
                            errorMessage = null
                        )
                    }
                    loadTransactions()
                }

                is LoginResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            currentUsername = null,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        logoutUseCase()
        _uiState.update { state ->
            state.copy(
                isLoggedIn = false,
                currentUsername = null,
                username = "",
                password = "",
                isBalanceVisible = true,
                sendAmountInput = "",
                showSendResultSheet = false,
                sendWasSuccessful = false,
                sendResultMessage = "",
                isTransactionsLoading = false,
                transactionsErrorMessage = null,
                transactions = emptyList(),
                errorMessage = null
            )
        }
    }

    fun toggleBalanceVisibility() {
        _uiState.update { state ->
            state.copy(isBalanceVisible = !state.isBalanceVisible)
        }
    }

    fun onSendAmountChanged(value: String) {
        val filtered = value.filter { it.isDigit() || it == '.' }
        val normalized = normalizeNumericInput(filtered)

        _uiState.update { state ->
            state.copy(sendAmountInput = normalized)
        }
    }

    fun submitSendMoney() {
        val state = _uiState.value
        val amountText = state.sendAmountInput.trim()
        val amount = amountText.toDoubleOrNull()
        val username = state.currentUsername ?: "demo"

        when {
            amountText.isEmpty() -> showSendResult(
                success = false,
                message = "Please enter an amount."
            )

            amount == null -> showSendResult(
                success = false,
                message = "Invalid amount format."
            )

            amount <= 0.0 -> showSendResult(
                success = false,
                message = "Amount must be greater than zero."
            )

            amount > state.walletBalanceAmount -> showSendResult(
                success = false,
                message = "Insufficient balance. Enter an amount less than or equal to ${state.walletBalance}."
            )

            else -> {
                viewModelScope.launch {
                    val createResult = createTransactionUseCase(username, amount)
                    createResult.fold(
                        onSuccess = {
                            val remainingBalance = (state.walletBalanceAmount - amount).coerceAtLeast(0.0)
                            _uiState.update { current ->
                                current.copy(
                                    walletBalanceAmount = remainingBalance,
                                    walletBalance = formatBalance(remainingBalance),
                                    sendAmountInput = "",
                                    showSendResultSheet = true,
                                    sendWasSuccessful = true,
                                    sendResultMessage = "Successfully sent ${formatBalance(amount)}."
                                )
                            }
                            loadTransactions()
                        },
                        onFailure = {
                            showSendResult(
                                success = false,
                                message = "Unable to send money right now. Please try again."
                            )
                        }
                    )
                }
            }
        }
    }

    fun loadTransactions() {
        val username = _uiState.value.currentUsername ?: "demo"
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isTransactionsLoading = true,
                    transactionsErrorMessage = null
                )
            }

            val result = getTransactionsUseCase(username)
            result.fold(
                onSuccess = { transactions ->
                    _uiState.update { state ->
                        state.copy(
                            isTransactionsLoading = false,
                            transactions = transactions.map { transaction ->
                                TransactionUiItem(
                                    id = transaction.id,
                                    username = transaction.username,
                                    amountDisplay = formatBalance(transaction.amount),
                                    source = transaction.source
                                )
                            }
                        )
                    }
                },
                onFailure = {
                    _uiState.update { state ->
                        state.copy(
                            isTransactionsLoading = false,
                            transactionsErrorMessage = "Failed to load transactions. Please try again."
                        )
                    }
                }
            )
        }
    }

    fun dismissSendResultSheet() {
        _uiState.update { state ->
            state.copy(showSendResultSheet = false)
        }
    }

    private fun showSendResult(success: Boolean, message: String) {
        _uiState.update { state ->
            state.copy(
                showSendResultSheet = true,
                sendWasSuccessful = success,
                sendResultMessage = message
            )
        }
    }

    private fun normalizeNumericInput(input: String): String {
        val firstDotIndex = input.indexOf('.')
        if (firstDotIndex == -1) {
            return input
        }

        val integerPart = input.substring(0, firstDotIndex)
        val decimalPart = input.substring(firstDotIndex + 1).replace(".", "").take(2)
        return "$integerPart.$decimalPart"
    }

    private fun formatBalance(amount: Double): String {
        return String.format(Locale.US, "%.2f PHP", amount)
    }
}
