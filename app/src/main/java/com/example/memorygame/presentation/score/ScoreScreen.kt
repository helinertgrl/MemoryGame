package com.example.memorygame.presentation.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorygame.domain.model.UserScore
import com.example.memorygame.presentation.game.GameViewModel

@Composable
fun ScoreScreen(
    scoreViewModel: ScoreViewModel
) {
    val scores by scoreViewModel.scores.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Liderlik Tablosu",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Oyuncu",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hamle",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Skor",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(scores) { item ->
                    ScoreRow(item)
                }
            }
        }
    }
}

@Composable
fun ScoreRow(userScore: UserScore) {
    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = userScore.nickname)
        Text(text = "${userScore.moves}")
        Text(text = "${userScore.score}",
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold
        )

    }
}
