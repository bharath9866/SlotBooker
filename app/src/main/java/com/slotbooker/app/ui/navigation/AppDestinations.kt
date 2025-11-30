package com.slotbooker.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Home : Screen()

    @Serializable
    data object Slots : Screen()

    @Serializable
    data class Details(val slot: String) : Screen()
}