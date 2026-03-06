package com.somphoto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.somphoto.ui.theme.GlassBorder
import com.somphoto.ui.theme.GlassWhite
import com.somphoto.ui.theme.SoftCottonWhite

@Composable
fun NeumorphicCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    elevation: Dp = 8.dp,
    content: @Composable BoxScope.() -> Unit
) {
    // Soft, extruded shadow card mimicking "Som" cotton texture
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color(0xFFB0BCC2),
                spotColor = Color(0xFFB0BCC2)
            )
            .background(SoftCottonWhite, shape = RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GlassmorphicContainer(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit
) {
    // Frosted glass effect overlaying the beautiful Magic Hour gradient
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(GlassWhite)
            .border(1.dp, GlassBorder, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun SkeuomorphicShutterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // A tactile realistic camera button with satisfying physical aesthetics
    Box(
        modifier = modifier
            .clickable(role = Role.Button, onClick = onClick)
            .padding(16.dp)
            .shadow(
                elevation = 12.dp, 
                shape = CircleShape, 
                ambientColor = Color.Black.copy(alpha=0.6f), 
                spotColor = Color.Black.copy(alpha=0.6f)
            )
            .background(Color(0xFFE0E5EC), CircleShape)
            .border(4.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Inner glossy center
        Box(
            modifier = Modifier
                .padding(8.dp)
                .matchParentSize()
                .background(Color(0xFFF0F4F8), CircleShape)
                .border(2.dp, Color(0xFFD1D9E6), CircleShape)
        )
    }
}
