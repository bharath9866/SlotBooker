@file:OptIn(ExperimentalCoroutinesApi::class)

package com.slotbooker.app.ui.screen.details

import app.cash.turbine.test
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.usecase.BookSlotUseCase
import com.slotbooker.app.testutil.MainDispatcherRule
import com.slotbooker.app.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun invalidName_showsError() = runTest {
        val vm = DetailsViewModel(mockk())
        val slot = Slot("1", "Test", "09:00", "10:00")

        vm.book(slot)

        vm.uiState.test {
            val result = awaitItem()
            assert(result is UiState.Error && result.message == "Name is required")
        }
    }

    @Test
    fun validInput_success() = runTest {
        val useCase = mockk<BookSlotUseCase>()
        coEvery { useCase(any()) } returns Result.success(Unit)

        val vm = DetailsViewModel(useCase)
        val slot = Slot("1", "Test", "09:00", "10:00")

        vm.name = "John"
        vm.phone = "9876543210"

        vm.book(slot)

        vm.uiState.drop(1).test {
            assert(awaitItem() is UiState.Loading)
            assert(awaitItem() is UiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
