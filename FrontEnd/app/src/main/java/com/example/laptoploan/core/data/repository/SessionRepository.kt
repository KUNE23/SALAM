package com.example.laptoploan.core.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("session")

class SessionRepository(private val context: Context) {
    private val userIdKey = longPreferencesKey("user_id")

    val userId: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[userIdKey]
    }

    suspend fun saveUser(id: Long) {
        context.dataStore.edit { it[userIdKey] = id }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
