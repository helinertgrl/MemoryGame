package com.example.memorygame.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context){
    companion object{
        val USER_NICKNAME = stringPreferencesKey("user_nickname")
    }

    suspend fun saveNickname(name: String){
        context.dataStore.edit { preferences ->
            preferences[USER_NICKNAME] = name
        }
    }

    val nickname: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NICKNAME] ?: ""
    }
}
