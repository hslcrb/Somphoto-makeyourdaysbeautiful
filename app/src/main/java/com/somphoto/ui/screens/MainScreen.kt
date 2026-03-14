package com.somphoto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.somphoto.R
import com.somphoto.data.JournalEntry
import com.somphoto.ui.components.HeatmapGraph
import com.somphoto.ui.components.SomBackground
import com.somphoto.ui.components.jellyClick
import com.somphoto.ui.theme.*
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    entriesFlow: Flow<List<JournalEntry>>,
    onNavigateToJournaling: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    val entries by entriesFlow.collectAsState(initial = emptyList())

    SomBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.app_name),
                            color = PastelBlueMain,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onOpenDrawer) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Menu",
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
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Welcome Msg at the Top
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.welcome_msg),
                    color = PastelBlueMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 24.sp
                )

                // 2. Heatmap positioned at "Upper-Middle"
                Spacer(modifier = Modifier.height(56.dp))
                
                HeatmapGraph(
                    entries = entries, 
                    modifier = Modifier.fillMaxWidth()
                )

                // 3. Small Pill-shaped button "Right Below" the Heatmap
                Spacer(modifier = Modifier.height(12.dp))
                
                Box(
                    modifier = Modifier
                        .jellyClick(onClick = onNavigateToJournaling)
                        .height(48.dp)
                        .fillMaxWidth(0.55f) // Smaller pill shape
                        .clip(RoundedCornerShape(24.dp)) // Semi-circular ends
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(PastelPinkMain, PastelPinkDark)
                            )
                        )
                        .border(
                            width = 2.dp, 
                            color = Color.White.copy(alpha = 0.8f), 
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.remember_me),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    
                    // Frutiger Aero Glossy Glass Highlight
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.4f),
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.1f)
                                    )
                                )
                            )
                    )
                }

                // 4. Push the rest to balance the "Upper-Middle" focus
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
