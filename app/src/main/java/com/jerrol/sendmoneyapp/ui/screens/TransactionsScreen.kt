package com.jerrol.sendmoneyapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jerrol.sendmoneyapp.presentation.transaction.TransactionUiItem

@Composable
fun TransactionsScreen(
    transactions: List<TransactionUiItem>,
    isLoading: Boolean,
    errorMessage: String?,
    onRetryClicked: () -> Unit,
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
                text = "Transactions",
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

        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
                Button(
                    onClick = onRetryClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Retry")
                }
            }

            transactions.isEmpty() -> {
                Text("No transactions yet.")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(transactions, key = { it.id }) { transaction ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Amount Sent: ${transaction.amountDisplay}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("By: ${transaction.username}")
                                Text("Source: ${transaction.source}")
                            }
                        }
                    }
                }
            }
        }
    }
}
