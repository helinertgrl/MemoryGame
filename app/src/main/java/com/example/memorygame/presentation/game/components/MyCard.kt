package com.example.memorygame.presentation.game.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyCard(
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