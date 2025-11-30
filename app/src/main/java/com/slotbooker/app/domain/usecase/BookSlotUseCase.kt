package com.slotbooker.app.domain.usecase

import com.slotbooker.app.domain.model.BookSlotRequest
import com.slotbooker.app.domain.repository.BookingRepository
import javax.inject.Inject

class BookSlotUseCase @Inject constructor(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(request: BookSlotRequest): Result<Unit> =
        repository.bookSlot(request)
}