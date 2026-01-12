package com.ms.societymemberhub.ui.members

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ms.societymemberhub.data.model.Member
import com.ms.societymemberhub.viewmodel.MembersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembersScreen(
    isAdmin: Boolean,
    viewModel: MembersViewModel
) {
    val members by viewModel.members.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var editingMember by remember { mutableStateOf<Member?>(null) }

    // This Scaffold wraps the main UI content
    Scaffold(
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = {
                        editingMember = null // Ensure we are adding, not editing
                        showDialog = true
                    }
                ) {
                    // It's better to use an Icon than text for a FAB
                    // Example: Icon(Icons.Default.Add, contentDescription = "Add Member")
                    Text("+")
                }
            }
        }
    ) { padding ->

        // A scrollable list for the members
        LazyColumn(
            modifier = Modifier
                .padding(padding) // Apply padding from the Scaffold
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp), // Inner padding for the list items
            verticalArrangement = Arrangement.spacedBy(12.dp) // Space between items
        ) {
            items(members) { member ->

                var menuExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Member's name
                            Text(
                                text = member.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            // Show menu only for admins
                            if (isAdmin) {
                                Box {
                                    IconButton(onClick = { menuExpanded = true }) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = "Member options"
                                        )
                                    }

                                    // Dropdown menu for Edit and Delete
                                    DropdownMenu(
                                        expanded = menuExpanded,
                                        onDismissRequest = { menuExpanded = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Edit") },
                                            onClick = {
                                                editingMember = member
                                                showDialog = true
                                                menuExpanded = false
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Delete") },
                                            onClick = {
                                                viewModel.deleteMember(member)
                                                menuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        // Member's role and domain
                        Text(text = "${member.role} • ${member.domain}")
                    }
                }
            }
        }
    }

    // Add / Edit dialog logic
    if (showDialog && isAdmin) {
        AddEditMemberDialog(
            initialName = editingMember?.name ?: "",
            initialRole = editingMember?.role ?: "",
            initialDomain = editingMember?.domain ?: "",
            onConfirm = { name, role, domain ->
                if (editingMember == null) {
                    // Add new member
                    viewModel.addMember(name, role, domain)
                } else {
                    // Update existing member
                    val updatedMember = editingMember!!.copy(name = name, role = role, domain = domain)
                    //viewModel.updateMember(updatedMember)
                }
                showDialog = false
                editingMember = null
            },
            onDismiss = {
                showDialog = false
                editingMember = null
            }
        )
    }
}


@Composable
fun AddEditMemberDialog(
    initialName: String,
    initialRole: String,
    initialDomain: String,
    onConfirm: (name: String, role: String, domain: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var role by remember { mutableStateOf(initialRole) }
    var domain by remember { mutableStateOf(initialDomain) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialName.isEmpty()) "Add Member" else "Edit Member") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role (e.g., Member, Lead)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = domain,
                    onValueChange = { domain = it },
                    label = { Text("Domain (e.g., Android, Web)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Basic validation to prevent adding empty names
                    if (name.isNotBlank()) {
                        onConfirm(name, role, domain)
                    }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
