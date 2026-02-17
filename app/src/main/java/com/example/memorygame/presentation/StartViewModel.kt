package com.example.memorygame.presentation

import androidx.lifecycle.ViewModel
import com.example.memorygame.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val userPreferences: UserPreferences
): ViewModel() {
    val nickname: Flow<String> = userPreferences.nickname
}