package com.slotbooker.app.ui.screen.details

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.preview.PreviewData
import com.slotbooker.app.ui.navigation.Screen
import com.slotbooker.app.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    slot: Slot,
    navController: NavController,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState: UiState<Unit>? by detailsViewModel.uiState.collectAsState()

    // Show toast and navigate back on success
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                Toast.makeText(context, "Booked successfully!", Toast.LENGTH_SHORT).show()
                navController.popBackStack(Screen.Home, inclusive = false)
            }

            is UiState.Error -> {
                Toast.makeText(context, (uiState as UiState.Error).message, Toast.LENGTH_SHORT)
                    .show()
            }

            else -> Unit
        }
    }

    DetailsScreen(
        modifier = Modifier,
        navController = navController,
        slot = slot,
        name = detailsViewModel.name,
        onNameChange = { detailsViewModel.name = it },
        phone = detailsViewModel.phone,
        onPhoneChange = { detailsViewModel.phone = it },
        onClickBookingConfirm = { detailsViewModel.book(it) },
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    slot: Slot,
    name: String,
    onNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    uiState: UiState<Unit>?,
    onClickBookingConfirm: (Slot) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Complete Booking") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Selected slot card
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = slot.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    supportingContent = {
                        Text("${slot.startTime} – ${slot.endTime}")
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null
                        )
                    }
                )
            }

            // Name field
            OutlinedTextField(
                value = name,
                onValueChange = { onNameChange(it) },
                label = { Text("Full Name *") },
                singleLine = true,
                isError = name.isBlank() && uiState is UiState.Error,
                modifier = Modifier.fillMaxWidth()
            )

            // Phone field
            OutlinedTextField(
                value = phone,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) onPhoneChange(newValue)
                },
                label = { Text("Phone Number *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                isError = phone.length < 10 && uiState is UiState.Error,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { onClickBookingConfirm(slot) },
                enabled = uiState == null || uiState is UiState.Loading || uiState is UiState.Error,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (uiState == UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Text("Confirm Booking")
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenInitialScreenPreview() {
    DetailsScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        slot = PreviewData.Slot1,
        name = "",
        onNameChange = {},
        phone = "",
        onPhoneChange = {},
        uiState = null,
        onClickBookingConfirm = {}
    )
}

@Preview
@Composable
private fun DetailsScreenErrorPreview() {
    DetailsScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        slot = PreviewData.Slot1,
        name = "",
        onNameChange = {},
        phone = "",
        onPhoneChange = {},
        uiState = UiState.Error("Invalid Phone Number"),
        onClickBookingConfirm = {}
    )
}

@Preview
@Composable
private fun DetailsScreenLoadingPreview() {
    DetailsScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        slot = PreviewData.Slot1,
        name = "",
        onNameChange = {},
        phone = "",
        onPhoneChange = {},
        uiState = UiState.Loading,
        onClickBookingConfirm = {}
    )
}