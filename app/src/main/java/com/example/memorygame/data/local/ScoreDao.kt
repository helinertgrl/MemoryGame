package com.example.memorygame.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memorygame.domain.model.UserScore
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertScore(score: UserScore)

    @Query("SELECT * FROM scores ORDER BY score DESC, moves ASC")
    fun getAllScores(): Flow<List<UserScore>>

    @Query("DELETE FROM scores")
    suspend fun deleteAllScores()
}