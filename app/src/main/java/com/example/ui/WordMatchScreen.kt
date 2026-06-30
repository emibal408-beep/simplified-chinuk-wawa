package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.DictionaryData
import com.example.data.DictionaryEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordMatchScreen(
    viewModel: AppViewModel,
    onNavigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val knowledgeScore by viewModel.knowledgeScore.collectAsState()

    var targetEntry by remember { mutableStateOf<DictionaryEntry?>(null) }
    var options by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAnsweredCorrectly by remember { mutableStateOf<Boolean?>(null) }
    var streak by remember { mutableStateOf(0) }

    fun generateNewMatchQuestion() {
        selectedOption = null
        isAnsweredCorrectly = null

        val entries = DictionaryData.entries
        if (entries.isNotEmpty()) {
            val correct = entries.random()
            targetEntry = correct

            // Filter out the correct option to get distractors, and choose 4 unique ones
            val distractors = entries
                .filter { it.english != correct.english }
                .map { it.english }
                .distinct()
                .shuffled()
                .take(4)

            // Shuffle the correct answer together with the distractors
            options = (distractors + correct.english).shuffled()
        }
    }

    LaunchedEffect(Unit) {
        generateNewMatchQuestion()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Match", fontWeight = FontWeight.Bold) },
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
                            Text("XP: $knowledgeScore", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Target chinuk wawa display
                targetEntry?.let { target ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        ),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(28.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = target.emoji,
                                fontSize = 64.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = target.chinuk,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Choose the correct English translation:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Feedback Banner
                if (isAnsweredCorrectly != null) {
                    val bannerBg = if (isAnsweredCorrectly == true) Color(0xFFDCE5D1) else Color(0xFFF1E5DA)
                    val bannerBorder = if (isAnsweredCorrectly == true) Color(0xFFC5D0BA) else Color(0xFFDECDBB)
                    val bannerText = if (isAnsweredCorrectly == true) "🎉 Dret! (+1 XP)" else "❌ Wik! (-2 XP)"
                    val textColor = if (isAnsweredCorrectly == true) Color(0xFF151E0E) else Color(0xFF4B2F20)

                    Surface(
                        color = bannerBg,
                        border = BorderStroke(1.dp, bannerBorder),
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
                                text = bannerText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = textColor
                            )
                        }
                    }
                }

                // Options list (English options)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    options.forEach { option ->
                        val isSelected = selectedOption == option
                        val isCorrectOption = targetEntry?.english == option

                        val buttonBg = when {
                            isSelected && isAnsweredCorrectly == true -> Color(0xFFDCE5D1)
                            isSelected && isAnsweredCorrectly == false -> Color(0xFFF1E5DA)
                            isAnsweredCorrectly != null && isCorrectOption -> Color(0xFFDCE5D1)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val textColor = when {
                            isSelected && isAnsweredCorrectly == true -> Color(0xFF151E0E)
                            isSelected && isAnsweredCorrectly == false -> Color(0xFF4B2F20)
                            isAnsweredCorrectly != null && isCorrectOption -> Color(0xFF151E0E)
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = selectedOption == null) {
                                    selectedOption = option
                                    val correct = option == targetEntry?.english
                                    isAnsweredCorrectly = correct
                                    if (correct) {
                                        streak += 1
                                        viewModel.adjustKnowledgeScore(1)
                                    } else {
                                        streak = 0
                                        viewModel.adjustKnowledgeScore(-2)
                                    }
                                    coroutineScope.launch {
                                        delay(1800)
                                        generateNewMatchQuestion()
                                    }
                                }
                                .testTag("match_option_${option.replace(" ", "_")}"),
                            colors = CardDefaults.cardColors(containerColor = buttonBg),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor,
                                    modifier = Modifier.weight(1f)
                                )
                                if (isAnsweredCorrectly != null && isCorrectOption) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct Translation",
                                        tint = Color(0xFF151E0E)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buy Answer Option (Cost: 5 XP)
                if (isAnsweredCorrectly == null) {
                    Button(
                        onClick = {
                            viewModel.adjustKnowledgeScore(-5)
                            isAnsweredCorrectly = true
                            selectedOption = targetEntry?.english
                            coroutineScope.launch {
                                delay(1800)
                                generateNewMatchQuestion()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("buy_answer_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("🔑 ", fontSize = 18.sp)
                            Text("Buy Answer (Cost: 5 XP)", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
