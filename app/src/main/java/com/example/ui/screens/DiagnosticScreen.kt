package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizQuestion
import com.example.model.StaticData
import com.example.ui.theme.*
import com.example.viewmodel.AtlasAcademyViewModel

@Composable
fun DiagnosticScreen(
    viewModel: AtlasAcademyViewModel,
    modifier: Modifier = Modifier
) {
    val currentQuizIndex by viewModel.currentQuizIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val feedbackMessage by viewModel.quizFeedbackMessage.collectAsState()
    val isCorrect by viewModel.isCorrectAnswer.collectAsState()

    val totalQuestions = StaticData.diagnosticQuestions.size
    val currentQuestion = StaticData.diagnosticQuestions[currentQuizIndex]
    val selectedOptionIndex = selectedAnswers[currentQuestion.id]
    val isAnswered = selectedOptionIndex != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CarbonBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Upper Title
        Text(
            text = "⚡ EVALUATION ENGINE",
            style = MaterialTheme.typography.labelLarge,
            color = CyberMint,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Diagnostic Placement",
            style = MaterialTheme.typography.displaySmall,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Custom Brutalist Block Progress Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until totalQuestions) {
                val isActive = i == currentQuizIndex
                val isDone = selectedAnswers[StaticData.diagnosticQuestions[i].id] != null
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .background(
                            when {
                                isActive -> CyberMint
                                isDone -> AtlasBlue
                                else -> SurfaceSlateAlt
                            },
                            RoundedCornerShape(1.dp)
                        )
                        .border(
                            1.dp,
                            if (isActive) CyberMint else GridBorder,
                            RoundedCornerShape(1.dp)
                        )
                )
            }
        }

        // Location Context Badge
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlateAlt)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = "Context",
                    tint = MarrakeshSunset,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = currentQuestion.situationalContext,
                    style = MaterialTheme.typography.labelLarge,
                    color = MarrakeshSunset,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Question Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "QUESTION ${currentQuizIndex + 1} OF $totalQuestions",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = currentQuestion.questionText,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Options Selection Column
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            currentQuestion.options.forEachIndexed { index, optionText ->
                val optionAnswered = selectedOptionIndex == index
                val isCorrectIndex = index == currentQuestion.correctOptionIndex

                // Determine border and background colors dynamically based on answering state
                val (borderColor, containerColor, textColor) = when {
                    !isAnswered -> {
                        Triple(GridBorder, SurfaceSlate, TextPrimary)
                    }
                    optionAnswered && isCorrectIndex -> {
                        Triple(CyberMint, SurfaceSlateAlt, CyberMint)
                    }
                    optionAnswered && !isCorrectIndex -> {
                        Triple(ErrorRed, SurfaceSlateAlt, ErrorRed)
                    }
                    isCorrectIndex -> {
                        Triple(CyberMint, SurfaceSlateAlt, CyberMint)
                    }
                    else -> {
                        Triple(GridBorder, CarbonBg, TextMuted)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(containerColor, RoundedCornerShape(4.dp))
                        .border(1.dp, borderColor, RoundedCornerShape(4.dp))
                        .clickable(enabled = !isAnswered) {
                            viewModel.submitAnswer(currentQuestion.id, index)
                        }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(CarbonBg, RoundedCornerShape(12.dp))
                            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = ('A' + index).toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isAnswered && !isCorrectIndex && !optionAnswered) TextMuted else TextPrimary,
                        modifier = Modifier.weight(1f)
                    )

                    // Correct/Incorrect Icons
                    if (isAnswered) {
                        if (optionAnswered && isCorrectIndex) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Correct",
                                tint = CyberMint,
                                modifier = Modifier.size(20.dp)
                            )
                        } else if (optionAnswered && !isCorrectIndex) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Incorrect",
                                tint = ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                        } else if (isCorrectIndex) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Correct Solution",
                                tint = CyberMint,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Detailed Feedback Section (Animated Entry)
        AnimatedVisibility(
            visible = isAnswered,
            enter = fadeIn() + expandVertically()
        ) {
            feedbackMessage?.let { msg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .border(1.dp, if (isCorrect == true) CyberMint else MarrakeshSunset, RoundedCornerShape(4.dp)),
                    colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = if (isCorrect == true) "✅ SOLUTION CRITIQUE: CORRECT" else "💡 EDUCATIONAL RATIONALE",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isCorrect == true) CyberMint else MarrakeshSunset,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Next Action Button
        if (isAnswered) {
            Button(
                onClick = { viewModel.nextQuizQuestion() },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberMint,
                    contentColor = Color(0xFF030712)
                ),
                border = BorderStroke(1.dp, CyberMint),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (currentQuizIndex == totalQuestions - 1) "CALCULATE PLACEMENT LEVEL" else "NEXT QUESTION",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
