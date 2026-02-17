package com.example.memorygame.presentation.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.data.ScoreDao
import com.example.memorygame.data.UserPreferences
import com.example.memorygame.domain.model.UserScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val scoreDao: ScoreDao,
    private val userPreferences: UserPreferences
): ViewModel() {
    private val _moves = mutableStateOf(0)
    val moves: State<Int> = _moves

    private val _isSaving = mutableStateOf(false)
    val isSaving : State<Boolean> = _isSaving

    private val _score = mutableStateOf(0)
    val score: State<Int> = _score

    val faceUpCards = mutableStateListOf<Int>()
    val matchedCards = mutableStateListOf<Int>()

    fun saveFinalScore(onComplete: () -> Unit){
        if (_isSaving.value) return
        _isSaving.value = true

        viewModelScope.launch {
            val name = userPreferences.nickname.first()
            if (name.isNotBlank()){
                val finalScore = UserScore(nickname = name, moves = _moves.value, score = _score.value)
                scoreDao.insertScore(finalScore)
                delay(500)
                _isSaving.value = false
                onComplete()
            }
        }
    }

    fun onCardClick(index: Int, shuffledList : List<Int>){
        if (faceUpCards.contains(index) || matchedCards.contains(index) || faceUpCards.size >=2) return

        faceUpCards.add(index)

        if (faceUpCards.size == 2){
            _moves.value++
            val first = faceUpCards[0]
            val second = faceUpCards[1]

            if (shuffledList[first] == shuffledList[second]) {
                matchedCards.add(first)
                matchedCards.add(second)
                _score.value++
                faceUpCards.clear()
            }
            else{

            }
        }
    }

    fun clearFaceUp() {
        faceUpCards.clear()
    }

    fun reset(){
        _moves.value = 0
        _score.value = 0
        faceUpCards.clear()
        matchedCards.clear()
    }
}
