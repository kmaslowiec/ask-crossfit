package com.example.triviaapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import javax.inject.Inject

class StatsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val NUM_OF_WRONG_ANSWERS = intPreferencesKey("wrong_answers")
    }

    suspend fun incrementWrongAnswersNumber() {
        dataStore.edit { stats ->
            val currentCounterValue = stats[PreferencesKeys.NUM_OF_WRONG_ANSWERS] ?: 0
            stats[PreferencesKeys.NUM_OF_WRONG_ANSWERS] = currentCounterValue + 1
        }
    }
}
