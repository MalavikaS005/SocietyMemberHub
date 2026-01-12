package com.ms.societymemberhub.viewmodel

import androidx.lifecycle.ViewModel
import com.ms.societymemberhub.utils.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RoleViewModel : ViewModel() {

    private val _role = MutableStateFlow<UserRole?>(null)
    val role: StateFlow<UserRole?> = _role

    fun setRole(role: UserRole) {
        _role.value = role
    }

    fun logout() {
        _role.value = null
    }
}
