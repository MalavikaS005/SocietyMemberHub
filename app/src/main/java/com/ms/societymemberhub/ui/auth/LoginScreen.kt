package com.ms.societymemberhub.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ms.societymemberhub.utils.UserRole

@Composable
fun LoginScreen(
    onLoginSuccess: (UserRole) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Admin Login", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )

                if (error.isNotEmpty()) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // 🔐 Mock authentication
                        if (username == "admin" && password == "admin123") {
                            onLoginSuccess(UserRole.ADMIN)
                        } else {
                            error = "Invalid credentials"
                        }
                    }
                ) {
                    Text("Login")
                }
            }
        }
    }
}
