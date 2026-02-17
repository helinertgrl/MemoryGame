package com.example.memorygame.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memorygame.data.local.ScoreDao
import com.example.memorygame.domain.model.UserScore

@Database(entities = [UserScore::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "memory_game_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}