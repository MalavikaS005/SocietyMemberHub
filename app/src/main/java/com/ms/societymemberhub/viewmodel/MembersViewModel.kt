package com.ms.societymemberhub.viewmodel

import androidx.lifecycle.ViewModel
import com.ms.societymemberhub.data.model.Member
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MembersViewModel : ViewModel() {

    private val _members = MutableStateFlow(
        listOf(
            Member("Malavika Sahoo", "Core Member", "Android"),
            Member("Kajal Rai", "Member", "Web")
        )
    )

    val members: StateFlow<List<Member>> = _members

    fun addMember(name: String, role: String, domain: String) {
        _members.value = _members.value + Member(name, role, domain)
    }

    fun deleteMember(member: Member) {
        _members.value = _members.value - member
    }

    fun removeMemberAt(index: Int) {
        _members.value = _members.value.toMutableList().also {
            if (index in it.indices) {
                it.removeAt(index)
            }
        }
    }

    
    fun updateMember(
        oldMember: Member,
        name: String,
        role: String,
        domain: String
    ) {
        _members.value = _members.value.map { member ->
            if (member == oldMember) {
                member.copy(
                    name = name,
                    role = role,
                    domain = domain
                )
            } else {
                member
            }
        }
    }
}
