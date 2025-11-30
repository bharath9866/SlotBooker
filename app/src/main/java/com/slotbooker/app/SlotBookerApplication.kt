package com.slotbooker.app

import android.app.Application
import com.slotbooker.app.data.mock.FakeServerDatabase
import com.slotbooker.app.domain.model.BookSlotRequest
import dagger.hilt.android.HiltAndroidApp
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class SlotBookerApplication : Application() {

    companion object {
        lateinit var mockWebServer: MockWebServer
            private set
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            startMockWebServer()
        }
    }

    private fun startMockWebServer() {
        Thread {
            mockWebServer = MockWebServer().apply {
                dispatcher = object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        val path = request.path.orEmpty()
                        println("MockWebServer ← ${request.method} $path")

                        return when(request.path?.substringBefore("?")) {
                            "/available-slots" -> {
                                val response = FakeServerDatabase.getAvailableSlots()
                                MockResponse()
                                    .setResponseCode(response.code ?: 200)
                                    .addHeader("Content-Type", "application/json")
                                    .setBody(Json.encodeToString(response))
                                    .setBodyDelay(600, TimeUnit.MILLISECONDS)
                            }
                            "/my-bookings" -> {
                                val response = FakeServerDatabase.getMyBookedSlots()
                                MockResponse()
                                    .setResponseCode(response.code ?: 200)
                                    .addHeader("Content-Type", "application/json")
                                    .setBody(Json.encodeToString(response))
                                    .setBodyDelay(600, TimeUnit.MILLISECONDS)
                            }
                            "/slot-booking" -> {
                                val body = request.body.readUtf8()
                                val req = try {
                                    Json.decodeFromString<BookSlotRequest>(body)
                                } catch (e: Exception) {
                                    return MockResponse().setResponseCode(400)
                                        .setBody("""{"success":false,"message":"Invalid request"}""")
                                }

                                val result = FakeServerDatabase.bookSlot(req.slotId)
                                MockResponse()
                                    .setResponseCode(result.code ?: 400)
                                    .addHeader("Content-Type", "application/json")
                                    .setBody(Json.encodeToString(result))
                                    .setBodyDelay(800, TimeUnit.MILLISECONDS)
                            }
                            else -> MockResponse().setResponseCode(404)
                        }
                    }
                }
                start(8080)
                println("MockWebServer STARTED → http://127.0.0.1:8080")
            }
        }.start()
    }
}