package com.jerrol.sendmoneyapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    username: String,
    walletBalance: String,
    isBalanceVisible: Boolean,
    onToggleBalanceVisibility: () -> Unit,
    onSendMoneyClicked: () -> Unit,
    onOpenTransactions: () -> Unit,
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isBalanceVisible) "₱$walletBalance" else "₱******",
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(
                        onClick = onToggleBalanceVisibility,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isBalanceVisible) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            contentDescription = if (isBalanceVisible) {
                                "Hide balance"
                            } else {
                                "Show balance"
                            },
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text(
                    text = "Wallet Balance",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onOpenTransactions,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(0xFF90EE90)
//                        )
                    ) {
                        Text("View transactions")
                    }

                    Button(
                        onClick = onSendMoneyClicked,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("Send money")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSignOutClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign out")
        }
    }
}
