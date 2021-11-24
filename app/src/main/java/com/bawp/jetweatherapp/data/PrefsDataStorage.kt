package com.bawp.jetweatherapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton


abstract class PrefsDataStorage(context: Context, fileName: String) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(fileName)
    val mDataStore: DataStore<Preferences> = context.dataStore

}

class UnitModeDataStore(context: Context) : PrefsDataStorage(context = context, PREF_FILE_UNIT_MODE),
    UnitModeImpl {

    // used to get the data from datastore
    override val unitMode: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            val unitMode = preferences[UNIT_MODE_KEY] ?: false
            unitMode
        }


    // used to save the ui preference to datastore
    override suspend fun saveToDataStore(isImperial: Boolean) {
        mDataStore.edit { preferences ->
            preferences[UNIT_MODE_KEY] = isImperial
        }
    }


    companion object {
        private const val PREF_FILE_UNIT_MODE = "unit_preference"
        private val UNIT_MODE_KEY = booleanPreferencesKey("unit_type")
    }
}

@Singleton
interface UnitModeImpl {
    val unitMode: Flow<Boolean>
    suspend fun saveToDataStore(isImperial: Boolean)
}