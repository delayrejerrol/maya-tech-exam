package com.jerrol.sendmoneyapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jerrol.sendmoneyapp.presentation.auth.AuthViewModel
import com.jerrol.sendmoneyapp.ui.navigation.AppRoutes
import com.jerrol.sendmoneyapp.ui.screens.HomeScreen
import com.jerrol.sendmoneyapp.ui.screens.LoginScreen
import com.jerrol.sendmoneyapp.ui.screens.SendMoneyScreen
import com.jerrol.sendmoneyapp.ui.screens.TransactionsScreen

@Composable
fun SendMoneyApp(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoggedIn) {
        val destination = if (uiState.isLoggedIn) AppRoutes.HOME else AppRoutes.LOGIN
        if (navController.currentDestination?.route == destination) return@LaunchedEffect

        navController.navigate(destination) {
            popUpTo(AppRoutes.LOGIN) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN
    ) {
        composable(AppRoutes.LOGIN) {
            LoginScreen(
                uiState = uiState,
                onUsernameChanged = authViewModel::onUsernameChanged,
                onPasswordChanged = authViewModel::onPasswordChanged,
                onLoginClicked = authViewModel::login
            )
        }

        composable(AppRoutes.HOME) {
            HomeScreen(
                username = uiState.currentUsername ?: "User",
                walletBalance = uiState.walletBalance,
                isBalanceVisible = uiState.isBalanceVisible,
                onToggleBalanceVisibility = authViewModel::toggleBalanceVisibility,
                onSendMoneyClicked = {
                    navController.navigate(AppRoutes.SEND_MONEY)
                },
                onOpenTransactions = {
                    navController.navigate(AppRoutes.TRANSACTIONS)
                },
                onSignOutClicked = authViewModel::logout
            )
        }

        composable(AppRoutes.SEND_MONEY) {
            SendMoneyScreen(
                walletBalance = uiState.walletBalance,
                amountInput = uiState.sendAmountInput,
                showResultSheet = uiState.showSendResultSheet,
                sendWasSuccessful = uiState.sendWasSuccessful,
                resultMessage = uiState.sendResultMessage,
                onAmountChanged = authViewModel::onSendAmountChanged,
                onSubmitClicked = authViewModel::submitSendMoney,
                onDismissResultSheet = authViewModel::dismissSendResultSheet,
                onBackClicked = {
                    navController.popBackStack()
                },
                onSignOutClicked = authViewModel::logout
            )
        }

        composable(AppRoutes.TRANSACTIONS) {
            LaunchedEffect(Unit) {
                authViewModel.loadTransactions()
            }
            TransactionsScreen(
                transactions = uiState.transactions,
                isLoading = uiState.isTransactionsLoading,
                errorMessage = uiState.transactionsErrorMessage,
                onRetryClicked = authViewModel::loadTransactions,
                onBackClicked = {
                    navController.popBackStack()
                },
                onSignOutClicked = authViewModel::logout
            )
        }
    }
}
