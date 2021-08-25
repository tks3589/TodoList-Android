package com.aaron.todolist_android

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShowStatusPreference(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "show_dialog_preference"
    )

    suspend fun saveToCheckStatus(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[CHECK_STATUS_KEY] = status
        }
    }

    suspend fun saveToLoginStatus(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATUS_KEY] = status
        }
    }

    val checkStatus: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val checkStatus = preferences[CHECK_STATUS_KEY] ?: false
            checkStatus
        }

    val loginStatus: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val loginStatus = preferences[LOGIN_STATUS_KEY] ?: false
            loginStatus
        }

    companion object {
        private val CHECK_STATUS_KEY = preferencesKey<Boolean>("check_status")
        private val LOGIN_STATUS_KEY = preferencesKey<Boolean>("login_status")
    }
}