package com.tech.androidchallenge.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.tech.androidchallenge.database.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataPreferences (
    private val context: Context
) {
    suspend fun saveUsername(noteUsername: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = noteUsername
        }
    }

    suspend fun saveName(noteUsername: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = noteUsername
        }
    }

    suspend fun saveLastname(noteUsername: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = noteUsername
        }
    }

    suspend fun saveUser(noteUser: UserEntity) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = noteUser.username
            preferences[NAME] = noteUser.name
            preferences[LASTNAME] = noteUser.lastname
        }
    }

    suspend fun deleteSaving() {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = ""
            preferences[NAME] = ""
            preferences[LASTNAME] = ""
        }
    }

    val getUsername: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME] ?:""
        }

    val getName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[NAME] ?:""
        }

    val getLastname: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LASTNAME] ?:""
        }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")
        private val USERNAME = stringPreferencesKey("key_app_username")
        private val NAME = stringPreferencesKey("key_app_name")
        private val LASTNAME = stringPreferencesKey("key_app_lastname")
    }
}