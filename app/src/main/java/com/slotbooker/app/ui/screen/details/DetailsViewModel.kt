package com.slotbooker.app.ui.screen.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slotbooker.app.domain.model.BookSlotRequest
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.usecase.BookSlotUseCase
import com.slotbooker.app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val bookSlotUseCase: BookSlotUseCase
) : ViewModel() {

    var name by mutableStateOf("")
    var phone by mutableStateOf("")

    private val _uiState = MutableStateFlow<UiState<Unit>?>(null)
    val uiState: StateFlow<UiState<Unit>?> = _uiState.asStateFlow()

    fun book(slot: Slot) = viewModelScope.launch {
        if (name.isBlank()) {
            _uiState.value = UiState.Error("Name is required")
            return@launch
        }
        if (phone.length < 10) {
            _uiState.value = UiState.Error("Enter valid phone number")
            return@launch
        }

        _uiState.value = UiState.Loading

        val result = bookSlotUseCase(
            BookSlotRequest(name, phone, slot.slotId)
        )

        _uiState.value = result.fold(
            onSuccess = { UiState.Success(Unit) },
            onFailure = { UiState.Error(it.message ?: "Booking failed") }
        )
    }
}