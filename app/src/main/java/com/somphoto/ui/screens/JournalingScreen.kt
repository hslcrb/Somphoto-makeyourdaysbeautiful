package com.somphoto.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.somphoto.ui.components.GlassmorphicContainer
import com.somphoto.ui.components.WinterEastSeaMagicHourBackground
import com.somphoto.ui.theme.DelicatePastelPink

enum class InputMode { Freeform, Guided }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalingScreen(onClose: () -> Unit) {
    var currentMode by remember { mutableStateOf(InputMode.Freeform) }
    var textEntry by remember { mutableStateOf("") }
    var showQuestions by remember { mutableStateOf(false) }
    var selectedQuestion by remember { mutableStateOf("What made you smile today?") }

    WinterEastSeaMagicHourBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .systemBarsPadding() 
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cancel", 
                    color = Color.White, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onClose() }
                )
                Text(
                    text = "Save", 
                    color = Color.White, 
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Photo Area Placeholder overlaying gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("Tap to add a soft memory", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dynamic Input Mode Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.3f))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ModeToggleButton(
                    title = "Freeform",
                    isSelected = currentMode == InputMode.Freeform,
                    onClick = { currentMode = InputMode.Freeform; showQuestions = false }
                )
                ModeToggleButton(
                    title = "Guided",
                    isSelected = currentMode == InputMode.Guided,
                    onClick = { currentMode = InputMode.Guided }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Smooth Animated Input Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                if (currentMode == InputMode.Guided) {
                    GlassmorphicContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showQuestions = !showQuestions }
                    ) {
                        Text(
                            text = if (showQuestions) "Select Today's Question" else selectedQuestion,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    
                    AnimatedVisibility(visible = showQuestions) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            QuestionItem("What made you smile today?") { selectedQuestion = it; showQuestions = false }
                            QuestionItem("Who did you think of the most?") { selectedQuestion = it; showQuestions = false }
                            QuestionItem("What felt like \"Som\" (cotton) today?") { selectedQuestion = it; showQuestions = false }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                TextField(
                    value = textEntry,
                    onValueChange = { textEntry = it },
                    placeholder = { 
                        Text("Let your thoughts flow delicately...", color = Color.White.copy(alpha = 0.6f)) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun ModeToggleButton(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = if (isSelected) DelicatePastelPink else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun QuestionItem(question: String, onClick: (String) -> Unit) {
    Text(
        text = question,
        color = Color.White.copy(alpha = 0.9f),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(question) }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}
