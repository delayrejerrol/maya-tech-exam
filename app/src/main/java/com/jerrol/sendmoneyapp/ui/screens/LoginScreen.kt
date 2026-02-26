package com.jerrol.sendmoneyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jerrol.sendmoneyapp.presentation.auth.AuthUiState

@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = "Use demo credentials: demo / password123",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = uiState.username,
            onValueChange = onUsernameChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("usernameField"),
            label = { Text("Username") },
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = onPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("passwordField"),
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Start
            )
        }

        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("loginButton")
                .padding(top = 24.dp),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(vertical = 2.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Login")
            }
        }
    }
}
