package com.example.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.AppViewModel
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    difficulty: String,
    viewModel: AppViewModel,
    onNavigateBack: () -> Unit
) {
    val showTranslations by viewModel.showTranslations.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    
    var score by remember { mutableStateOf(0) }
    var streak by remember { mutableStateOf(0) }
    
    // Super Easy State
    var superEasyTarget by remember { mutableStateOf<DictionaryEntry?>(null) }
    var superEasyChoices by remember { mutableStateOf<List<DictionaryEntry>>(emptyList()) }
    
    // Easy & Medium State
    var currentChainQuestion by remember { mutableStateOf<EmojiChainQuestion?>(null) }
    var chainChoices by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    
    // Hard Mode State
    var currentAssembleQuestion by remember { mutableStateOf<AssembleQuestion?>(null) }
    var assembledBlocks by remember { mutableStateOf<List<String>>(emptyList()) }
    var availablePaletteBlocks by remember { mutableStateOf<List<String>>(emptyList()) }
    
    // Professional Mode State
    var currentProQuestion by remember { mutableStateOf<ProQuestion?>(null) }
    var proChoices by remember { mutableStateOf<List<String>>(emptyList()) }
    
    // Feedback / Selection States
    var selectedStringAnswer by remember { mutableStateOf<String?>(null) }
    var selectedEntryAnswer by remember { mutableStateOf<DictionaryEntry?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var showExplanationState by remember { mutableStateOf(false) }

    fun generateQuestion() {
        selectedStringAnswer = null
        selectedEntryAnswer = null
        isCorrect = null
        showExplanationState = false
        
        when (difficulty) {
            "SUPEREASY" -> {
                // Single emoji to 3 choices
                val allEntries = DictionaryData.entries.filter { it.emoji.isNotEmpty() }
                if (allEntries.isNotEmpty()) {
                    val target = allEntries.random()
                    val wrongs = allEntries.filter { it.chinuk != target.chinuk }.shuffled().take(2)
                    superEasyTarget = target
                    superEasyChoices = (wrongs + target).shuffled()
                }
            }
            "EASY" -> {
                // 3-4 emoji chain to simplified Chinuk sentence (3 choices)
                val question = GameData.easyQuestions.random()
                currentChainQuestion = question
                val correctChoice = Pair(question.correctSentence, question.correctEnglish)
                chainChoices = (question.wrongSentences + correctChoice).shuffled()
            }
            "MEDIUM" -> {
                // 5-7 emoji chain representing full compound events (4 choices)
                val question = GameData.mediumQuestions.random()
                currentChainQuestion = question
                val correctChoice = Pair(question.correctSentence, question.correctEnglish)
                chainChoices = (question.wrongSentences + correctChoice).shuffled()
            }
            "HARD" -> {
                // English sentence to manually assemble by tapping word blocks
                val question = GameData.assembleQuestions.random()
                currentAssembleQuestion = question
                assembledBlocks = emptyList()
                availablePaletteBlocks = (question.correctBlocks + question.distractors).shuffled()
            }
            "PRO" -> {
                // Original, unsimplified historical orthography (no emoji) to 4 meanings
                val question = GameData.proQuestions.random()
                currentProQuestion = question
                proChoices = (question.wrongMeanings + question.correctMeaning).shuffled()
            }
        }
    }

    LaunchedEffect(Unit) {
        generateQuestion()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Play: ${difficulty.lowercase().replaceFirstChar { it.uppercase() }}") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("🔥 $streak", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Score: $score", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            
            // MAIN QUESTION AREA
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (difficulty) {
                    "SUPEREASY" -> {
                        superEasyTarget?.let { target ->
                            if (showTranslations) {
                                Text(
                                    text = target.english,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            Text(
                                text = target.emoji,
                                fontSize = 100.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "What is the Chinuk Wawa word for this?",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    "EASY", "MEDIUM" -> {
                        currentChainQuestion?.let { question ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (showTranslations) {
                                        Text(
                                            text = question.correctEnglish,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                    }
                                    Text(
                                        text = question.emojiChain,
                                        fontSize = 32.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )
                                    Text(
                                        text = "Translate this chain into the correct sentence structure:",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                    "HARD" -> {
                        currentAssembleQuestion?.let { question ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Translate into Chinuk Wawa:",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = question.englishSentence,
                                        style = MaterialTheme.typography.titleLarge,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                    "PRO" -> {
                        currentProQuestion?.let { question ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = question.historicalPhrase,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(bottom = 12.dp),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Standard historical orthography - Match the definition:",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // FEEDBACK FLASH BANNER
            if (isCorrect != null) {
                Surface(
                    color = if (isCorrect == true) Color(0xFFDCE5D1) else Color(0xFFF1E5DA),
                    border = BorderStroke(1.dp, if (isCorrect == true) Color(0xFFC5D0BA) else Color(0xFFDECDBB)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (isCorrect == true) "🎉 Dret! (Correct!)" else "❌ Wik! (Incorrect)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (isCorrect == true) Color(0xFF151E0E) else Color(0xFF4B2F20)
                        )
                    }
                }
            }

            // BUY ANSWER CONTROLS (COST 5 POINTS)
            if (isCorrect == null) {
                Button(
                    onClick = {
                        viewModel.adjustKnowledgeScore(-5)
                        isCorrect = true
                        when (difficulty) {
                            "SUPEREASY" -> {
                                selectedEntryAnswer = superEasyTarget
                            }
                            "EASY", "MEDIUM" -> {
                                selectedStringAnswer = currentChainQuestion?.correctSentence
                            }
                            "HARD" -> {
                                currentAssembleQuestion?.let { q ->
                                    assembledBlocks = q.correctBlocks
                                }
                            }
                            "PRO" -> {
                                selectedStringAnswer = currentProQuestion?.correctMeaning
                            }
                        }
                        coroutineScope.launch {
                            delay(1800)
                            generateQuestion()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("🔑 Buy Answer (Cost: 5 XP)", fontWeight = FontWeight.Bold)
                }
            }

            // SELECTION / CONTROLS AREA
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (difficulty) {
                    "SUPEREASY" -> {
                        superEasyChoices.forEach { entry ->
                            val isSelected = selectedEntryAnswer == entry
                            val buttonColor = when {
                                isSelected && isCorrect == true -> Color(0xFFDCE5D1)
                                isSelected && isCorrect == false -> Color(0xFFF1E5DA)
                                isCorrect != null && entry == superEasyTarget -> Color(0xFFDCE5D1)
                                else -> MaterialTheme.colorScheme.surface
                            }
                            val textColor = when {
                                isSelected && isCorrect == true -> Color(0xFF151E0E)
                                isSelected && isCorrect == false -> Color(0xFF4B2F20)
                                isCorrect != null && entry == superEasyTarget -> Color(0xFF151E0E)
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = selectedEntryAnswer == null) {
                                        selectedEntryAnswer = entry
                                        isCorrect = entry == superEasyTarget
                                        if (isCorrect == true) {
                                            score += 10 + (streak * 2)
                                            streak += 1
                                            viewModel.adjustKnowledgeScore(1)
                                        } else {
                                            streak = 0
                                            viewModel.adjustKnowledgeScore(-2)
                                        }
                                        coroutineScope.launch {
                                            delay(1800)
                                            generateQuestion()
                                        }
                                    },
                                colors = CardDefaults.cardColors(containerColor = buttonColor),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = entry.chinuk,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }

                    "EASY", "MEDIUM" -> {
                        chainChoices.forEach { choicePair ->
                            val choice = choicePair.first
                            val english = choicePair.second
                            val isSelected = selectedStringAnswer == choice
                            val buttonColor = when {
                                isSelected && isCorrect == true -> Color(0xFFDCE5D1)
                                isSelected && isCorrect == false -> Color(0xFFF1E5DA)
                                isCorrect != null && choice == currentChainQuestion?.correctSentence -> Color(0xFFDCE5D1)
                                else -> MaterialTheme.colorScheme.surface
                            }
                            val textColor = when {
                                isSelected && isCorrect == true -> Color(0xFF151E0E)
                                isSelected && isCorrect == false -> Color(0xFF4B2F20)
                                isCorrect != null && choice == currentChainQuestion?.correctSentence -> Color(0xFF151E0E)
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = selectedStringAnswer == null) {
                                        selectedStringAnswer = choice
                                        isCorrect = choice == currentChainQuestion?.correctSentence
                                        if (isCorrect == true) {
                                            score += 15 + (streak * 2)
                                            streak += 1
                                            viewModel.adjustKnowledgeScore(1)
                                        } else {
                                            streak = 0
                                            viewModel.adjustKnowledgeScore(-2)
                                        }
                                        coroutineScope.launch {
                                            delay(1800)
                                            generateQuestion()
                                        }
                                    },
                                colors = CardDefaults.cardColors(containerColor = buttonColor),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = choice,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }

                    "HARD" -> {
                        // Current assembled boxes area
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (assembledBlocks.isEmpty()) {
                                    Text(
                                        text = "Tap blocks below to build the sentence...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(start = 12.dp)
                                    )
                                } else {
                                    assembledBlocks.forEach { block ->
                                        Surface(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier
                                                .clickable(enabled = isCorrect == null) {
                                                    // Remove block
                                                    assembledBlocks = assembledBlocks - block
                                                    availablePaletteBlocks = (availablePaletteBlocks + block)
                                                }
                                        ) {
                                            Text(
                                                text = block,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Scattered Block Palette
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp
                        ) {
                            availablePaletteBlocks.forEach { block ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .clickable(enabled = isCorrect == null) {
                                            assembledBlocks = assembledBlocks + block
                                            availablePaletteBlocks = availablePaletteBlocks - block
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = block,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Bottom Action Controls for Hard Mode
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Reset
                            OutlinedButton(
                                onClick = {
                                    currentAssembleQuestion?.let { q ->
                                        assembledBlocks = emptyList()
                                        availablePaletteBlocks = (q.correctBlocks + q.distractors).shuffled()
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                enabled = isCorrect == null,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reset")
                            }

                            // Check Answer
                            Button(
                                onClick = {
                                    currentAssembleQuestion?.let { q ->
                                        val matches = assembledBlocks == q.correctBlocks
                                        isCorrect = matches
                                        if (matches) {
                                            score += 20 + (streak * 2)
                                            streak += 1
                                            viewModel.adjustKnowledgeScore(1)
                                        } else {
                                            streak = 0
                                            viewModel.adjustKnowledgeScore(-2)
                                        }
                                        coroutineScope.launch {
                                            delay(2000)
                                            generateQuestion()
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1.5f),
                                enabled = isCorrect == null && assembledBlocks.isNotEmpty(),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Verify")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Verify!")
                            }
                        }
                    }

                    "PRO" -> {
                        proChoices.forEach { choice ->
                            val isSelected = selectedStringAnswer == choice
                            val buttonColor = when {
                                isSelected && isCorrect == true -> Color(0xFFDCE5D1)
                                isSelected && isCorrect == false -> Color(0xFFF1E5DA)
                                isCorrect != null && choice == currentProQuestion?.correctMeaning -> Color(0xFFDCE5D1)
                                else -> MaterialTheme.colorScheme.surface
                            }
                            val textColor = when {
                                isSelected && isCorrect == true -> Color(0xFF151E0E)
                                isSelected && isCorrect == false -> Color(0xFF4B2F20)
                                isCorrect != null && choice == currentProQuestion?.correctMeaning -> Color(0xFF151E0E)
                                else -> MaterialTheme.colorScheme.onSurface
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = selectedStringAnswer == null) {
                                        selectedStringAnswer = choice
                                        isCorrect = choice == currentProQuestion?.correctMeaning
                                        if (isCorrect == true) {
                                            score += 25 + (streak * 3)
                                            streak += 1
                                            viewModel.adjustKnowledgeScore(1)
                                        } else {
                                            streak = 0
                                            viewModel.adjustKnowledgeScore(-2)
                                        }
                                        coroutineScope.launch {
                                            delay(1800)
                                            generateQuestion()
                                        }
                                    },
                                colors = CardDefaults.cardColors(containerColor = buttonColor),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = choice,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    crossAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.layout.Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
        val layoutWidth = constraints.maxWidth
        
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0
        
        placeables.forEach { placeable ->
            val spacing = if (currentRow.isEmpty()) 0 else mainAxisSpacing.roundToPx()
            if (currentRowWidth + spacing + placeable.width <= layoutWidth) {
                currentRow.add(placeable)
                currentRowWidth += spacing + placeable.width
            } else {
                rows.add(currentRow)
                currentRow = mutableListOf(placeable)
                currentRowWidth = placeable.width
            }
        }
        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
        }
        
        val rowHeights = rows.map { row -> row.maxOf { it.height } }
        val height = rowHeights.sum() + (rows.size - 1).coerceAtLeast(0) * crossAxisSpacing.roundToPx()
        
        layout(layoutWidth, height) {
            var y = 0
            rows.forEachIndexed { rowIndex, row ->
                var x = 0
                row.forEach { placeable ->
                    placeable.place(x, y)
                    x += placeable.width + mainAxisSpacing.roundToPx()
                }
                y += rowHeights[rowIndex] + crossAxisSpacing.roundToPx()
            }
        }
    }
}
