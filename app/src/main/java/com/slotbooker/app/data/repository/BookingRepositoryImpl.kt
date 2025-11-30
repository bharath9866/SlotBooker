package com.slotbooker.app.data.repository

import com.slotbooker.app.data.remote.ApiService
import com.slotbooker.app.domain.model.BookSlotRequest
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.repository.BookingRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookingRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BookingRepository {

    override suspend fun getAvailableSlots(): Result<List<Slot>> = try {
        val response = apiService.getAvailableSlots()
        if (response.status == "success") {
            Result.success(response.data.map { it.toDomain() })
        } else {
            Result.failure(Exception(response.message))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun bookSlot(request: BookSlotRequest): Result<Unit> = try {
        val response = apiService.bookSlot(request)
        if (response.status == "success")
            Result.success(Unit)
        else
            Result.failure(Exception(response.message))
    } catch (e: Exception) {
        Result.failure(e)
    }


    override suspend fun getMyBookingSlots(): Result<List<Slot>> = try {
        val response = apiService.getMyBookingSlots()
        if (response.status == "success") {
            Result.success(response.data.map { it.toDomain() })
        } else {
            Result.failure(Exception(response.message))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}