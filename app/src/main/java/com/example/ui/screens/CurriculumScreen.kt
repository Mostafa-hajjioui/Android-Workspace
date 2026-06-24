package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CourseWeek
import com.example.model.ProficiencyLevel
import com.example.model.StaticData
import com.example.model.TaskType
import com.example.ui.theme.*
import com.example.viewmodel.AtlasAcademyViewModel

@Composable
fun CurriculumScreen(
    viewModel: AtlasAcademyViewModel,
    onNavigateToCertificate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile.collectAsState()
    val scrollState = rememberScrollState()

    // Level Selector - allows the user/reviewer to instantly inspect other tracks
    var selectedViewLevel by remember { mutableStateOf(profile.assignedLevel) }

    // Sync selectedViewLevel when profile.assignedLevel changes
    LaunchedEffect(profile.assignedLevel) {
        selectedViewLevel = profile.assignedLevel
    }

    val weeks = StaticData.getCurriculumForLevel(selectedViewLevel)
    val totalTasksCount = weeks.flatMap { it.tasks }.size
    val completedLevelTasks = weeks.flatMap { it.tasks }.filter { profile.completedTaskIds.contains(it.id) }
    val completedTasksCount = completedLevelTasks.size
    val progressPct = if (totalTasksCount > 0) completedTasksCount.toFloat() / totalTasksCount else 0f

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CarbonBg)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Upper Title
        Text(
            text = "📚 CURRICULUM SYLLABUS HUB",
            style = MaterialTheme.typography.labelLarge,
            color = CyberMint,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Your 4-Week Track",
            style = MaterialTheme.typography.displaySmall,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Dynamic Level Toggle (Interactive blueprint explorer)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(BorderStroke(1.dp, GridBorder), RoundedCornerShape(4.dp))
                .background(SurfaceSlateAlt),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProficiencyLevel.values().forEach { level ->
                val isActive = selectedViewLevel == level
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedViewLevel = level }
                        .background(if (isActive) CyberMint else Color.Transparent)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = level.code,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) Color(0xFF030712) else TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Info Panel for active level
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        contentDescription = "Placement",
                        tint = AtlasBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "LEVEL: ${selectedViewLevel.label}",
                        style = MaterialTheme.typography.labelLarge,
                        color = AtlasBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedViewLevel.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Widget
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlateAlt)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SYLLABUS PROGRESS",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberMint
                    )
                    Text(
                        text = "$completedTasksCount / $totalTasksCount COMPLETED",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberMint
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { progressPct },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .border(1.dp, GridBorder, RoundedCornerShape(1.dp)),
                    color = CyberMint,
                    trackColor = CarbonBg,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Square
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Complete all lessons to unlock your Certificate of Success in the Graduation tab!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // The 4 Weeks Cards list
        weeks.forEach { week ->
            WeekAccordion(
                week = week,
                completedTaskIds = profile.completedTaskIds,
                onToggleTask = { viewModel.toggleTaskCompletion(it) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Graduation CTA Box / Developer Fast Track
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MarrakeshSunset, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎓 GRADUATION GATEWAY",
                    style = MaterialTheme.typography.labelLarge,
                    color = MarrakeshSunset,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                if (completedTasksCount == totalTasksCount) {
                    Text(
                        text = "Congratulations! You have successfully mastered all lessons. Your Certificate is ready!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Button(
                        onClick = onNavigateToCertificate,
                        colors = ButtonDefaults.buttonColors(containerColor = CyberMint, contentColor = Color(0xFF030712)),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("VIEW VERIFIABLE CERTIFICATE", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text(
                        text = "Moroccan Youth Fast-Track Bypass enabled for previewing of certificate.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.fastTrackGraduation()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MarrakeshSunset, contentColor = Color.White),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("FAST-TRACK GRADUATION 🎓", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun WeekAccordion(
    week: CourseWeek,
    completedTaskIds: Set<String>,
    onToggleTask: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceSlate)
    ) {
        Column {
            // Header Row clickable
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .background(SurfaceSlateAlt)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "WEEK ${week.weekNumber}: ${week.focusArea}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MarrakeshSunset,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = week.weekTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand/Collapse",
                    tint = TextSecondary
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    week.tasks.forEach { task ->
                        val isTaskCompleted = completedTaskIds.contains(task.id)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CarbonBg, RoundedCornerShape(4.dp))
                                .border(1.dp, if (isTaskCompleted) CyberMint else GridBorder, RoundedCornerShape(4.dp))
                                .clickable { onToggleTask(task.id) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Completion Circle/Checkbox
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        if (isTaskCompleted) CyberMint else Color.Transparent,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isTaskCompleted) CyberMint else TextMuted,
                                        RoundedCornerShape(4.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isTaskCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Done",
                                        tint = Color(0xFF030712),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 2.dp)
                                ) {
                                    // Task type badge
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                when (task.type) {
                                                    TaskType.CORE_LESSON -> SurfaceSlateAlt
                                                    TaskType.CYBER_CHALLENGE -> AtlasBlue.copy(alpha = 0.2f)
                                                    TaskType.MOROCCAN_PITCH -> MarrakeshSunset.copy(alpha = 0.2f)
                                                },
                                                RoundedCornerShape(2.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = when (task.type) {
                                                TaskType.CORE_LESSON -> "LESSON"
                                                TaskType.CYBER_CHALLENGE -> "CHALLENGE"
                                                TaskType.MOROCCAN_PITCH -> "PITCH 🇲🇦"
                                            },
                                            style = MaterialTheme.typography.labelMedium,
                                            fontSize = 9.sp,
                                            color = when (task.type) {
                                                TaskType.CORE_LESSON -> TextSecondary
                                                TaskType.CYBER_CHALLENGE -> AtlasBlue
                                                TaskType.MOROCCAN_PITCH -> MarrakeshSunset
                                            }
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = task.duration,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = TextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = if (isTaskCompleted) CyberMint else TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = task.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
