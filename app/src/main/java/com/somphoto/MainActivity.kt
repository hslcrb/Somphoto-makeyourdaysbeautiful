package com.somphoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.somphoto.ui.screens.JournalingScreen
import com.somphoto.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showJournaling by remember { mutableStateOf(false) }
            if (showJournaling) {
                JournalingScreen(onClose = { showJournaling = false })
            } else {
                MainScreen(onNavigateToJournaling = { showJournaling = true })
            }
        }
    }
}
