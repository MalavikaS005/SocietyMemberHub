package com.ms.societymemberhub.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ms.societymemberhub.ui.announcements.AnnouncementsScreen
import com.ms.societymemberhub.ui.members.MembersScreen
import com.ms.societymemberhub.utils.UserRole
import com.ms.societymemberhub.viewmodel.AnnouncementsViewModel
import com.ms.societymemberhub.viewmodel.MembersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    role: UserRole,
    membersViewModel: MembersViewModel,
    announcementsViewModel: AnnouncementsViewModel,
    onLogout: () -> Unit
) {
    var tab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (role == UserRole.ADMIN)
                            "Admin Dashboard"
                        else
                            "Member Dashboard"
                    )
                },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val selectedColor = MaterialTheme.colorScheme.primary
                val unselectedColor = MaterialTheme.colorScheme.surfaceVariant

                Button(
                    onClick = { tab = 0 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tab == 0) selectedColor else unselectedColor
                    )
                ) {
                    Text("Members")
                }

                Button(
                    onClick = { tab = 1 },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tab == 1) selectedColor else unselectedColor
                    )
                ) {
                    Text("Announcements")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (tab == 0) {
                MembersScreen(
                    viewModel = membersViewModel,
                    isAdmin = role == UserRole.ADMIN
                )
            } else {
                AnnouncementsScreen(
                    role = role,
                    viewModel = announcementsViewModel
                )
            }
        }
    }
}
