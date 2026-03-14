package com.somphoto.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.somphoto.ui.theme.*

@Composable
fun SomCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = ShadowDark,
                spotColor = ShadowDark
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(SoftWhite, PastelPinkLight)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(2.dp, GlassWhite, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

fun Modifier.jellyClick(onClick: () -> Unit): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "JellyScale"
    )

    this
        .scale(scale)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
}

@Composable
fun GlossyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .jellyClick(onClick)
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PastelBlueLight,
                        PastelBlueMain,
                        PastelBlueMain.copy(alpha = 0.8f)
                    )
                )
            )
            .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
            .padding(horizontal = 24.dp, vertical = 12.dp),
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
    Box(
        modifier = modifier
            .jellyClick(onClick)
            .shadow(
                elevation = 12.dp,
                shape = CircleShape,
                ambientColor = PastelPinkDark,
                spotColor = PastelPinkDark
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(PastelPinkLight, PastelPinkMain, PastelPinkDark)
                ),
                shape = CircleShape
            )
            .border(4.dp, Color.White, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                .background(Color.Transparent, CircleShape)
        )
    }
}
