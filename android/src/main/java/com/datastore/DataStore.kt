package com.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "react-native-data-store")

class DataStoreHelper(private val context: Context) {

  private val dataStore: DataStore<Preferences> by lazy {
    context.dataStore
  }

  suspend fun readDataStoreValue(key: String): String? {
    return withContext(Dispatchers.IO) {
      dataStore.data.firstOrNull()?.get(stringPreferencesKey(key))?.toString()
    }
  }

  suspend fun writeDataStoreValue(key: String, value: String) {
    withContext(Dispatchers.IO) {
      dataStore.edit { settings ->
        settings[stringPreferencesKey(key)] = value
      }
    }
  }

  suspend fun clearDataStoreValue(key: String) {
    withContext(Dispatchers.IO) {
      dataStore.edit { settings ->
        settings.remove(stringPreferencesKey(key))
      }
    }
  }

  suspend fun clearDataStore() {
    withContext(Dispatchers.IO) {
      dataStore.edit { settings ->
        settings.clear()
      }
    }
  }
}
