package com.example.memorygame.data.repository

import com.example.memorygame.data.local.ScoreDao
import com.example.memorygame.data.local.UserPreferences
import com.example.memorygame.domain.model.UserScore
import com.example.memorygame.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(
    private val scoreDao: ScoreDao,
    private val userPreferences: UserPreferences
) : ScoreRepository {
    override fun getAllScores(): Flow<List<UserScore>> = scoreDao.getAllScores()
    override suspend fun insertScore(score: UserScore) = scoreDao.insertScore(score)
    override suspend fun deleteAllScores() = scoreDao.deleteAllScores()

    override fun getNickname(): Flow<String> = userPreferences.nickname
    override suspend fun saveNickname(name: String) = userPreferences.saveNickname(name)
}