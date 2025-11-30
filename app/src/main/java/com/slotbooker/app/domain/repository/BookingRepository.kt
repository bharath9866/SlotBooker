package com.slotbooker.app.domain.repository

import com.slotbooker.app.domain.model.BookSlotRequest
import com.slotbooker.app.domain.model.Slot

interface BookingRepository {
    suspend fun getAvailableSlots(): Result<List<Slot>>
    suspend fun getMyBookingSlots(): Result<List<Slot>>
    suspend fun bookSlot(request: BookSlotRequest): Result<Unit>
}