package edu.bu.metcs.projectportal.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor (
    private val dataStore: DataStore<Preferences>,
) {
    private object PreferencesKeys {
        val SHOW_ACCESSTIME = stringPreferencesKey("access_time")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map {
        preferences ->
        val showAccessTime = preferences[PreferencesKeys.SHOW_ACCESSTIME]?:""
        UserPreferences(showAccessTime)
    }

    suspend fun updateAccessTime(accessTime: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_ACCESSTIME] = accessTime
        }
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: UserPreferencesRepository? = null
//
//        fun getInstance(dataStore: DataStore<Preferences>, context: Context): UserPreferencesRepository {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE?.let {
//                    return it
//                }
//
//                val instance = UserPreferencesRepository(dataStore, context)
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}