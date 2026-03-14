package com.somphoto.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.somphoto.R
import com.somphoto.ui.components.SomBackground
import com.somphoto.ui.components.SomCard
import com.somphoto.ui.theme.*

enum class InputMode {
    Freeform,
    Guided
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalingScreen(onClose: () -> Unit, onSave: (String, String?, String?) -> Unit) {
    var currentMode by remember { mutableStateOf(InputMode.Freeform) }
    var textEntry by remember { mutableStateOf("") }
    var showQuestions by remember { mutableStateOf(false) }
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedPhotoUri = uri
        capturedBitmap = null
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        capturedBitmap = bitmap
        selectedPhotoUri = null
    }
    
    val q1 = stringResource(id = R.string.q1)
    val q2 = stringResource(id = R.string.q2)
    val q3 = stringResource(id = R.string.q3)
    
    var selectedQuestion by remember(q1) { mutableStateOf(q1) }

    SomBackground {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp).systemBarsPadding()) {
            // Top Bar
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = stringResource(id = R.string.cancel),
                        color = PastelBlueMain,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable { onClose() }
                )
                Text(
                        text = stringResource(id = R.string.save),
                        color = PastelBlueMain,
                        fontWeight = FontWeight.ExtraBold,
                        modifier =
                                Modifier.clickable {
                                    onSave(
                                            textEntry,
                                            selectedPhotoUri?.toString() ?: capturedBitmap?.toString(),
                                            if (currentMode == InputMode.Guided) selectedQuestion
                                            else null
                                    )
                                }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Photo Area with Dual Options
            SomCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
            ) {
                if (selectedPhotoUri != null || capturedBitmap != null) {
                    AsyncImage(
                        model = selectedPhotoUri ?: capturedBitmap,
                        contentDescription = "Selected Photo",
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp)).clickable { 
                            selectedPhotoUri = null
                            capturedBitmap = null
                        },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Camera Option
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { cameraLauncher.launch() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = null,
                                tint = PastelPinkMain,
                                modifier = Modifier.size(48.dp)
                            )
                            Text("Camera", color = PastelPinkDark, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        // Gallery Option
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { galleryLauncher.launch("image/*") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_gallery),
                                contentDescription = null,
                                tint = PastelBlueMain,
                                modifier = Modifier.size(48.dp)
                            )
                            Text("Gallery", color = PastelBlueMain, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mode Switcher
            Row(
                    modifier =
                            Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(50))
                                    .background(Color.White.copy(alpha = 0.6f))
                                    .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ModeToggleButton(
                        title = stringResource(id = R.string.freeform),
                        isSelected = currentMode == InputMode.Freeform,
                        onClick = {
                            currentMode = InputMode.Freeform
                            showQuestions = false
                        }
                )
                ModeToggleButton(
                        title = stringResource(id = R.string.guided),
                        isSelected = currentMode == InputMode.Guided,
                        onClick = { currentMode = InputMode.Guided }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                    modifier =
                            Modifier.fillMaxWidth()
                                    .animateContentSize(
                                            animationSpec =
                                                    spring(
                                                            dampingRatio =
                                                                    Spring.DampingRatioMediumBouncy,
                                                            stiffness = Spring.StiffnessLow
                                                    )
                                    )
            ) {
                if (currentMode == InputMode.Guided) {
                    SomCard(
                            modifier =
                                    Modifier.fillMaxWidth().clickable {
                                        showQuestions = !showQuestions
                                    }
                    ) {
                        Text(
                                text =
                                        if (showQuestions) stringResource(id = R.string.select_question)
                                        else selectedQuestion,
                                color = PastelPinkDark,
                                modifier = Modifier.padding(16.dp),
                                fontWeight = FontWeight.ExtraBold
                        )
                    }

                    AnimatedVisibility(visible = showQuestions) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            QuestionItem(q1) {
                                selectedQuestion = it
                                showQuestions = false
                            }
                            QuestionItem(q2) {
                                selectedQuestion = it
                                showQuestions = false
                            }
                            QuestionItem(q3) {
                                selectedQuestion = it
                                showQuestions = false
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                TextField(
                        value = textEntry,
                        onValueChange = { textEntry = it },
                        placeholder = {
                            Text(
                                    stringResource(id = R.string.placeholder_thoughts),
                                    color = PastelBlueMain.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 140.dp),
                        colors =
                                TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                                        unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = PastelPinkMain,
                                        focusedTextColor = PastelBlueMain,
                                        unfocusedTextColor = PastelBlueMain
                                ),
                        shape = RoundedCornerShape(24.dp)
                )
            }
        }
    }
}

@Composable
fun ModeToggleButton(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
            modifier =
                    Modifier.clip(RoundedCornerShape(50))
                            .background(if (isSelected) Color.White else Color.Transparent)
                            .clickable { onClick() }
                            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
                text = title,
                color = if (isSelected) PastelPinkMain else PastelBlueMain,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp
        )
    }
}

@Composable
fun QuestionItem(question: String, onClick: (String) -> Unit) {
    Text(
            text = question,
            color = PastelBlueMain,
            fontWeight = FontWeight.ExtraBold,
            modifier =
                    Modifier.fillMaxWidth()
                            .clickable { onClick(question) }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
    )
}
