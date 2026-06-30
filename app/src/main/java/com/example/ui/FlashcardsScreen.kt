package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AppViewModel
import com.example.data.DictionaryCategory
import com.example.data.DictionaryData
import com.example.data.DictionaryEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardsScreen(
    category: String,
    viewModel: AppViewModel,
    onNavigateBack: () -> Unit
) {
    val showTranslations by viewModel.showTranslations.collectAsState()
    
    val entries = remember(category) {
        if (category == "PHRASES") {
            DictionaryData.entries.filter { it.category == DictionaryCategory.PHRASES || it.chinuk.contains(" ") || it.chinuk.contains("-") }
        } else {
            DictionaryData.entries.filter { it.category != DictionaryCategory.PHRASES && !it.chinuk.contains(" ") && !it.chinuk.contains("-") }
        }
    }
    
    var currentEntry by remember { mutableStateOf(entries.random()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (category == "PHRASES") "Phrases" else "Words") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Spacer(modifier = Modifier.weight(1f))
            
            AnimatedContent(
                targetState = currentEntry,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "flashcard_animation"
            ) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (entry.emoji.isNotEmpty()) {
                            Text(text = entry.emoji, fontSize = 72.sp)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        
                        Text(
                            text = entry.chinuk,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        
                        if (showTranslations) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(modifier = Modifier.padding(horizontal = 32.dp))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = entry.english,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { currentEntry = entries.random() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text("Next", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
