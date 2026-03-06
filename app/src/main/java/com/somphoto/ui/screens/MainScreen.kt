package com.somphoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somphoto.ui.components.HeatmapGraph
import com.somphoto.ui.components.SkeuomorphicShutterButton
import com.somphoto.ui.components.WinterEastSeaMagicHourBackground
import com.somphoto.ui.theme.SoftCottonWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToJournaling: () -> Unit) {
    WinterEastSeaMagicHourBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Somphoto", color = SoftCottonWhite, fontWeight = FontWeight.Medium) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            floatingActionButton = {
                SkeuomorphicShutterButton(
                    modifier = Modifier.size(80.dp),
                    onClick = onNavigateToJournaling
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "A soft touch to your day.",
                    color = SoftCottonWhite,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(60.dp))
                HeatmapGraph(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
