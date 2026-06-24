package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun BlueprintScreen(modifier: Modifier = Modifier) {
    var activeTab by remember { mutableStateOf(0) }
    val tabs = listOf("Information Architecture", "User Journey Flow", "Visual System & Grids", "Value Copywriting")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CarbonBg)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "📐 ARCHITECT'S BLUEPRINT",
            style = MaterialTheme.typography.labelLarge,
            color = CyberMint,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Platform Specifications",
            style = MaterialTheme.typography.displaySmall,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Tab Selector (Sharp, Grid-Based)
        ScrollableTabRow(
            selectedTabIndex = activeTab,
            containerColor = Color.Transparent,
            contentColor = CyberMint,
            edgePadding = 0.dp,
            divider = {},
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, GridBorder), RoundedCornerShape(4.dp))
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = activeTab == index,
                    onClick = { activeTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (activeTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (activeTab == index) CyberMint else TextSecondary
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content (Scrollable Box)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, GridBorder), RoundedCornerShape(4.dp))
                .background(SurfaceSlate)
                .padding(16.dp)
        ) {
            when (activeTab) {
                0 -> InfoArchitectureContent()
                1 -> UserFlowContent()
                2 -> VisualSystemContent()
                3 -> ValueCopywritingContent()
            }
        }
    }
}

@Composable
fun InfoArchitectureContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "1. Information Architecture Map",
            style = MaterialTheme.typography.titleLarge,
            color = CyberMint,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Atlas Academy is structured as a modern high-conversion mobile-first application to minimize churn and maximize learner action.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Divider(color = GridBorder, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        // App Tree Representation
        val treeNodes = listOf(
            "📍 ROOT GATEWAY" to listOf(
                "├── 🚪 Splash / Value Proposition (High-impact hook for Moroccan youth)",
                "└── 🚀 Onboarding Form (Collects Name, Age Bracket [11-14 / 15-19] & Goals)"
            ),
            "📊 EVALUATION LAYER" to listOf(
                "├── 📝 Gamified Diagnostic (5 Situational questions set in Marrakech/Casablanca)",
                "├── 🔄 Scoring Engine (Calculates points and determines CEFR alignment)",
                "└── 🎯 Placement Feedback (Assigns A1-A2 Explorer, B1-B2 Builder, or C1-C2 Innovator)"
            ),
            "📚 CORE CURRICULUM SYLLABUS HUB" to listOf(
                "├── 🧭 Adaptive Syllabus (Instantly rewrites weeks 1-4 depending on evaluation)",
                "├── ⚡ Expandable Week Accordions (Week focus, Lesson content, Interactive tasks)",
                "└── 🏁 Completion Checkboxes (Saved to SharedPreferences for persistent streak)"
            ),
            "🎓 GRADUATION PORTAL" to listOf(
                "├── 📜 Certificate Vault (Locked until all 4-week tasks are checked off)",
                "├── 🏆 Interactive Certificate Card (Dynamic Name, CEFR stamp, Verification UUID)",
                "└── 📢 Share & Proof Trigger (For parents' Facebook/WhatsApp & student LinkedIn)"
            )
        )

        treeNodes.forEach { (category, children) ->
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge,
                color = MarrakeshSunset,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )
            children.forEach { child ->
                Text(
                    text = child,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Monospace,
                    color = TextPrimary,
                    modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
fun UserFlowContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "2. Core User Journey Flow",
            style = MaterialTheme.typography.titleLarge,
            color = AtlasBlue,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val steps = listOf(
            "Step 1: Onboarding Hook" to "Student enters via high-vibe Moroccan-tailored headlines. Self-selects Age Bracket (crucial for educational targeting) and Goal. SharedPreferences profile is initialized.",
            "Step 2: Gamified Level Evaluation" to "Seamless launch of a 5-question situational test. Questions are customized to Moroccan youth (e.g., café in Marrakech, Discord hackathon collaboration). Options give immediate visual feedback and high-value grammar/vocabulary rationales.",
            "Step 3: Level Placement" to "Scoring engine translates quiz results to CEFR levels. Instead of standard dry academic labels, we use: A1-A2 Explorer (foundations), B1-B2 Builder (tech/startup ready), or C1-C2 Innovator (advanced leadership).",
            "Step 4: 4-Week Dynamic Syllabus" to "The database or local state rewrites the 1-month curriculum. Students follow specialized modules (e.g., Pitching traditional rugs worldwide, writing Discord bug reports, drafting technical specs) and check them off week-by-week.",
            "Step 5: Graduation Verification" to "Upon completing all modules (or using the developer fast-track bypass), the 'Certificate of Success' is unlocked. It presents the ultimate proof of academic credibility for parents and modern career leverage for youth."
        )

        steps.forEachIndexed { index, (title, description) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(SurfaceSlateAlt, RoundedCornerShape(4.dp))
                        .border(1.dp, GridBorderActive, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (index + 1).toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberMint
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .width(2.dp)
                        .height(24.dp)
                        .background(GridBorder)
                )
            }
        }
    }
}

