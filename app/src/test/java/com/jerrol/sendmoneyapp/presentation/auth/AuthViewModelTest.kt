package com.jerrol.sendmoneyapp.presentation.auth

import com.jerrol.sendmoneyapp.data.auth.InMemoryAuthRepository
import com.jerrol.sendmoneyapp.domain.auth.usecase.GetCurrentUserUseCase
import com.jerrol.sendmoneyapp.domain.auth.usecase.IsLoggedInUseCase
import com.jerrol.sendmoneyapp.domain.auth.usecase.LoginUseCase
import com.jerrol.sendmoneyapp.domain.auth.usecase.LogoutUseCase
import com.jerrol.sendmoneyapp.domain.transaction.usecase.CreateTransactionUseCase
import com.jerrol.sendmoneyapp.domain.transaction.usecase.GetTransactionsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = InMemoryAuthRepository()
    private val transactionRepository = FakeTransactionRepository()
    private val viewModel = AuthViewModel(
        loginUseCase = LoginUseCase(repository),
        logoutUseCase = LogoutUseCase(repository),
        isLoggedInUseCase = IsLoggedInUseCase(repository),
        getCurrentUserUseCase = GetCurrentUserUseCase(repository),
        createTransactionUseCase = CreateTransactionUseCase(transactionRepository),
        getTransactionsUseCase = GetTransactionsUseCase(transactionRepository)
    )

    @Test
    fun login_withValidCredentials_updatesToLoggedInState() = runTest {
        viewModel.onUsernameChanged("demo")
        viewModel.onPasswordChanged("password123")

        viewModel.login()

        val state = viewModel.uiState.value
        assertTrue(state.isLoggedIn)
        assertEquals("demo", state.currentUsername)
        assertNull(state.errorMessage)
    }

    @Test
    fun login_withInvalidCredentials_showsError() = runTest {
        viewModel.onUsernameChanged("wrong")
        viewModel.onPasswordChanged("wrong")

        viewModel.login()

        val state = viewModel.uiState.value
        assertFalse(state.isLoggedIn)
        assertEquals("Invalid username or password.", state.errorMessage)
    }

    @Test
    fun logout_clearsAuthenticatedSession() = runTest {
        viewModel.onUsernameChanged("demo")
        viewModel.onPasswordChanged("password123")
        viewModel.login()

        viewModel.logout()

        val state = viewModel.uiState.value
        assertFalse(state.isLoggedIn)
        assertNull(state.currentUsername)
        assertEquals("", state.username)
        assertEquals("", state.password)
    }

    @Test
    fun toggleBalanceVisibility_updatesMaskState() = runTest {
        assertTrue(viewModel.uiState.value.isBalanceVisible)

        viewModel.toggleBalanceVisibility()
        assertFalse(viewModel.uiState.value.isBalanceVisible)

        viewModel.toggleBalanceVisibility()
        assertTrue(viewModel.uiState.value.isBalanceVisible)
    }

    @Test
    fun onSendAmountChanged_filtersNonNumericCharacters() = runTest {
        viewModel.onSendAmountChanged("1a2b.3x4c")

        assertEquals("12.34", viewModel.uiState.value.sendAmountInput)
    }

    @Test
    fun submitSendMoney_withAmountGreaterThanBalance_showsErrorBottomSheet() = runTest {
        viewModel.onSendAmountChanged("600")
        viewModel.submitSendMoney()

        val state = viewModel.uiState.value
        assertTrue(state.showSendResultSheet)
        assertFalse(state.sendWasSuccessful)
        assertEquals("500.00 PHP", state.walletBalance)
    }

    @Test
    fun submitSendMoney_withValidAmount_deductsBalanceAndShowsSuccess() = runTest {
        viewModel.onSendAmountChanged("100")
        viewModel.submitSendMoney()

        val state = viewModel.uiState.value
        assertTrue(state.showSendResultSheet)
        assertTrue(state.sendWasSuccessful)
        assertEquals("400.00 PHP", state.walletBalance)
        assertEquals("", state.sendAmountInput)
    }

    @Test
    fun loadTransactions_returnsTransactionHistory() = runTest {
        viewModel.onSendAmountChanged("50")
        viewModel.submitSendMoney()

        viewModel.loadTransactions()

        val state = viewModel.uiState.value
        assertFalse(state.isTransactionsLoading)
        assertTrue(state.transactions.isNotEmpty())
        assertEquals("50.00 PHP", state.transactions.first().amountDisplay)
    }
}
