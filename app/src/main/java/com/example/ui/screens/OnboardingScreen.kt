package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgeGroup
import com.example.model.StudyGoal
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.viewmodel.AtlasAcademyViewModel

@Composable
fun OnboardingScreen(
    viewModel: AtlasAcademyViewModel,
    onStartEvaluation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    var nameInput by remember { mutableStateOf(profile.name) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CarbonBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome Header & Morocco Accent
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        ) {
            Text(
                text = "🇲🇦 ATLAS ACADEMY",
                style = MaterialTheme.typography.labelLarge,
                color = CyberMint,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(MarrakeshSunset, RoundedCornerShape(2.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "SUMMER '26",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    fontSize = 9.sp
                )
            }
        }

        Text(
            text = "UPGRADE YOUR SKILLS",
            style = MaterialTheme.typography.displayMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = "Interactive Level Diagnostics & 4-Week Custom Learning Track for Moroccan Youth",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // CARD 1: Name Input
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "1. WHAT IS YOUR NAME?",
                    style = MaterialTheme.typography.labelLarge,
                    color = CyberMint,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = {
                        nameInput = it
                        viewModel.updateProfileNameAndAge(it, profile.ageGroup)
                    },
                    placeholder = { Text("Enter your full name...", color = TextMuted) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberMint,
                        unfocusedBorderColor = GridBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // CARD 2: Age Bracket (Critical for mapping target audience)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "2. CHOOSE YOUR LEVEL / AGE BRACKET",
                    style = MaterialTheme.typography.labelLarge,
                    color = AtlasBlue,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                AgeGroup.values().forEach { ageGroup ->
                    val isSelected = profile.ageGroup == ageGroup
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(
                                if (isSelected) SurfaceSlateAlt else Color.Transparent,
                                RoundedCornerShape(4.dp)
                            )
                            .border(
                                1.dp,
                                if (isSelected) CyberMint else GridBorder,
                                RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                focusManager.clearFocus()
                                viewModel.updateProfileNameAndAge(nameInput, ageGroup)
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                focusManager.clearFocus()
                                viewModel.updateProfileNameAndAge(nameInput, ageGroup)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = CyberMint,
                                unselectedColor = TextMuted
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = ageGroup.label,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isSelected) CyberMint else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = ageGroup.desc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }

        // CARD 3: Goals Selectors
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "3. SELECT YOUR CHOSEN PATHWAYS",
                    style = MaterialTheme.typography.labelLarge,
                    color = MarrakeshSunset,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StudyGoal.values().forEach { goal ->
                    val isSelected = profile.goals.contains(goal)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(
                                if (isSelected) SurfaceSlateAlt else Color.Transparent,
                                RoundedCornerShape(4.dp)
                            )
                            .border(
                                1.dp,
                                if (isSelected) MarrakeshSunset else GridBorder,
                                RoundedCornerShape(4.dp)
                            )
                            .clickable {
                                focusManager.clearFocus()
                                viewModel.toggleGoal(goal)
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (goal.iconName) {
                                "Code" -> Icons.Default.Code
                                "Forum" -> Icons.Default.Forum
                                else -> Icons.Default.School
                            },
                            contentDescription = goal.title,
                            tint = if (isSelected) MarrakeshSunset else TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = goal.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isSelected) MarrakeshSunset else TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = goal.desc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = {
                                focusManager.clearFocus()
                                viewModel.toggleGoal(goal)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MarrakeshSunset,
                                uncheckedColor = TextMuted,
                                checkmarkColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CTA Trigger Button
        val isValid = nameInput.trim().isNotEmpty() && profile.goals.isNotEmpty()
        Button(
            onClick = {
                focusManager.clearFocus()
                onStartEvaluation()
            },
            enabled = isValid,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CyberMint,
                contentColor = Color(0xFF030712),
                disabledContainerColor = SurfaceSlate,
                disabledContentColor = TextMuted
            ),
            border = BorderStroke(1.dp, if (isValid) CyberMint else GridBorder),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "LAUNCH DIAGNOSTIC LEVEL TEST",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go",
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
