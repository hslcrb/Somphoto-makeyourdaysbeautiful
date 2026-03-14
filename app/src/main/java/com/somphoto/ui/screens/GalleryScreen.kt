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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.somphoto.R
import com.somphoto.data.JournalEntry
import com.somphoto.ui.components.SomBackground
import com.somphoto.ui.components.SomCard
import com.somphoto.ui.theme.PastelBlueMain
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(entriesFlow: Flow<List<JournalEntry>>, onBack: () -> Unit) {
    val entries by entriesFlow.collectAsState(initial = emptyList())

    SomBackground {
        Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        stringResource(id = R.string.your_gallery),
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
            if (entries.isEmpty()) {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(id = R.string.no_memories),
                        color = PastelBlueMain.copy(alpha = 0.7f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
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
    SomCard(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
        Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            if (entry.photoPath != null) {
                AsyncImage(
                    model = entry.photoPath,
                    contentDescription = null,
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(4.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                        modifier =
                                Modifier.weight(1f).fillMaxWidth().padding(4.dp).padding(bottom = 8.dp),
                        contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        tint = PastelBlueMain.copy(alpha = 0.3f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Text(
                    text = entry.text,
                    color = PastelBlueMain,
                    fontSize = 13.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}
