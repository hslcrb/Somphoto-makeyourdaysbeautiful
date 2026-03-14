package com.somphoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somphoto.R
import com.somphoto.ui.components.SomBackground
import com.somphoto.ui.components.SomCard
import com.somphoto.ui.theme.PastelBlueMain
import com.somphoto.ui.theme.PastelPinkMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    SomBackground {
        Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        stringResource(id = R.string.settings),
                                        color = PastelBlueMain,
                                        fontWeight = FontWeight.ExtraBold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = onBack) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back),
                                        contentDescription = "Back",
                                        tint = PastelBlueMain,
                                        modifier = Modifier.size(28.dp)
                                    )
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
                        title = stringResource(id = R.string.daily_nudge),
                        description = stringResource(id = R.string.nudge_desc)
                )
                SettingsItem(
                        title = stringResource(id = R.string.cloud_sync),
                        description = stringResource(id = R.string.cloud_desc)
                )
                SettingsItem(
                    title = stringResource(id = R.string.theme_options), 
                    description = stringResource(id = R.string.theme_desc)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                        text = stringResource(id = R.string.version),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = PastelBlueMain.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun SettingsItem(title: String, description: String) {
    var checked by remember { mutableStateOf(true) }
    SomCard(modifier = Modifier.fillMaxWidth()) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = PastelBlueMain, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(description, color = PastelBlueMain.copy(alpha = 0.7f), fontSize = 12.sp)
            }
            Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors =
                            SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = PastelPinkMain,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = PastelBlueMain.copy(alpha = 0.3f)
                            )
            )
        }
    }
}
