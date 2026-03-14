package com.somphoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.somphoto.ui.components.HeatmapGraph
import com.somphoto.ui.components.SkeuomorphicShutterButton
import com.somphoto.ui.components.SomBackground
import com.somphoto.ui.theme.PastelBlueMain
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
                },
                floatingActionButton = {
                    SkeuomorphicShutterButton(
                            modifier = Modifier.size(80.dp),
                            onClick = onNavigateToJournaling
                    )
                }
        ) { paddingValues ->
            Column(
                    modifier =
                            Modifier.fillMaxSize()
                                    .padding(paddingValues)
                                    .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                        text = stringResource(id = R.string.welcome_msg),
                        color = PastelBlueMain,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(60.dp))
                HeatmapGraph(entries = entries, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