@Composable
fun VisualSystemContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "3. Visual & Style System Tokens",
            style = MaterialTheme.typography.titleLarge,
            color = CyberMint,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val tokens = listOf(
            Triple("Theme Background", "#0A0C10 (Carbon Bg)", "Deep dark, high-contrast, premium aesthetic that eliminates eye strain and appeals to tech-savvy youth."),
            Triple("Primary Accent", "#00FF88 (Cyber Mint)", "Neon Green representing growth, futuristic tech, and Morocco's national green reimagined."),
            Triple("Secondary Accent", "#00D2FF (Atlas Blue)", "Cool cyan blue reflecting the Atlantic coast and modern technical development."),
            Triple("Tertiary Accent", "#FFFF5722 (Marrakesh Orange)", "Cyber Saffron representing the energy, warmth, and vitality of Moroccan youth."),
            Triple("Structural Outlines", "1.dp Border, 4.dp Radius", "Strict grid lines aligned with high-contrast brutalism, rejecting soft gradients in favor of structural precision."),
            Triple("Interactive States", "Instant Ripple + Color Fill", "Buttons react instantly with color fills and Material Ripples to ensure tactile credibility and zero 'dead' clicks.")
        )

        tokens.forEach { (tokenName, tokenValue, tokenDesc) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceSlateAlt)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tokenName,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                        Box(
                            modifier = Modifier
                                .background(SurfaceSlate, RoundedCornerShape(2.dp))
                                .border(1.dp, GridBorder, RoundedCornerShape(2.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = tokenValue,
                                style = MaterialTheme.typography.labelMedium,
                                color = CyberMint,
                                fontSize = 10.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tokenDesc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun ValueCopywritingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "4. Value Proposition Copywriting Matrix",
            style = MaterialTheme.typography.titleLarge,
            color = MarrakeshSunset,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val copyBlocks = listOf(
            CopyBlock(
                target = "🎓 High School Grads (15-19 Years)",
                headline = "\"Upgrade Your Summer. Prep for the Global Tech Stage.\"",
                subhead = "Don't spend your break just gaming. Speak English that wins hackathons, drafts clean specs, and gets you ready for international university applications.",
                hook = "Focus: Coding terminology, startup pitch scripts, TOEFL prep, and LinkedIn profiles."
            ),
            CopyBlock(
                target = "🎒 Middle Schoolers (11-14 Years)",
                headline = "\"Speak English. Build Cool Things. Meet Global Friends.\"",
                subhead = "English is more than a classroom subject—it's your key to global Discord servers, Minecraft mods, and simple coding apps. Gain base confidence this summer!",
                hook = "Focus: Confident conversation, fun descriptive vocabulary, game terminology, and short videos."
            ),
            CopyBlock(
                target = "👨‍👩‍👦 Moroccan Parents (Academic Credibility)",
                headline = "\"A Standardized, Practical Path to Global Academic Readiness.\"",
                subhead = "We align our intensive course to the international CEFR standard. Your child receives structured daily modules, practical evaluations, and a verifiable Certificate of Success.",
                hook = "Focus: Academic rigor, parent dashboard reports, CV readiness, and official graduation."
            )
        )

        copyBlocks.forEach { block ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, GridBorder, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceSlateAlt)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = block.target,
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberMint,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = block.headline,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = block.subhead,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CarbonBg, RoundedCornerShape(2.dp))
                            .border(1.dp, GridBorder, RoundedCornerShape(2.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = block.hook,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MarrakeshSunset,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

data class CopyBlock(
    val target: String,
    val headline: String,
    val subhead: String,
    val hook: String
)
