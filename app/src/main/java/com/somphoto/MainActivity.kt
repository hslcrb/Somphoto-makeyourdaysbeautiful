package com.somphoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.somphoto.data.JournalEntry
import com.somphoto.data.SomDatabase
import com.somphoto.ui.components.jellyClick
import com.somphoto.ui.screens.*
import com.somphoto.ui.theme.*
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
                    // Sensory Kitsch Drawer
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(320.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(PastelPinkLight, SoftWhite)
                                )
                            )
                            .clip(RoundedCornerShape(topEnd = 40.dp, bottomEnd = 40.dp))
                            .border(
                                width = 2.dp,
                                color = Color.White.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(topEnd = 40.dp, bottomEnd = 40.dp)
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                        ) {
                            // Header: Cloud/Flower Identity
                            Spacer(modifier = Modifier.height(32.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 40.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(Color.White, PastelPinkMain)
                                            )
                                        )
                                        .border(2.dp, Color.White, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_flower),
                                        contentDescription = null,
                                        tint = PastelPinkDark,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    color = PastelBlueMain,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            // Menu Items
                            DrawerItem(
                                iconId = R.drawable.ic_home,
                                label = stringResource(id = R.string.home),
                                isSelected = currentScreen == Screen.Main,
                                onClick = {
                                    currentScreen = Screen.Main
                                    scope.launch { drawerState.close() }
                                }
                            )
                            DrawerItem(
                                iconId = R.drawable.ic_gallery,
                                label = stringResource(id = R.string.gallery),
                                isSelected = currentScreen == Screen.Gallery,
                                onClick = {
                                    currentScreen = Screen.Gallery
                                    scope.launch { drawerState.close() }
                                }
                            )
                            DrawerItem(
                                iconId = R.drawable.ic_profile,
                                label = stringResource(id = R.string.profile),
                                isSelected = currentScreen == Screen.Profile,
                                onClick = {
                                    currentScreen = Screen.Profile
                                    scope.launch { drawerState.close() }
                                }
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            // Bottom Item
                            DrawerItem(
                                iconId = R.drawable.ic_settings,
                                label = stringResource(id = R.string.settings),
                                isSelected = currentScreen == Screen.Settings,
                                onClick = {
                                    currentScreen = Screen.Settings
                                    scope.launch { drawerState.close() }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
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

@Composable
fun DrawerItem(
    iconId: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .jellyClick(onClick = onClick)
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (isSelected) PastelPinkMain.copy(alpha = 0.4f) else Color.Transparent
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Transparent,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = if (isSelected) PastelPinkDark else PastelBlueMain,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                color = if (isSelected) PastelPinkDark else PastelBlueMain,
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold
            )
        }
    }
}
