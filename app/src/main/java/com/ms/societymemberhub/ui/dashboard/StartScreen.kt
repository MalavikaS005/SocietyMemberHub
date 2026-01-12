package com.ms.societymemberhub.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Import for Color.Unspecified
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight // Import for FontWeight
import androidx.compose.ui.unit.dp
//import com.ms.societymemberhub.R // Import for R.drawable.app_logo

@Composable
fun StartScreen(
    onAdminClick: () -> Unit,
    onMemberClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // App Logo
//        Icon(
//            // The painterResource function is now recognized
//            painter = painterResource(id = R.drawable.app_logo.),
//            contentDescription = "App Logo",
//            modifier = Modifier.size(96.dp),
//            tint = Color.Unspecified
//        )

        Spacer(Modifier.height(16.dp))

        // App Name
        Text(
            text = "Society Member Hub",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onAdminClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue as Admin")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onMemberClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue as Member")
        }
    }
}
