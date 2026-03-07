package com.somphoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun GalleryScreen(entriesFlow: Flow<List<JournalEntry>>, onBack: () -> Unit) {
    val entries by entriesFlow.collectAsState(initial = emptyList())

    WinterEastSeaMagicHourBackground {
        Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        "Your Gallery",
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
            if (entries.isEmpty()) {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) { Text("No soft memories yet.", color = SoftCottonWhite.copy(alpha = 0.7f)) }
            } else {
                LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(paddingValues)
                ) { items(entries) { entry -> MemoryItem(entry) } }
            }
        }
    }
}

@Composable
fun MemoryItem(entry: JournalEntry) {
    NeumorphicCard(modifier = Modifier.fillMaxWidth().aspectRatio(1f), cornerRadius = 20.dp) {
        Column(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            // Photo Placeholder
            Box(
                    modifier =
                            Modifier.weight(1f).fillMaxWidth().padding(4.dp).padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
            ) { Text("📷", fontSize = 24.sp) }
            Text(
                    text = entry.text,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}
