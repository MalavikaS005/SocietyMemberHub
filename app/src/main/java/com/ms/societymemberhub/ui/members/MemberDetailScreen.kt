package com.ms.societymemberhub.ui.members

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ms.societymemberhub.data.model.Member

@Composable
fun MemberDetailScreen(
    member: Member
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(member.name, style = MaterialTheme.typography.headlineMedium)
        Text("Role: ${member.role}")
        Text("Domain: ${member.domain}")
    }
}
