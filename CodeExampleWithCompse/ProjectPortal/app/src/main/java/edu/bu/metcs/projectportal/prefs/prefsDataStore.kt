package edu.bu.metcs.projectportal.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// create a datastore
private val Context.dataStore: DataStore<Preferences>
by preferencesDataStore(name = "accesstime-datastore")

private val accesstTimeKey = stringPreferencesKey("access_time")

// return a flow when read from data store
fun loadAccessTimeFromDataStore(context: Context): Flow<String> {
    // get the last access time from the preferences datastore.
    return context.dataStore.data.map{ preferences ->
        preferences[accesstTimeKey]?:""
    }
}
// need to be a suspend function when write to the data store.
suspend fun saveAccessTimetoDataStore(context: Context, curTime: String){
    // write the current Time to the preferences store
   context.dataStore.edit {
       preferences ->
       preferences[accesstTimeKey] = curTime
   }
}