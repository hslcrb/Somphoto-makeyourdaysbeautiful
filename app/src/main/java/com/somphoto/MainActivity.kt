package com.somphoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.somphoto.data.JournalEntry
import com.somphoto.data.SomDatabase
import com.somphoto.ui.screens.*
import com.somphoto.ui.theme.PastelBlueMain
import kotlinx.coroutines.launch

enum class Screen {
        Main,
        Journaling,
        Gallery,
        Profile,
        Settings
}

class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                val database = SomDatabase.getDatabase(this)
                val journalDao = database.journalDao()

                enableEdgeToEdge()
                setContent {
                        var currentScreen by remember { mutableStateOf(Screen.Main) }
                        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                        val scope = rememberCoroutineScope()

                        val entriesFlow = journalDao.getAllEntries()

                        ModalNavigationDrawer(
                                drawerState = drawerState,
                                drawerContent = {
                                        ModalDrawerSheet(
                                                drawerContainerColor =
                                                        Color.White.copy(alpha = 0.95f)
                                        ) {
                                                Text(
                                                        stringResource(id = R.string.app_name),
                                                        modifier = Modifier.padding(16.dp),
                                                        style =
                                                                MaterialTheme.typography
                                                                        .headlineMedium,
                                                        color = PastelBlueMain
                                                )
                                                Divider()
                                                NavigationDrawerItem(
                                                        icon = { Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = null, modifier = Modifier.size(24.dp)) },
                                                        label = { Text(stringResource(id = R.string.home)) },
                                                        selected = currentScreen == Screen.Main,
                                                        onClick = {
                                                                currentScreen = Screen.Main
                                                                scope.launch { drawerState.close() }
                                                        }
                                                )
                                                NavigationDrawerItem(
                                                        icon = { Icon(painter = painterResource(id = R.drawable.ic_gallery), contentDescription = null, modifier = Modifier.size(24.dp)) },
                                                        label = { Text(stringResource(id = R.string.gallery)) },
                                                        selected = currentScreen == Screen.Gallery,
                                                        onClick = {
                                                                currentScreen = Screen.Gallery
                                                                scope.launch { drawerState.close() }
                                                        }
                                                )
                                                NavigationDrawerItem(
                                                        icon = { Icon(painter = painterResource(id = R.drawable.ic_profile), contentDescription = null, modifier = Modifier.size(24.dp)) },
                                                        label = { Text(stringResource(id = R.string.profile)) },
                                                        selected = currentScreen == Screen.Profile,
                                                        onClick = {
                                                                currentScreen = Screen.Profile
                                                                scope.launch { drawerState.close() }
                                                        }
                                                )
                                                NavigationDrawerItem(
                                                        icon = { Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = null, modifier = Modifier.size(24.dp)) },
                                                        label = { Text(stringResource(id = R.string.settings)) },
                                                        selected = currentScreen == Screen.Settings,
                                                        onClick = {
                                                                currentScreen = Screen.Settings
                                                                scope.launch { drawerState.close() }
                                                        }
                                                )
                                        }
                                }
                        ) {
                                Surface {
                                        when (currentScreen) {
                                                Screen.Main ->
                                                        MainScreen(
                                                                entriesFlow = entriesFlow,
                                                                onNavigateToJournaling = {
                                                                        currentScreen =
                                                                                Screen.Journaling
                                                                },
                                                                onOpenDrawer = {
                                                                        scope.launch {
                                                                                drawerState.open()
                                                                        }
                                                                }
                                                        )
                                                Screen.Journaling ->
                                                        JournalingScreen(
                                                                onClose = {
                                                                        currentScreen = Screen.Main
                                                                },
                                                                onSave = { text, photo, question ->
                                                                        lifecycleScope.launch {
                                                                                journalDao
                                                                                        .insertEntry(
                                                                                                JournalEntry(
                                                                                                        timestamp =
                                                                                                                System.currentTimeMillis(),
                                                                                                        photoPath =
                                                                                                                photo,
                                                                                                        text =
                                                                                                                text,
                                                                                                        question =
                                                                                                                question
                                                                                                )
                                                                                        )
                                                                                currentScreen =
                                                                                        Screen.Main
                                                                        }
                                                                }
                                                        )
                                                Screen.Gallery ->
                                                        GalleryScreen(
                                                                entriesFlow = entriesFlow,
                                                                onBack = {
                                                                        currentScreen = Screen.Main
                                                                }
                                                        )
                                                Screen.Profile ->
                                                        ProfileScreen(
                                                                entriesFlow = entriesFlow,
                                                                onBack = {
                                                                        currentScreen = Screen.Main
                                                                }
                                                        )
                                                Screen.Settings ->
                                                        SettingsScreen(
                                                                onBack = {
                                                                        currentScreen = Screen.Main
                                                                }
                                                        )
                                        }
                                }
                        }
                }
        }
}
