package com.example.memorygame.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memorygame.presentation.game.GameScreen
import com.example.memorygame.presentation.game.GameViewModel
import com.example.memorygame.presentation.onboarding.OnboardingScreen
import com.example.memorygame.presentation.onboarding.OnboardingViewModel
import com.example.memorygame.presentation.score.ScoreScreen
import com.example.memorygame.presentation.score.ScoreViewModel
import kotlinx.serialization.Serializable

@Serializable
object MainRoute

@Serializable
object GameRoute

@Serializable
object ScoreRoute

sealed class Screen(
    val icon: ImageVector,
    val title: String
){
    data object Main: Screen(
        icon = Icons.Filled.Home,
        title = "Main"
    )
    data object Game: Screen(
        icon = Icons.Filled.Star,
        title = "Game"
    )
    data object Score: Screen(
        icon = Icons.Filled.CheckCircle,
        title = "Score"
    )
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val startViewModel: StartViewModel = hiltViewModel()
    val savedName by startViewModel.nickname.collectAsState(initial = null)

    if(savedName == null) return

    val startRoute = if (savedName!!.isEmpty()) MainRoute else GameRoute

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable<MainRoute> {
            val onboardingVM: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                navController = navController,
                viewModel = onboardingVM
            )
        }
        composable<GameRoute> {
            val gameVM: GameViewModel = hiltViewModel()
            GameScreen(
                viewModel = gameVM,
                navController = navController
            )
        }
        composable<ScoreRoute> {
            val scoreViewModel: ScoreViewModel = hiltViewModel()

            ScoreScreen(
                scoreViewModel = scoreViewModel,
                navController = navController
            )
        }
    }
}
