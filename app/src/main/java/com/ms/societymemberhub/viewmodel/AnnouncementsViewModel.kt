package com.ms.societymemberhub.viewmodel

import androidx.lifecycle.ViewModel
import com.ms.societymemberhub.data.model.Announcement
import com.ms.societymemberhub.data.model.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnnouncementsViewModel : ViewModel() {

    private val _announcements = MutableStateFlow(
        listOf(
            Announcement("Meeting", "General body meeting", Priority.HIGH),
            Announcement("Workshop", "Compose basics", Priority.MEDIUM),
            Announcement("Reminder", "Submit reports", Priority.LOW)
        )
    )

    val announcements: StateFlow<List<Announcement>> = _announcements

    fun addAnnouncement(title: String, message: String, priority: Priority) {
        _announcements.value =
            _announcements.value + Announcement(title, message, priority)
    }

    fun deleteAnnouncement(announcement: Announcement) {
        _announcements.value = _announcements.value - announcement
    }

    fun updateAnnouncement(updated: Announcement) {
        _announcements.value = _announcements.value.map {
            if (it == updated) updated else it
        }
    }
}
