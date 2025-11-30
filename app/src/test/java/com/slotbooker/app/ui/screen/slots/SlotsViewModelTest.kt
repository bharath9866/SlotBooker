@file:OptIn(ExperimentalCoroutinesApi::class)

package com.slotbooker.app.ui.screen.slots

import app.cash.turbine.test
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.usecase.GetAvailableSlotsUseCase
import com.slotbooker.app.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SlotsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadSlots_success() = runTest {
        val useCase = mockk<GetAvailableSlotsUseCase>()
        coEvery { useCase.invoke() } returns Result.success(listOf(Slot("1", "Morning", "09:00", "10:00")))

        val vm = SlotsViewModel(useCase)

        vm.uiState.test {
            assert(awaitItem() is UiState.Loading)
            assert(awaitItem() is UiState.Success)
        }
    }
}
