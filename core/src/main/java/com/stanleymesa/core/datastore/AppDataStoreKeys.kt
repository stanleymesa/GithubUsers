package com.stanleymesa.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppDataStoreKeys {
    const val APP_DATA_STORE_NAME = "app_data_store"
    val KEY_TOKEN = stringPreferencesKey("token")
    val KEY_IS_FORCE_LOGOUT = booleanPreferencesKey("isForceLogout")
    val KEY_IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
    val KEY_REFRESH_TOKEN = stringPreferencesKey("refreshToken")
    val KEY_EMAIL = stringPreferencesKey("email")
    val KEY_IS_ONBOARDING_DONE = booleanPreferencesKey("isOnboardingDone")
    val KEY_LANGUAGE = stringPreferencesKey("language")
    val KEY_THEME = stringPreferencesKey("theme")
    val KEY_AUTHOR = stringPreferencesKey("author")
}