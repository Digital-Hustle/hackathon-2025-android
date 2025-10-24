package com.example.core.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core.domain.UserStorage
import com.example.core.presentation.di.userDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStorageImpl(private val context:Context):UserStorage {


//    init {
//        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
//            clear()
//        }
//    }

    companion object {
        private val ID_KEY = intPreferencesKey("id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val NAME_KEY = stringPreferencesKey("name")
    }



    override fun getUsername(): Flow<String?> =
        context.userDataStore.data.map { preferences -> preferences[USERNAME_KEY] }

    override suspend fun setName(name: String) {
        context.userDataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    override fun getName(): Flow<String?> =
        context.userDataStore.data.map { preferences -> preferences[NAME_KEY] }

    override suspend fun setUsername(username: String) {
        context.userDataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    override fun getId(): Flow<Int?> =
        context.userDataStore.data.map { preferences ->
            val id =  preferences[ID_KEY]
            Log.d("mLogSTORAGE",id.toString())
            id
        }

    override suspend fun setId(id: Int) {
        context.userDataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }


    override suspend fun clear() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}