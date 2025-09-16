package com.example.memorygame

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var moves = mutableStateOf(0)
    var score = mutableStateOf(0)

    fun increaseMove(){
        moves.value++
    }
    fun increaseScore(){
        score.value++
    }

    fun reset(){
        moves.value = 0
        score.value = 0
    }
}