package com.slotbooker.app.data.remote.dto

import com.slotbooker.app.domain.model.Slot
import kotlinx.serialization.Serializable

@Serializable
data class SlotsResponse(
    val status: String,
    val message: String,
    val data: List<SlotDto>
)

@Serializable
data class SlotDto(
    val slotId: String,
    val name: String,
    val startTime: String,
    val endTime: String
) {
    fun toDomain() = Slot(slotId, name, startTime, endTime)
}

@Serializable
data class BookingResponse(
    val status: String,
    val message: String
)