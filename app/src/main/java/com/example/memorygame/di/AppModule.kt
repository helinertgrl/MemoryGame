package com.example.memorygame.di

import android.content.Context
import com.example.memorygame.data.local.AppDatabase
import com.example.memorygame.data.local.ScoreDao
import com.example.memorygame.data.local.UserPreferences
import com.example.memorygame.data.repository.ScoreRepositoryImpl
import com.example.memorygame.domain.repository.ScoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideScoreDao(database: AppDatabase): ScoreDao {
        return database.scoreDao()
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences{
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideScoreRepository(
        scoreDao: ScoreDao,
        userPreferences: UserPreferences
    ): ScoreRepository {
        return ScoreRepositoryImpl(scoreDao, userPreferences)
    }
}