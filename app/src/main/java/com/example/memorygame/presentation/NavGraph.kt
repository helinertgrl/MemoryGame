package com.example.memorygame.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.data.AppDatabase
import com.example.memorygame.data.UserPreferences
import com.example.memorygame.presentation.game.GameScreen
import com.example.memorygame.presentation.game.GameViewModel
import com.example.memorygame.presentation.onboarding.OnboardingScreen
import com.example.memorygame.presentation.onboarding.OnboardingViewModel
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
    val userPrefs = UserPreferences(context)

    val savedName by userPrefs.nickname.collectAsState(initial = null)

    val startRoute = remember(savedName) {
        if (savedName == null) null
        else if (savedName!!.isNotEmpty()) Game.route
        else Main.route
    }

    val onboardingVM : OnboardingViewModel = viewModel(
        factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OnboardingViewModel(userPrefs) as T
            }
        }
    )

    val gameVM : GameViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GameViewModel(database.scoreDao(), userPrefs) as T
            }
        }
    )

    if (startRoute != null){
        NavHost(navController = navController, startDestination = Score.route){
            composable (route = Main.route) {
                OnboardingScreen(
                    navController = navController,
                    viewModel = onboardingVM )
            }
            composable(route = Game.route) {
                GameScreen(
                    viewModel = gameVM,
                    navController = navController)
            }
            composable (route = Score.route) {
                val scoreViewModel: ScoreViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ScoreViewModel(database.scoreDao()) as T
                        }
                    }
                )
                ScoreScreen(
                    scoreViewModel = scoreViewModel,
                    navController = navController)
            }
        }
    }
}


