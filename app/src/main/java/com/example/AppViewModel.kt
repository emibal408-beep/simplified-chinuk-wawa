package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    private val _showTranslations = MutableStateFlow(true)
    val showTranslations: StateFlow<Boolean> = _showTranslations.asStateFlow()

    private val _knowledgeScore = MutableStateFlow(0)
    val knowledgeScore: StateFlow<Int> = _knowledgeScore.asStateFlow()

    fun toggleTranslations() {
        _showTranslations.value = !_showTranslations.value
    }

    fun adjustKnowledgeScore(amount: Int) {
        _knowledgeScore.value += amount
    }
}
