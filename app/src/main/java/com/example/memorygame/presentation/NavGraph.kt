package com.example.memorygame.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.data.AppDatabase
import com.example.memorygame.presentation.components.BottomNavi
import com.example.memorygame.presentation.game.GameScreen
import com.example.memorygame.presentation.onboarding.OnboardingScreen
import com.example.memorygame.presentation.score.ScoreScreen
import com.example.memorygame.presentation.score.ScoreViewModel

interface Destinations{
    val route: String
    val icon: ImageVector
    val title : String
}

object Main: Destinations{
    override val route = "Main"
    override val icon = Icons.Filled.Home
    override val title = "Main"
}

object Game: Destinations{
    override val route = "Game"
    override val icon = Icons.Filled.Star
    override val title = "Game"
}

object Score: Destinations{
    override val route = "Score"
    override val icon = Icons.Filled.CheckCircle
    override val title = "Score"
}

@Composable
fun memoryGame(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    Scaffold (
        bottomBar = {BottomNavi(navController)}
    ) {
        Box(modifier = Modifier.padding(it)){
            NavHost(navController = navController, startDestination = Main.route){
                composable (route = Main.route) {
                    OnboardingScreen(navController)
                }
                composable(route = Game.route) {
                    GameScreen(viewModel = viewModel())
                }
                composable (route = Score.route) {
                    val scoreViewModel: ScoreViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return ScoreViewModel(database.scoreDao()) as T
                            }
                        }
                    )
                    ScoreScreen(scoreViewModel = scoreViewModel)
                }
            }
        }
    }
}