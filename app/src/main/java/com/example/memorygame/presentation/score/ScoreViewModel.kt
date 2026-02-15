package com.example.memorygame.presentation.score

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.data.ScoreDao
import com.example.memorygame.domain.model.UserScore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScoreViewModel(private val scoreDao: ScoreDao): ViewModel() {
    val scores: StateFlow<List<UserScore>> = scoreDao.getAllScores()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun clearAllScores(){
        viewModelScope.launch {
            scoreDao.deleteAllScores()
        }
    }
}