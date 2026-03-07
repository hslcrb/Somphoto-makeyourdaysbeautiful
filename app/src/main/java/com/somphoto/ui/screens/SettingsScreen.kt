package com.somphoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somphoto.ui.components.GlassmorphicContainer
import com.somphoto.ui.components.WinterEastSeaMagicHourBackground
import com.somphoto.ui.theme.SoftCottonWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    WinterEastSeaMagicHourBackground {
        Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        "Settings",
                                        color = SoftCottonWhite,
                                        fontWeight = FontWeight.Medium
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = onBack) {
                                    Text("←", color = SoftCottonWhite, fontSize = 24.sp)
                                }
                            },
                            colors =
                                    TopAppBarDefaults.topAppBarColors(
                                            containerColor = Color.Transparent
                                    )
                    )
                }
        ) { paddingValues ->
            Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsItem(
                        title = "Daily Nudge",
                        description = "Get a soft reminder to record your day."
                )
                SettingsItem(
                        title = "Cloud Sync",
                        description = "Back up your memories to the soft cloud."
                )
                SettingsItem(title = "Theme Options", description = "Adjust the magic hour colors.")

                Spacer(modifier = Modifier.weight(1f))

                Text(
                        text = "Somphoto v1.0",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = SoftCottonWhite.copy(alpha = 0.5f),
                        fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SettingsItem(title: String, description: String) {
    var checked by remember { mutableStateOf(true) }
    GlassmorphicContainer(modifier = Modifier.fillMaxWidth()) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(description, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
            Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors =
                            SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color.White.copy(alpha = 0.5f)
                            )
            )
        }
    }
}
