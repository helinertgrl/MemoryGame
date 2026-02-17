package com.example.memorygame.presentation.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.memorygame.presentation.navigation.GameRoute
import com.example.memorygame.presentation.navigation.OnboardingRoute
import com.example.memorygame.presentation.score.components.ScoreRow

@Composable
fun ScoreScreen(
    navController: NavController,
    scoreViewModel: ScoreViewModel
) {
    val scores by scoreViewModel.scores.collectAsStateWithLifecycle()

    Box (
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xB58760E0),
                        Color(0xB5B284DA)
                    )
                )
            )
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Leaderboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Card(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Player", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Black, fontSize = 14.sp)
                        Text(text = "Moves", modifier = Modifier.weight(1f), fontWeight = FontWeight.Black, fontSize = 14.sp, textAlign = TextAlign.Center)
                        Text(text = "Date", modifier = Modifier.weight(1f), fontWeight = FontWeight.Black, fontSize = 14.sp, textAlign = TextAlign.End)
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(scores) { item ->
                            ScoreRow(item)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row (
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Button(
                    onClick = {
                        navController.navigate(GameRoute) {
                            popUpTo(GameRoute) {
                                inclusive = true
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B5CF6)
                    ),
                    elevation = ButtonDefaults.buttonElevation(2.dp)
                ) {
                    Text(text = "Play Again")
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate(OnboardingRoute) {
                            popUpTo(0)
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(text = "Different player",
                        color = Color(0xFF8B5CF6))
                }

                Button(
                    onClick = {scoreViewModel.clearAllScores()},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xDAB01B1A))
                ) {
                    Text(
                        text = "Delete all",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}