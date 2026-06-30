package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    private val _showTranslations = MutableStateFlow(true)
    val showTranslations: StateFlow<Boolean> = _showTranslations.asStateFlow()

    fun toggleTranslations() {
        _showTranslations.value = !_showTranslations.value
    }
}
