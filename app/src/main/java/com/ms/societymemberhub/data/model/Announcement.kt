package com.ms.societymemberhub.data.model

enum class Priority {
    HIGH, MEDIUM, LOW
}

data class Announcement(
    val title: String,
    val message: String,
    val priority: Priority = Priority.MEDIUM
)
