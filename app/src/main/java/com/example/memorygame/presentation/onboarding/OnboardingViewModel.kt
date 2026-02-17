package com.example.memorygame.presentation.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.domain.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: ScoreRepository): ViewModel() {

    private val _nickname = mutableStateOf("")
    val nickname: State<String> = _nickname

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun saveNickname(nickname: String){
        viewModelScope.launch {
            repository.saveNickname(nickname)
        }
    }
    fun onNicknameChange(newName: String){
        _nickname.value = newName
        if (newName.isNotBlank()) _errorMessage.value = null
    }

    fun isNameValid(): Boolean {
        val currentName = _nickname.value.trim()

        return when {
            currentName.isEmpty() -> {
                _errorMessage.value = "Nickname cannot be empty."
                false
            }
            currentName.length < 3 -> {
                _errorMessage.value = "Nickname must be at least 3 characters long."
                false
            }
            else -> {
                _errorMessage.value = null
                true
            }
        }
    }

    fun saveUserAndContinue(onSuccess: () -> Unit) {
        if (isNameValid()) {
            viewModelScope.launch {
                repository.saveNickname(nickname.value)
                onSuccess()
            }
        }
    }
}