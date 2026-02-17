package com.example.memorygame.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.memorygame.presentation.game.components.CardGrid
import com.example.memorygame.presentation.game.components.ScoreBoard
import com.example.memorygame.presentation.navigation.GameRoute
import com.example.memorygame.presentation.navigation.ScoreRoute
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel) {

    val emojilist = viewModel.state.value.cards

    LaunchedEffect(Unit) {
        viewModel.reset()
    }

    LaunchedEffect(
        viewModel.faceUpCards.size
    ) {
        if (viewModel.faceUpCards.size == 2) {
            delay(800)
            viewModel.clearFaceUp()
        }
    }

    LaunchedEffect(viewModel.matchedCards.size) {
        if (viewModel.matchedCards.size == viewModel.state.value.cards.size && emojilist.isNotEmpty()) {
            viewModel.saveFinalScore{
                navController.navigate(ScoreRoute){
                    popUpTo(GameRoute) { inclusive = true}
                }
            }
        }
    }
    Box (
        modifier = Modifier.fillMaxSize()
            .background(Color(0xB5B284DA))
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScoreBoard(moves = viewModel.state.value.moves, score = viewModel.state.value.score)

            CardGrid(
                shuffledList = emojilist,
                faceUpCard = viewModel.faceUpCards,
                matchedCards = viewModel.matchedCards,
                onCardClick = { index -> viewModel.onCardClick(index) }
            )
        }
    }
}