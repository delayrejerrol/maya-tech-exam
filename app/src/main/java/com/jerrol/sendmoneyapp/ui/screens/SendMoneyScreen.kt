package com.jerrol.sendmoneyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    walletBalance: String,
    amountInput: String,
    showResultSheet: Boolean,
    sendWasSuccessful: Boolean,
    resultMessage: String,
    onAmountChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    onDismissResultSheet: () -> Unit,
    onBackClicked: () -> Unit,
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Home"
                )
            }
            Text(
                text = "Send Money",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onSignOutClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Sign out"
                )
            }
        }

        OutlinedTextField(
            value = amountInput,
            onValueChange = onAmountChanged,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            singleLine = true,
            label = { Text("Enter amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Text(
            text = "You have ₱$walletBalance in your wallet.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 20.dp, end = 16.dp, top = 4.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSubmitClicked,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().
                padding(start = 16.dp, end = 16.dp),
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(24.dp))

    }

    if (showResultSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissResultSheet
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (sendWasSuccessful) "Success" else "Error",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (sendWasSuccessful) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
                Text(
                    text = resultMessage,
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = onDismissResultSheet,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("OK")
                }
            }
        }
    }
}
