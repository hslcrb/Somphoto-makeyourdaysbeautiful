package com.somphoto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somphoto.ui.theme.DelicatePastelPink

@Composable
fun HeatmapGraph(entries: List<com.somphoto.data.JournalEntry>, modifier: Modifier = Modifier) {
    // Custom aesthetic heatmap integrated softly into a Neumorphic card
    NeumorphicCard(modifier = modifier, cornerRadius = 24.dp) {
        Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                    text = "Your Memories Log",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
            )
            val rows = 5
            val cols = 7

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (r in 0 until rows) {
                    Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                    ) {
                        for (c in 0 until cols) {
                            // Simple mock: if index < entry size, it's "active"
                            val index = r * cols + c
                            val isActive = index < entries.size
                            HeatmapCell(hasPhoto = isActive)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeatmapCell(hasPhoto: Boolean) {
    Box(
            modifier =
                    Modifier.size(36.dp)
                            .background(
                                    color =
                                            if (hasPhoto) DelicatePastelPink.copy(alpha = 0.8f)
                                            else Color(0xFFEBEBEB),
                                    shape = RoundedCornerShape(8.dp)
                            )
                            .shadow(
                                    elevation =
                                            if (hasPhoto) 4.dp
                                            else 0.dp, // Filled ones cast light shadows, empty
                                    // sit flat
                                    shape = RoundedCornerShape(8.dp),
                                    clip = false
                            ),
            contentAlignment = Alignment.Center
    ) {}
}
