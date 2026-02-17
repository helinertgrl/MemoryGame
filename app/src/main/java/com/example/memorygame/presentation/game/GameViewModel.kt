package com.example.memorygame.presentation.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.R
import com.example.memorygame.domain.model.UserScore
import com.example.memorygame.domain.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameState(
    val cards: List<Int> = emptyList(),
    val moves: Int = 0,
    val score: Int = 0,
    val isSaving: Boolean = false
)

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: ScoreRepository
): ViewModel() {

    private val _state = mutableStateOf(GameState())
    val state: State<GameState> = _state

    val faceUpCards = mutableStateListOf<Int>()
    val matchedCards = mutableStateListOf<Int>()

    init {
        generateCards()
    }

    private fun generateCards() {
        val baseEmojis = listOf(
            R.drawable.alien, R.drawable.monkey, R.drawable.sad,
            R.drawable.poop, R.drawable.heart, R.drawable.evil,
            R.drawable.pumpkin, R.drawable.ghost
        )
        _state.value = _state.value.copy(cards = (baseEmojis + baseEmojis).shuffled())
    }

    fun saveFinalScore(onComplete: () -> Unit){
        if (_state.value.isSaving) return
        _state.value = _state.value.copy(isSaving = true)

        viewModelScope.launch {
            val name = repository.getNickname().first()
            if (name.isNotBlank()){
                val finalScore = UserScore(
                    nickname = name,
                    moves = _state.value.moves,
                    score = _state.value.score
                )
                repository.insertScore(finalScore)
                delay(500)
                _state.value = _state.value.copy(isSaving = false)
                onComplete()
            }
        }
    }

    fun onCardClick(index: Int){

        val cards = _state.value.cards

        if (faceUpCards.size >= 2 || faceUpCards.contains(index) || matchedCards.contains(index)) return
        faceUpCards.add(index)

        if (faceUpCards.size == 2){
            _state.value = _state.value.copy(moves = _state.value.moves + 1)

            val first = faceUpCards[0]
            val second = faceUpCards[1]

            if (cards[first] == cards[second]) {
                matchedCards.add(first)
                matchedCards.add(second)
                _state.value = _state.value.copy(score = _state.value.score + 1)
                faceUpCards.clear()
            }
        }
    }

    fun clearFaceUp() {
        faceUpCards.clear()
    }

    fun reset(){
        _state.value = GameState()
        faceUpCards.clear()
        matchedCards.clear()
        generateCards()
    }
}
