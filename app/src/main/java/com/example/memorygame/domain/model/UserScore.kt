package com.example.memorygame.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class UserScore (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nickname: String,
    val moves: Int,
    val score: Int,
    val date: Long = System.currentTimeMillis()
)