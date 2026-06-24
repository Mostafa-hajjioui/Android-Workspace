package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AtlasAcademyViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("atlas_academy_prefs", Context.MODE_PRIVATE)

    // Reactive State representing the active user profile
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    // App Navigation & Session States
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _isQuizActive = MutableStateFlow(false)
    val isQuizActive: StateFlow<Boolean> = _isQuizActive.asStateFlow()

    // Interactive Quiz State
    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers.asStateFlow()

    private val _quizFeedbackMessage = MutableStateFlow<String?>(null)
    val quizFeedbackMessage: StateFlow<String?> = _quizFeedbackMessage.asStateFlow()

    private val _isCorrectAnswer = MutableStateFlow<Boolean?>(null)
    val isCorrectAnswer: StateFlow<Boolean?> = _isCorrectAnswer.asStateFlow()

    init {
        loadProfileFromPrefs()
    }

    private fun loadProfileFromPrefs() {
        val name = sharedPrefs.getString("user_name", "") ?: ""
        val ageGroupStr = sharedPrefs.getString("user_age_group", AgeGroup.HIGH_SCHOOL.name) ?: AgeGroup.HIGH_SCHOOL.name
        val ageGroup = try { AgeGroup.valueOf(ageGroupStr) } catch (e: Exception) { AgeGroup.HIGH_SCHOOL }
        
        val finishedDiag = sharedPrefs.getBoolean("diag_finished", false)
        val score = sharedPrefs.getInt("diag_score", 0)
        val levelStr = sharedPrefs.getString("assigned_level", ProficiencyLevel.A1_A2.name) ?: ProficiencyLevel.A1_A2.name
        val level = try { ProficiencyLevel.valueOf(levelStr) } catch (e: Exception) { ProficiencyLevel.A1_A2 }

        val goalsSet = sharedPrefs.getStringSet("user_goals", emptySet()) ?: emptySet()
        val goals = goalsSet.mapNotNull {
            try { StudyGoal.valueOf(it) } catch (e: Exception) { null }
        }.toSet()

        val completedSet = sharedPrefs.getStringSet("completed_tasks", emptySet()) ?: emptySet()
        val hasGraduated = sharedPrefs.getBoolean("has_graduated", false)
        val certId = sharedPrefs.getString("certificate_id", "ACAD-${UUID.randomUUID().toString().take(8).uppercase()}") ?: "ACAD-XXXX"
        val completionDate = sharedPrefs.getString("completion_date", "") ?: ""

        _profile.value = UserProfile(
            name = name,
            ageGroup = ageGroup,
            goals = goals,
            diagnosticScore = score,
            diagnosticFinished = finishedDiag,
            assignedLevel = level,
            completedTaskIds = completedSet,
            hasGraduated = hasGraduated,
            certificateId = certId,
            completionDate = completionDate
        )

        // Load navigation & session states
        _selectedTab.value = sharedPrefs.getInt("selected_tab", 0)
        _isQuizActive.value = sharedPrefs.getBoolean("is_quiz_active", false)
        _currentQuizIndex.value = sharedPrefs.getInt("quiz_current_index", 0)

        val savedAnswers = mutableMapOf<Int, Int>()
        StaticData.diagnosticQuestions.forEach { question ->
            val key = "quiz_ans_${question.id}"
            if (sharedPrefs.contains(key)) {
                savedAnswers[question.id] = sharedPrefs.getInt(key, -1)
            }
        }
        _selectedAnswers.value = savedAnswers
    }

    fun setSelectedTab(index: Int) {
        _selectedTab.value = index
        sharedPrefs.edit().putInt("selected_tab", index).apply()
    }

    fun setIsQuizActive(active: Boolean) {
        _isQuizActive.value = active
        sharedPrefs.edit().putBoolean("is_quiz_active", active).apply()
    }

    private fun saveProfileToPrefs(profile: UserProfile) {
        sharedPrefs.edit().apply {
            putString("user_name", profile.name)
            putString("user_age_group", profile.ageGroup.name)
            putStringSet("user_goals", profile.goals.map { it.name }.toSet())
            putBoolean("diag_finished", profile.diagnosticFinished)
            putInt("diag_score", profile.diagnosticScore)
            putString("assigned_level", profile.assignedLevel.name)
            putStringSet("completed_tasks", profile.completedTaskIds)
            putBoolean("has_graduated", profile.hasGraduated)
            putString("certificate_id", profile.certificateId)
            putString("completion_date", profile.completionDate)
            apply()
        }
    }

    fun updateProfileNameAndAge(name: String, ageGroup: AgeGroup) {
        val updated = _profile.value.copy(name = name, ageGroup = ageGroup)
        _profile.value = updated
        saveProfileToPrefs(updated)
    }

    fun toggleGoal(goal: StudyGoal) {
        val currentGoals = _profile.value.goals.toMutableSet()
        if (currentGoals.contains(goal)) {
            currentGoals.remove(goal)
        } else {
            currentGoals.add(goal)
        }
        val updated = _profile.value.copy(goals = currentGoals)
        _profile.value = updated
        saveProfileToPrefs(updated)
    }

    // Submit an answer for the diagnostic quiz question
    fun submitAnswer(questionId: Int, optionIndex: Int) {
        val currentAnswers = _selectedAnswers.value.toMutableMap()
        if (currentAnswers.containsKey(questionId)) return // prevent double answering

        currentAnswers[questionId] = optionIndex
        _selectedAnswers.value = currentAnswers

        sharedPrefs.edit().putInt("quiz_ans_$questionId", optionIndex).apply()

        val question = StaticData.diagnosticQuestions.find { it.id == questionId } ?: return
        val isCorrect = question.correctOptionIndex == optionIndex
        _isCorrectAnswer.value = isCorrect
        _quizFeedbackMessage.value = question.explanation
    }

    fun nextQuizQuestion() {
        val currentIndex = _currentQuizIndex.value
        _quizFeedbackMessage.value = null
        _isCorrectAnswer.value = null

        if (currentIndex < StaticData.diagnosticQuestions.size - 1) {
            val nextIndex = currentIndex + 1
            _currentQuizIndex.value = nextIndex
            sharedPrefs.edit().putInt("quiz_current_index", nextIndex).apply()
        } else {
            // End of Quiz - Calculate Level Placement
            calculatePlacementLevel()
        }
    }

    private fun calculatePlacementLevel() {
        var score = 0
        StaticData.diagnosticQuestions.forEach { question ->
            val userAns = _selectedAnswers.value[question.id]
            if (userAns == question.correctOptionIndex) {
                score++
            }
        }

        // Standardized Placement:
        // 0-1 Correct -> Beginner (A1-A2)
        // 2-3 Correct -> Intermediate (B1-B2)
        // 4-5 Correct -> Advanced (C1-C2)
        val level = when (score) {
            0, 1 -> ProficiencyLevel.A1_A2
            2, 3 -> ProficiencyLevel.B1_B2
            else -> ProficiencyLevel.C1_C2
        }

        val uniqueId = "ACAD-2026-${(1000..9999).random()}-${level.code}"

        val updated = _profile.value.copy(
            diagnosticScore = score,
            diagnosticFinished = true,
            assignedLevel = level,
            certificateId = uniqueId
        )
        _profile.value = updated
        saveProfileToPrefs(updated)

        // Reset active quiz state upon completion
        _isQuizActive.value = false
        _currentQuizIndex.value = 0
        sharedPrefs.edit().apply {
            putBoolean("is_quiz_active", false)
            putInt("quiz_current_index", 0)
            apply()
        }
    }

    // Toggle lesson/challenge completion in the 4-week course curriculum
    fun toggleTaskCompletion(taskId: String) {
        val currentCompleted = _profile.value.completedTaskIds.toMutableSet()
        if (currentCompleted.contains(taskId)) {
            currentCompleted.remove(taskId)
        } else {
            currentCompleted.add(taskId)
        }

        val updated = _profile.value.copy(completedTaskIds = currentCompleted)
        _profile.value = updated
        saveProfileToPrefs(updated)

        // Check if all tasks for the current level are completed to auto-trigger graduation
        checkGraduationEligibility()
    }

    private fun checkGraduationEligibility() {
        val levelTasks = StaticData.getCurriculumForLevel(_profile.value.assignedLevel)
            .flatMap { it.tasks }
            .map { it.id }
            .toSet()

        val completedLevelTasks = _profile.value.completedTaskIds.intersect(levelTasks)
        val allCompleted = completedLevelTasks.size == levelTasks.size && levelTasks.isNotEmpty()

        if (allCompleted && !_profile.value.hasGraduated) {
            val updated = _profile.value.copy(
                hasGraduated = true,
                completionDate = getFormattedCurrentDate()
            )
            _profile.value = updated
            saveProfileToPrefs(updated)
        }
    }

    // Fast track graduation so developers/reviewers don't have to check all boxes
    fun fastTrackGraduation() {
        val levelTasks = StaticData.getCurriculumForLevel(_profile.value.assignedLevel)
            .flatMap { it.tasks }
            .map { it.id }
            .toSet()

        val updatedCompleted = _profile.value.completedTaskIds + levelTasks
        val updated = _profile.value.copy(
            completedTaskIds = updatedCompleted,
            hasGraduated = true,
            completionDate = getFormattedCurrentDate()
        )
        _profile.value = updated
        saveProfileToPrefs(updated)
    }

    fun customizeCertificate(name: String, completionDate: String) {
        val updated = _profile.value.copy(
            name = name,
            completionDate = completionDate
        )
        _profile.value = updated
        saveProfileToPrefs(updated)
    }

    private fun getFormattedCurrentDate(): String {
        return try {
            val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
            sdf.format(Date())
        } catch (e: Exception) {
            "June 24, 2026"
        }
    }

    fun resetAcademy() {
        // Clear all SharedPreferences
        sharedPrefs.edit().clear().apply()

        _currentQuizIndex.value = 0
        _selectedAnswers.value = emptyMap()
        _quizFeedbackMessage.value = null
        _isCorrectAnswer.value = null
        _selectedTab.value = 0
        _isQuizActive.value = false

        val cleared = UserProfile(
            certificateId = "ACAD-${UUID.randomUUID().toString().take(8).uppercase()}"
        )
        _profile.value = cleared
        saveProfileToPrefs(cleared)
    }
}
