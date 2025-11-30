package com.slotbooker.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Slot(
    val slotId: String,
    val name: String,
    val startTime: String,
    val endTime: String
)

@Serializable
data class BookSlotRequest(
    val customerName: String,
    val phoneNumber: String,
    val slotId: String
)