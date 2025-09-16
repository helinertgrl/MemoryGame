package com.example.memorygame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(navController: NavController) {


    Scaffold(
        topBar = {TopAppBar(title = { Text(text = "Main Menu")} )}
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it),
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

            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {navController.navigate(Game.route)}) {
                Text(
                    text = "Start game",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun prev() {
    val navController = rememberNavController()
    MainMenu(navController)
}
