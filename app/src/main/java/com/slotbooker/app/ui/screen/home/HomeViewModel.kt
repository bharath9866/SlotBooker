package com.slotbooker.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.domain.usecase.GetMySlotsUseCase
import com.slotbooker.app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMySlotsUseCase: GetMySlotsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Slot>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Slot>>> = _uiState.asStateFlow()

    fun loadSlots() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        getMySlotsUseCase().fold(
            onSuccess = { _uiState.value = UiState.Success(it) },
            onFailure = { _uiState.value = UiState.Error(it.message ?: "Failed to load") }
        )
    }

}