package com.slotbooker.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.slotbooker.app.ui.navigation.SlotBookerNavGraph
import com.slotbooker.app.ui.theme.SlotBookerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlotBookerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SlotBookerNavGraph()
                }
            }
        }
    }
}