package com.example.memorygame.domain.repository

import com.example.memorygame.domain.model.UserScore
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    fun getAllScores(): Flow<List<UserScore>>
    suspend fun insertScore(score: UserScore)
    suspend fun deleteAllScores()

    fun getNickname(): Flow<String>
    suspend fun saveNickname(name: String)
}