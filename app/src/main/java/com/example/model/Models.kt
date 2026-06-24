package com.example.model

import androidx.compose.ui.graphics.vector.ImageVector

// Target Audience Age Brackets
enum class AgeGroup(val label: String, val desc: String) {
    MIDDLE_SCHOOL("11-14 Years", "Middle School (Collège)"),
    HIGH_SCHOOL("15-17 Years", "High School (Lycée)"),
    GRADUATE("18-19 Years", "Bac Graduate (Fac/Classes Prépa)")
}

// Student Learning Goals / Focus Areas
enum class StudyGoal(val title: String, val iconName: String, val desc: String) {
    TECH_ENGLISH("Coding & Tech English", "Code", "Master terminology for programming, AI, and discord hackathons."),
    CONVERSATION("Global Conversation", "Forum", "Speak confidently without hesitation with peers around the world."),
    ACADEMIC_PREP("TOEFL & Bac Excellence", "School", "Ace the English national exam and prepare for university abroad.")
}

// Learning Proficiency Levels (CEFR-aligned)
enum class ProficiencyLevel(val code: String, val label: String, val details: String) {
    A1_A2("A1-A2", "Foundational / Explorer", "Perfect for building initial speech confidence, sentence structure, and base tech vocabulary."),
    B1_B2("B1-B2", "Professional / Builder", "Ideal for explaining technical logic, pitching startups, and moderate academic debating."),
    C1_C2("C1-C2", "Advanced / Innovator", "Focused on international leadership, professional tech writing, and scholarly argumentation.")
}

// Diagnostic Quiz Question Model
data class QuizQuestion(
    val id: Int,
    val questionText: String,
    val situationalContext: String, // Moroccan situational setting
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String // Educational rationale with Gen-Z friendly tips
)

// Task Type for curriculum
enum class TaskType {
    CORE_LESSON,     // Theoretical and vocabulary foundations
    CYBER_CHALLENGE, // Sandbox coding/vocabulary challenge
    MOROCCAN_PITCH   // Situational practical oral/written exercise
}

// Individual Course Task/Lesson
data class CourseTask(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val type: TaskType,
    val completed: Boolean = false
)

// Weekly Core Curriculum structure
data class CourseWeek(
    val weekNumber: Int,
    val weekTitle: String,
    val focusArea: String,
    val tasks: List<CourseTask>
)

// User Profile state
data class UserProfile(
    val name: String = "",
    val ageGroup: AgeGroup = AgeGroup.HIGH_SCHOOL,
    val goals: Set<StudyGoal> = emptySet(),
    val diagnosticScore: Int = 0,
    val diagnosticFinished: Boolean = false,
    val assignedLevel: ProficiencyLevel = ProficiencyLevel.A1_A2,
    val currentWeekIndex: Int = 0,
    val completedTaskIds: Set<String> = emptySet(),
    val hasGraduated: Boolean = false,
    val certificateId: String = "ACAD-2026-XXXX",
    val completionDate: String = ""
)
