package com.example.memorygame.presentation.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OnboardingViewModel: ViewModel() {

    private val _nickname = mutableStateOf("")
    val nickname: State<String> = _nickname

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun onNicknameChange(newName: String){
        _nickname.value = newName
        if (newName.isNotBlank()) _errorMessage.value = null
    }

    fun isNameValid(): Boolean{
        return if (_nickname.value.trim().length < 3){
            _errorMessage.value = "Nick en az 3 karakter olmalıdır!"
            false
        } else {
            true
        }
    }
}