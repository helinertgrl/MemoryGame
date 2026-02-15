package com.example.memorygame.presentation.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.memorygame.R
import com.example.memorygame.presentation.Game
import com.example.memorygame.presentation.Score
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel = viewModel()) {

    val emojilist = remember {
        val baseEmojis = listOf(
            R.drawable.alien,
            R.drawable.monkey,
            R.drawable.sad,
            R.drawable.poop,
            R.drawable.heart,
            R.drawable.evil,
            R.drawable.pumpkin,
            R.drawable.ghost
        )
        (baseEmojis + baseEmojis).shuffled()
    }

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
        if (viewModel.matchedCards.size == 16 && viewModel.matchedCards.size > 0) {
            viewModel.saveFinalScore{
                navController.navigate(Score.route){
                    popUpTo(Game.route) {inclusive = true}
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
            ScoreBoard(moves = viewModel.moves.value, score = viewModel.score.value)

            CardGrid(
                shuffledList = emojilist,
                faceUpCard = viewModel.faceUpCards,
                matchedCards = viewModel.matchedCards,
                onCardClick = { index -> viewModel.onCardClick(index, emojilist) }
            )
        }
    }
}
@Composable
fun ScoreBoard(moves: Int, score: Int) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hamle",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "$moves",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Skor",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "$score",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF18AD21)
            )
        }
    }
}

@Composable
fun CardGrid(
    shuffledList: List<Int>,
    faceUpCard: List<Int>,
    matchedCards: List<Int>,
    onCardClick: (Int) -> Unit
){
    val grouped = shuffledList.chunked(4)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        grouped.forEachIndexed { rowIndex, rowItems ->
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                rowItems.forEachIndexed { colIndex, _ ->
                    val globalIndex = rowIndex * 4 + colIndex
                    myCard(
                        imageRes = shuffledList[globalIndex],
                        isFaceUp = faceUpCard.contains(globalIndex),
                        isMatched = matchedCards.contains(globalIndex),
                        onClick = { onCardClick(globalIndex) }
                    )
                    if (colIndex < 3)
                        Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}

@Composable
fun myCard(
    imageRes: Int,
    isFaceUp: Boolean,
    isMatched: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(85.dp)
            .clickable(enabled = !isMatched && !isFaceUp) {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isFaceUp || isMatched) 0.dp else 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = if (isMatched) Color.LightGray else Color.DarkGray
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isFaceUp || isMatched) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = "emoji",
                    modifier = Modifier.size(50.dp)
                )
            } else {
                Text(
                    text = "?",
                    color = Color.White,
                    fontSize = 24.sp)
            }
        }
    }
}

