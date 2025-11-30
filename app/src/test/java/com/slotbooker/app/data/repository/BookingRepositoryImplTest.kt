package com.slotbooker.app.data.repository

import com.slotbooker.app.data.remote.ApiService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookingRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ApiService
    private lateinit var repository: BookingRepositoryImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        repository = BookingRepositoryImpl(api)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getAvailableSlots_success() = runTest {
        val mockResponse = """
            {
              "status": "success",
              "message": "ok",
              "data": [
                { "slotId":"1","name":"Test Slot","startTime":"09:00","endTime":"10:00" }
              ]
            }
        """
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))

        val result = repository.getAvailableSlots()

        assertTrue(result.isSuccess)
    }

    @Test
    fun getAvailableSlots_error() = runTest {
        val mockResponse = """
            { "status": "error", "message": "Something went wrong", "data": [] }
        """
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))

        val result = repository.getAvailableSlots()

        assertTrue(result.isFailure)
    }
}
