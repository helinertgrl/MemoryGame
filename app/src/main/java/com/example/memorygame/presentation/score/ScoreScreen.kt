package com.example.memorygame.presentation.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.memorygame.domain.model.UserScore
import com.example.memorygame.presentation.Game
import com.example.memorygame.presentation.Main
import com.example.memorygame.presentation.Score
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScoreScreen(
    navController: NavController,
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
                            text = "Tarih",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(scores) { item ->
                    ScoreRow(item)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = {
                    navController.navigate(Game.route) {
                        popUpTo(Score.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = "Tekrar Oyna")
            }

            OutlinedButton(
                onClick = {
                    navController.navigate(Main.route) {
                        popUpTo(0)
                    }
                }
            ) {
                Text(text = "Farklı Oyuncu")
            }
        }
    }
}

@Composable
fun ScoreRow(userScore: UserScore) {
    val dateString = remember(userScore.date) {
        SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(userScore.date))
    }

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = userScore.nickname,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)
        Text(text = "${userScore.moves}",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)
        Text(text = dateString,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp)

    }
}
