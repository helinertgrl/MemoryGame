package com.example.memorygame.presentation.score.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.domain.model.UserScore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScoreRow(userScore: UserScore) {
    val dateString = remember(userScore.date) {
        SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(userScore.date))
    }

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = userScore.nickname,
            modifier = Modifier.weight(1.5f),
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${userScore.moves}",
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = dateString,
            modifier = Modifier.weight(1f),
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            color = Color.Gray
        )

    }
}
