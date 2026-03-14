package com.somphoto.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somphoto.R
import com.somphoto.ui.theme.PastelBlueMain
import com.somphoto.ui.theme.PastelPinkMain
import com.somphoto.ui.theme.ShadowDark

enum class HeatmapView {
    Weekly, Monthly, Yearly
}

@Composable
fun HeatmapGraph(entries: List<com.somphoto.data.JournalEntry>, modifier: Modifier = Modifier) {
    var currentView by remember { mutableStateOf(HeatmapView.Weekly) }

    SomCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.memories_log),
                    color = PastelBlueMain,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.5f))
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ViewTab(
                        text = stringResource(id = R.string.weekly),
                        isSelected = currentView == HeatmapView.Weekly,
                        onClick = { currentView = HeatmapView.Weekly }
                    )
                    ViewTab(
                        text = stringResource(id = R.string.monthly),
                        isSelected = currentView == HeatmapView.Monthly,
                        onClick = { currentView = HeatmapView.Monthly }
                    )
                    ViewTab(
                        text = stringResource(id = R.string.yearly),
                        isSelected = currentView == HeatmapView.Yearly,
                        onClick = { currentView = HeatmapView.Yearly }
                    )
                }
            }

            val cellCount = when (currentView) {
                HeatmapView.Weekly -> 7
                HeatmapView.Monthly -> 31
                HeatmapView.Yearly -> 365
            }

            val columns = when (currentView) {
                HeatmapView.Weekly -> 7
                HeatmapView.Monthly -> 7
                HeatmapView.Yearly -> 20
            }

            Box(modifier = Modifier.heightIn(max = 240.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth(),
                    userScrollEnabled = currentView == HeatmapView.Yearly
                ) {
                    items(cellCount) { index ->
                        val isActive = index < entries.size 
                        HeatmapCell(hasPhoto = isActive)
                    }
                }
            }
        }
    }
}

@Composable
fun ViewTab(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor by animateColorAsState(
        if (isSelected) Color.White else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val textColor by animateColorAsState(
        if (isSelected) PastelPinkMain else PastelBlueMain,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeatmapCell(hasPhoto: Boolean) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .shadow(
                elevation = if (hasPhoto) 2.dp else 0.dp,
                shape = RoundedCornerShape(4.dp),
                ambientColor = ShadowDark,
                spotColor = ShadowDark
            )
            .background(
                color = if (hasPhoto) PastelPinkMain else Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(4.dp)
            )
    )
}
