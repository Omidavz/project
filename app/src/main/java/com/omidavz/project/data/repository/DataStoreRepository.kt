package com.omidavz.project.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.omidavz.project.data.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "myDataStore")

class DataStoreRepository (context: Context){


    companion object {
        val nameKey = stringPreferencesKey(name = "name")
        val lastNameKey = stringPreferencesKey(name = "lastName")
        val dateOfBirthKey = stringPreferencesKey(name = "dateOfBirth")
        val identityNumberKey = longPreferencesKey(name = "identityNumber")
        val isUserLogin = booleanPreferencesKey("isUserLogin")
    }

    private val datastore  = context.dataStore

    suspend fun saveDataToDataStore(userModel: UserModel) {
        datastore.edit {preference ->
            preference[nameKey] = userModel.name
            preference[lastNameKey] = userModel.lastName
            preference[dateOfBirthKey] = userModel.dateOfBirth
            preference[identityNumberKey] = userModel.identityNumber

        }
    }


    fun readFromDataStore() = datastore.data
        .map {preference ->

            UserModel(
                name = preference[nameKey] ?: "",
                lastName = preference[lastNameKey] ?: "",
                dateOfBirth = preference[dateOfBirthKey] ?: "",
                identityNumber = preference[identityNumberKey] ?: 0
            )
        }


    suspend fun deleteDataFromDataStore() {
        datastore.edit {preference ->
            preference.clear()
        }
    }


    suspend fun setIsUserLogin(value: Boolean) {
        datastore.edit { preferences ->
            preferences[isUserLogin] = value
        }
    }

    val getIsUserLogin: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[isUserLogin]
        }

}