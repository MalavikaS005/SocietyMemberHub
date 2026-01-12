package com.ms.societymemberhub.ui.announcements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ms.societymemberhub.data.model.Announcement
import com.ms.societymemberhub.utils.UserRole
import com.ms.societymemberhub.viewmodel.AnnouncementsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.ms.societymemberhub.data.model.Priority // <<< CORRECT IMPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(
    role: UserRole,
    viewModel: AnnouncementsViewModel
) {
    val announcements by viewModel.announcements.collectAsState()

    // Ensure this 'filter' state uses the correct Priority type
    var filter by remember { mutableStateOf<Priority?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<Announcement?>(null) }

    val filteredSorted = announcements
        .filter { filter == null || it.priority == filter } // This comparison will now work
        .sortedBy {
            when (it.priority) {
                Priority.HIGH -> 0
                Priority.MEDIUM -> 1
                Priority.LOW -> 2
            }
        }

    Scaffold(
        floatingActionButton = {
            if (role == UserRole.ADMIN) {
                FloatingActionButton(
                    onClick = {
                        editing = null
                        showDialog = true
                    }
                ) { Text("+") }
            }
        }
    ) { padding ->

        Column(Modifier.padding(padding).padding(16.dp)) {

            // 🔎 FILTER CHIPS
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = filter == null,
                    onClick = { filter = null },
                    label = { Text("All") }
                )
                // This now correctly iterates over the right enum
                Priority.values().forEach {
                    FilterChip(
                        selected = filter == it,
                        onClick = { filter = it },
                        label = { Text(it.name) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(filteredSorted) { ann ->
                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(ann.title, style = MaterialTheme.typography.titleMedium)
                                    // Assuming PriorityChip uses the correct Priority
                                    PriorityChip(ann.priority)
                                }

                                if (role == UserRole.ADMIN) {
                                    Box {
                                        IconButton(onClick = { expanded = true }) {
                                            Icon(Icons.Default.MoreVert, null)
                                        }
                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("Edit") },
                                                onClick = {
                                                    editing = ann
                                                    showDialog = true
                                                    expanded = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("Delete") },
                                                onClick = {
                                                    viewModel.deleteAnnouncement(ann)
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(4.dp))
                            Text(ann.message)
                        }
                    }
                }
            }
        }
    }

    if (showDialog && role == UserRole.ADMIN) {
        // This dialog now receives the correct Priority type
        AddEditAnnouncementDialog(
            initialTitle = editing?.title ?: "",
            initialMessage = editing?.message ?: "",
            initialPriority = editing?.priority ?: Priority.MEDIUM,
            onConfirm = { t, m, p ->
                if (editing == null) {
                    viewModel.addAnnouncement(t, m, p)
                } else {
                    viewModel.updateAnnouncement(
                        editing!!.copy(title = t, message = m, priority = p)
                    )
                }
                showDialog = false
                editing = null
            },
            onDismiss = {
                showDialog = false
                editing = null
            }
        )
    }
}
