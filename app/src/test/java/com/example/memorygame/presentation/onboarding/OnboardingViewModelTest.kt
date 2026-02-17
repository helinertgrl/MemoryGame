package com.example.memorygame.presentation.onboarding

import com.example.memorygame.domain.repository.ScoreRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel
    private val repository: ScoreRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        // ViewModel içindeki coroutine'lerin test ortamında çalışması için
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingViewModel(repository)
    }

    @Test
    fun `isNameValid returns false for empty nickname`() {
        viewModel.onNicknameChange("")
        assertFalse(viewModel.isNameValid())
    }

    @Test
    fun `isNameValid returns true for valid nickname`() {
        viewModel.onNicknameChange("Helin")
        assertTrue(viewModel.isNameValid())
    }
}