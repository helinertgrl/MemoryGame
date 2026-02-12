package com.example.memorygame.presentation.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.memorygame.presentation.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = viewModel()
    ) {

    val nickname by viewModel.nickname
    val error by viewModel.errorMessage

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            border = BorderStroke(width = 2.dp, color = Color.Black),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray)
        ) {
            Text(
                text = "Ready to find to matches?",
                color = Color.White,
                fontSize = 27.sp,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(13.dp)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "The game is a simple memory game. " +
                        "Find matching emojis on the face-down cards that appear on the screen. " +
                        "If you find the matching emojis, you'll earn a score; if not, the card will be turned back over.",
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nickname,
            onValueChange = {viewModel.onNicknameChange(it)},
            label = {Text("Takma Adını Yaz")},
            isError = error != null,
            supportingText = {if (error != null) Text(error!!, color = Color.Red)},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (viewModel.isNameValid()) {
                    navController.navigate(Game.route)
                }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                text = "Start game",
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}