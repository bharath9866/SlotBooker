package com.slotbooker.app.domain.usecase

import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.repository.BookingRepository
import javax.inject.Inject

class GetMySlotsUseCase @Inject constructor(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(): Result<List<Slot>> = repository.getMyBookingSlots()
}