package com.example.laptoploan.core.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.sessionStore by preferencesDataStore("api_session")

class SessionManager(private val context: Context) {
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val userIdKey = longPreferencesKey("user_id")

    val userId: Flow<Long?> = context.sessionStore.data.map { it[userIdKey] }
    val accessToken: Flow<String?> = context.sessionStore.data.map { it[accessTokenKey] }
    val refreshToken: Flow<String?> = context.sessionStore.data.map { it[refreshTokenKey] }

    suspend fun saveSession(accessToken: String, refreshToken: String?, userId: Long) {
        context.sessionStore.edit {
            it[accessTokenKey] = accessToken
            if (refreshToken != null) it[refreshTokenKey] = refreshToken
            it[userIdKey] = userId
        }
    }

    suspend fun updateAccessToken(token: String) {
        context.sessionStore.edit { it[accessTokenKey] = token }
    }

    suspend fun getAccessToken(): String? = accessToken.first()
    suspend fun getRefreshToken(): String? = refreshToken.first()

    suspend fun clear() {
        context.sessionStore.edit { it.clear() }
    }
}
