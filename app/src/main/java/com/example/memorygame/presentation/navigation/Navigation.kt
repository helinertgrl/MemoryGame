package com.example.memorygame.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val startViewModel: StartViewModel = hiltViewModel()
    val savedName by startViewModel.nickname.collectAsState(initial = null)

    if(savedName == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startRoute = if (savedName!!.isEmpty()) OnboardingRoute else GameRoute

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable<OnboardingRoute> {
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
