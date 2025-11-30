package com.slotbooker.app.data.mock

import com.slotbooker.app.domain.model.Slot
import kotlinx.serialization.Serializable

object FakeServerDatabase {

    private val availableSlots = mutableListOf(
        Slot(slotId = "SLOT001", name = "Morning Yoga", startTime = "09:00", endTime = "10:00"),
        Slot(slotId = "SLOT002", name = "Late Morning Slot", startTime = "10:00", endTime = "11:00"),
        Slot(slotId = "SLOT003", name = "Afternoon Slot", startTime = "14:00", endTime = "15:00"),
        Slot(slotId = "SLOT004", name = "Evening Slot", startTime = "16:00", endTime = "17:00")
    )

    private val myBookedSlots = mutableListOf<Slot>()

    fun getAvailableSlots(): ApiResponse<List<Slot>> = ApiResponse(
        status = "success",
        message = "Available slots fetched successfully",
        data = availableSlots.toList(),
        code = 200
    )

    fun getMyBookedSlots(): ApiResponse<List<Slot>> = ApiResponse(
        status = "success",
        message = "Your booked slots fetched successfully",
        data = myBookedSlots.toList(),
        code = 200
    )

    fun bookSlot(slotId: String): ApiResponse<BookSlotResponse> {
        // 1. Slot not found
        val slot = availableSlots.find { it.slotId == slotId }
            ?: return ApiResponse(
                status = "error",
                message = "Slot not found",
                data = null,
                code = 404
            )

        // 2. Already booked
        if (myBookedSlots.any { it.slotId == slotId }) {
            return ApiResponse(
                status = "error",
                message = "This slot is already booked",
                data = null,
                code = 409
            )
        }

        // SUCCESS: Book it!
        availableSlots.remove(slot)

        val newBooking = Slot(
            slotId = slotId,
            name = slot.name,
            startTime = slot.startTime,
            endTime = slot.endTime
        )
        myBookedSlots.add(0, newBooking) // newest first

        return ApiResponse(
            status = "success",
            message = "Slot booked successfully!",
            data = BookSlotResponse(bookingId = newBooking.slotId),
            code = 201
        )
    }
}

@Serializable
data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T?,
    val code: Int? = null
)

@Serializable
data class BookSlotResponse(
    val bookingId: String
)