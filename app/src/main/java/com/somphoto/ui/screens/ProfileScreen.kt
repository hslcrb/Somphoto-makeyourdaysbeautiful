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
import com.somphoto.data.JournalEntry
import com.somphoto.ui.components.NeumorphicCard
import com.somphoto.ui.components.WinterEastSeaMagicHourBackground
import com.somphoto.ui.theme.SoftCottonWhite
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(entriesFlow: Flow<List<JournalEntry>>, onBack: () -> Unit) {
    val entries by entriesFlow.collectAsState(initial = emptyList())
    val totalEntries = entries.size
    val lastEntryDate = if (entries.isNotEmpty()) "Today" else "N/A"

    WinterEastSeaMagicHourBackground {
        Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        "Your Profile",
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
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Avatar Placeholder
                NeumorphicCard(modifier = Modifier.size(120.dp), cornerRadius = 60.dp) {
                    Text("📷", fontSize = 48.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                        text = "A Gentle Dreamer",
                        color = SoftCottonWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Stats Cards
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                            label = "Memories",
                            value = totalEntries.toString(),
                            modifier = Modifier.weight(1f)
                    )
                    StatCard(
                            label = "Last Visit",
                            value = lastEntryDate,
                            modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                NeumorphicCard(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        cornerRadius = 24.dp
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Current Streak", color = Color.Gray, fontSize = 14.sp)
                        Text(
                                "3 Days",
                                color = Color(0xFFF68084),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    NeumorphicCard(modifier = modifier.height(100.dp), cornerRadius = 24.dp) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, color = Color.Gray, fontSize = 14.sp)
            Text(value, color = Color.DarkGray, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
