package com.slotbooker.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.slotbooker.app.domain.model.Slot
import com.slotbooker.app.ui.screen.details.DetailsScreen
import com.slotbooker.app.ui.screen.home.HomeScreen
import com.slotbooker.app.ui.screen.slots.SlotsScreen
import kotlinx.serialization.json.Json

@Preview
@Composable
fun SlotBookerNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Home) {

        composable<Screen.Home> {
            HomeScreen(navController = navController)
        }

        composable<Screen.Slots> {
            SlotsScreen(navController = navController)
        }

        composable<Screen.Details> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Details>()

            DetailsScreen(
                slot = Json.decodeFromString<Slot>(args.slot),
                navController = navController
            )
        }
    }
}