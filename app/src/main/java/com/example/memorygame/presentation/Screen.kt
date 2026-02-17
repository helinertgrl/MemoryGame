package com.example.memorygame.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable object MainRoute
@Serializable object GameRoute
@Serializable object ScoreRoute

sealed class Screen(val icon: ImageVector, val title: String) {
    data object Main : Screen(icon = Icons.Filled.Home, title = "Main")
    data object Game : Screen(icon = Icons.Filled.Star, title = "Game")
    data object Score : Screen(icon = Icons.Filled.CheckCircle, title = "Score")
}