package com.slotbooker.app.data.remote

import com.slotbooker.app.data.remote.dto.BookingResponse
import com.slotbooker.app.data.remote.dto.SlotsResponse
import com.slotbooker.app.domain.model.BookSlotRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("available-slots")
    suspend fun getAvailableSlots(): SlotsResponse

    @GET("my-bookings")
    suspend fun getMyBookingSlots(): SlotsResponse

    @POST("slot-booking")
    suspend fun bookSlot(@Body request: BookSlotRequest): BookingResponse

}