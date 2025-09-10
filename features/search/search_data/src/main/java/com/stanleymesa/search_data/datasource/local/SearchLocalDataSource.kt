package com.stanleymesa.search_data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(
//    private val appDatabase: AppDatabase,
//    private val userDatabase: UserDatabase,
    private val dataStore: DataStore<Preferences>
) {
    // Transaction
//    suspend fun <R> transaction(block: suspend () -> R): R = appDatabase.withTransaction {
//        block.invoke()
//    }
//
//    suspend fun <R> transactionUser(block: suspend () -> R): R = userDatabase.withTransaction {
//        block.invoke()
//    }

}