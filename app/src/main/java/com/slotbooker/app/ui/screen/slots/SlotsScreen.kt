package com.slotbooker.app.ui.screen.slots

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.preview.PreviewData
import com.slotbooker.app.ui.components.ShimmerSlotItem
import com.slotbooker.app.ui.components.SlotCard
import com.slotbooker.app.ui.navigation.Screen
import com.slotbooker.app.util.UiState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotsScreen(
    navController: NavController,
    slotsViewModel: SlotsViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val state: UiState<List<Slot>> by slotsViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val selected by slotsViewModel.selectedSlot.collectAsState()

    LaunchedEffect(selected) {
        selected?.let { slot ->
            navController.navigate(Screen.Details(Json.encodeToString(slot))) {
                popUpTo<Screen.Slots> { inclusive = true }
            }
        }
    }

    SlotsScreen(
        modifier = Modifier,
        snackBarHostState = snackBarHostState,
        navController = navController,
        state = state,
        selected = selected,
        onClickSlot = { slotsViewModel.selectSlot(it) },
        onError = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotsScreen(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    state:UiState<List<Slot>>,
    selected:Slot?,
    onClickSlot:(Slot) -> Unit,
    onError: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Slots Available")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->
        when (state) {
            is UiState.Loading -> {
                LazyVerticalGrid(
                    modifier = Modifier.padding(innerPadding),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(6) { ShimmerSlotItem() }
                }
            }

            is UiState.Success -> {
                val slots = state.data
                LazyVerticalGrid(
                    modifier = Modifier.padding(innerPadding),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(slots.size) { i ->
                        val slot = slots[i]
                        SlotCard(
                            slot = slot,
                            isSelected = selected?.slotId == slot.slotId,
                            onClick = {
                                onClickSlot(slot)
                            }
                        )
                    }
                }
            }

            is UiState.Error -> {
                onError(state.message)
            }
        }
    }
}

@Preview
@Composable
private fun SlotsScreenPreview() {
    SlotsScreen(
        navController = rememberNavController(),
        snackBarHostState = SnackbarHostState(),
        modifier = Modifier,
        state = UiState.Success(PreviewData.AllSlots),
        selected = PreviewData.Slot1,
        onClickSlot = {},
        onError = {}
    )
}

@Preview
@Composable
private fun SlotsScreenLoadingPreview() {
    SlotsScreen(
        navController = rememberNavController(),
        snackBarHostState = SnackbarHostState(),
        modifier = Modifier,
        state = UiState.Loading,
        selected = PreviewData.Slot1,
        onClickSlot = {},
        onError = {}
    )
}