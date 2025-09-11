//package com.stanleymesa.core.database.shared_datasource
//
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.room.withTransaction
//import com.stanleymesa.core.database.AppDatabase
//import com.stanleymesa.core.datastore.AppDataStoreKeys
//import kotlinx.coroutines.flow.first
//import javax.inject.Inject
//
//class SharedLocalDataSource @Inject constructor(
//    private val appDatabase: AppDatabase,
//    private val userDatabase: UserDatabase,
//    private val userDao: UserDao,
//    private val dataStore: DataStore<Preferences>
//) {
//    suspend fun <R> transaction(block: suspend () -> R): R = appDatabase.withTransaction {
//        block.invoke()
//    }
//
//    suspend fun <R> transactionUser(block: suspend () -> R): R = userDatabase.withTransaction {
//        block.invoke()
//    }
//
//    // User
//    suspend fun insertUser(userEntity: UserEntity) = userDao.insert(userEntity)
//    suspend fun insertAllUser(users: List<UserEntity>) = userDao.insertAll(users)
//    suspend fun deleteUser(id: Int) = userDao.delete(id)
//    suspend fun deleteAll() = userDao.deleteAll()
//    suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()
//    suspend fun getUser(): UserEntity? = userDao.getUser()
//
//    // Data Store
//    suspend fun getToken(): String = dataStore.data.first()[AppDataStoreKeys.KEY_TOKEN].orEmpty()
//    suspend fun deleteUserDataStore() = dataStore.edit { prefs ->
//        prefs.remove(AppDataStoreKeys.KEY_TOKEN)
//    }
//
//    // Login
//    suspend fun login(userEntity: UserEntity) {
//        transactionUser {
//            deleteAll()
//            insertUser(userEntity)
//        }
//        dataStore.edit { prefs ->
//            prefs[AppDataStoreKeys.KEY_TOKEN] = userEntity.token
//            prefs[AppDataStoreKeys.KEY_EMAIL] = userEntity.email
//            prefs[AppDataStoreKeys.KEY_IS_LOGGED_IN] = true
//        }
//    }
//
//    // Logout
//    suspend fun logout() {
//        deleteAll()
//        dataStore.edit { prefs ->
//            prefs.remove(AppDataStoreKeys.KEY_TOKEN)
//            prefs.remove(AppDataStoreKeys.KEY_IS_LOGGED_IN)
//            prefs.remove(AppDataStoreKeys.KEY_IS_FORCE_LOGOUT)
//        }
//    }
//
//}