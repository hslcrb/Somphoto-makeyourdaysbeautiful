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
import com.somphoto.data.JournalEntry
import com.somphoto.ui.components.SomBackground
import com.somphoto.ui.components.SomCard
import com.somphoto.ui.theme.PastelBlueMain
import com.somphoto.ui.theme.PastelPinkDark
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(entriesFlow: Flow<List<JournalEntry>>, onBack: () -> Unit) {
    val entries by entriesFlow.collectAsState(initial = emptyList())
    val totalEntries = entries.size
    val lastEntryDate = if (entries.isNotEmpty()) stringResource(id = R.string.today) else stringResource(id = R.string.na)

    SomBackground {
        Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        stringResource(id = R.string.your_profile),
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
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Avatar Placeholder
                SomCard(modifier = Modifier.size(120.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_flower),
                        contentDescription = null,
                        tint = Color.Unspecified, // Keep SVG colors if any, or use tint
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                        text = stringResource(id = R.string.dreamer_name),
                        color = PastelBlueMain,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Stats Cards
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                            label = stringResource(id = R.string.memories_stat),
                            value = totalEntries.toString(),
                            modifier = Modifier.weight(1f)
                    )
                    StatCard(
                            label = stringResource(id = R.string.last_visit),
                            value = lastEntryDate,
                            modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SomCard(
                        modifier = Modifier.fillMaxWidth().height(120.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(id = R.string.current_streak),
                            color = PastelBlueMain,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                                stringResource(id = R.string.days_count, 3),
                                color = PastelPinkDark,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    SomCard(modifier = modifier.height(110.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, color = PastelBlueMain, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(value, color = PastelPinkDark, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
