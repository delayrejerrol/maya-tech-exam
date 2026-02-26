package com.jerrol.sendmoneyapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jerrol.sendmoneyapp.presentation.auth.AuthUiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginButtonClick_triggersCallbackAfterEnteringCredentials() {
        var loginClicked = false
        var username = ""
        var password = ""

        composeRule.setContent {
            LoginScreen(
                uiState = AuthUiState(username = username, password = password),
                onUsernameChanged = { username = it },
                onPasswordChanged = { password = it },
                onLoginClicked = { loginClicked = true }
            )
        }

        composeRule.onNodeWithTag("usernameField").performTextInput("demo")
        composeRule.onNodeWithTag("passwordField").performTextInput("password123")
        composeRule.onNodeWithTag("loginButton").performClick()

        assertTrue(loginClicked)
    }

    @Test
    fun loginScreen_showsCredentialHint() {
        composeRule.setContent {
            LoginScreen(
                uiState = AuthUiState(),
                onUsernameChanged = {},
                onPasswordChanged = {},
                onLoginClicked = {}
            )
        }

        composeRule.onNodeWithText("Use demo credentials: demo / password123").assertIsDisplayed()
    }
}
