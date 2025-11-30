package com.slotbooker.app.ui.screen.slots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.usecase.GetAvailableSlotsUseCase
import com.slotbooker.app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotsViewModel @Inject constructor(
    private val getAvailableSlotsUseCase: GetAvailableSlotsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Slot>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Slot>>> = _uiState.asStateFlow()

    private val _selectedSlot = MutableStateFlow<Slot?>(null)
    val selectedSlot = _selectedSlot.asStateFlow()

    init { loadSlots() }

    fun loadSlots() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        getAvailableSlotsUseCase().fold(
            onSuccess = { _uiState.value = UiState.Success(it) },
            onFailure = { _uiState.value = UiState.Error(it.message ?: "Failed to load") }
        )
    }

    fun selectSlot(slot: Slot) {
        _selectedSlot.value = slot
    }
}