package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.AtlasAcademyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: AtlasAcademyViewModel = viewModel()) {
    val profile by viewModel.profile.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isQuizActive by viewModel.isQuizActive.collectAsState()

    // Ensure quiz resets if the profile finished state changes externally
    LaunchedEffect(profile.diagnosticFinished) {
        if (profile.diagnosticFinished) {
            viewModel.setIsQuizActive(false)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceSlateAlt,
                contentColor = CyberMint,
                tonalElevation = 8.dp,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { viewModel.setSelectedTab(0) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
                            contentDescription = "Portal"
                        )
                    },
                    label = { Text("Portal", style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF030712),
                        selectedTextColor = CyberMint,
                        indicatorColor = CyberMint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { viewModel.setSelectedTab(1) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 1) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                            contentDescription = "Syllabus"
                        )
                    },
                    label = { Text("Syllabus", style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF030712),
                        selectedTextColor = CyberMint,
                        indicatorColor = CyberMint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { viewModel.setSelectedTab(2) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 2) Icons.Filled.WorkspacePremium else Icons.Outlined.WorkspacePremium,
                            contentDescription = "Graduation"
                        )
                    },
                    label = { Text("Graduation", style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF030712),
                        selectedTextColor = CyberMint,
                        indicatorColor = CyberMint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { viewModel.setSelectedTab(3) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 3) Icons.Filled.Layers else Icons.Outlined.Layers,
                            contentDescription = "Blueprint"
                        )
                    },
                    label = { Text("Blueprint", style = MaterialTheme.typography.labelMedium) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF030712),
                        selectedTextColor = CyberMint,
                        indicatorColor = CyberMint,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CarbonBg)
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> {
                    if (isQuizActive) {
                        DiagnosticScreen(viewModel = viewModel)
                    } else if (!profile.diagnosticFinished) {
                        OnboardingScreen(
                            viewModel = viewModel,
                            onStartEvaluation = { viewModel.setIsQuizActive(true) }
                        )
                    } else {
                        ProfileDashboardScreen(
                            viewModel = viewModel,
                            onNavigateToSyllabus = { viewModel.setSelectedTab(1) },
                            onRetakeQuiz = { viewModel.setIsQuizActive(true) }
                        )
                    }
                }
                1 -> CurriculumScreen(
                    viewModel = viewModel,
                    onNavigateToCertificate = { viewModel.setSelectedTab(2) }
                )
                2 -> CertificateScreen(viewModel = viewModel)
                3 -> BlueprintScreen()
            }
        }
    }
}
