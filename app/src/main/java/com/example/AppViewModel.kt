package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.UserProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.pow

class AppViewModel(private val repository: UserProgressRepository) : ViewModel() {
    private val _showTranslations = MutableStateFlow(true)
    val showTranslations: StateFlow<Boolean> = _showTranslations.asStateFlow()

    val knowledgeScore: StateFlow<Int> = repository.progress
        .map { it?.knowledgeScore ?: 0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun toggleTranslations() {
        _showTranslations.value = !_showTranslations.value
    }

    fun adjustKnowledgeScore(baseAmount: Int) {
        viewModelScope.launch {
            val currentScore = knowledgeScore.value
            val currentLevel = if (currentScore >= 0) currentScore / 20 else (currentScore - 19) / 20
            
            // "every exp level you get mutiply's your exp income by 1.5x"
            val actualAmount = if (baseAmount > 0 && currentLevel > 0) {
                (baseAmount * 1.5.pow(currentLevel.toDouble())).toInt().coerceAtLeast(baseAmount)
            } else {
                baseAmount
            }
            
            repository.saveScore(currentScore + actualAmount)
        }
    }
}

class AppViewModelFactory(private val repository: UserProgressRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
