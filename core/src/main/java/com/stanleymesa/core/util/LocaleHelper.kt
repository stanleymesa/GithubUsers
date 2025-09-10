package com.stanleymesa.core.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.datastore.preferences.core.edit
import com.stanleymesa.core.datastore.AppDataStoreKeys
import com.stanleymesa.core.util.extentions.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale


object LocaleHelper {

    fun setLocale(context: Context): Context {
        var lang: AppLanguage
        runBlocking {
            lang = (context.dataStore.data.first()[AppDataStoreKeys.KEY_LANGUAGE]
                ?: "").toLocaleEnum()
        }
        return setLocale(context, lang)
    }

    // the method is used to set the language at runtime
    fun setLocale(context: Context, language: AppLanguage): Context {
        persist(context, language)

        // updating the language for devices above android nougat
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
        // for devices having lower version of android os
    }

    private fun persist(context: Context, language: AppLanguage) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit { setting ->
                setting[AppDataStoreKeys.KEY_LANGUAGE] = language.toString()
            }
        }
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: AppLanguage): Context {
        val locale =
            Locale(if (language == AppLanguage.ENGLISH) LANGUAGE_ENGLISH else LANGUAGE_INDONESIA)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }


    private fun updateResourcesLegacy(context: Context, language: AppLanguage): Context {
        val locale =
            Locale(if (language == AppLanguage.ENGLISH) LANGUAGE_ENGLISH else LANGUAGE_INDONESIA)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

    fun String.toLocaleEnum() =
        if (this == AppLanguage.ENGLISH.toString()) AppLanguage.ENGLISH else AppLanguage.INDONESIA

    fun AppLanguage.toLocaleString() =
        if (this == AppLanguage.ENGLISH) LANGUAGE_ENGLISH else LANGUAGE_INDONESIA

    enum class AppLanguage {
        ENGLISH,
        INDONESIA
    }

    private const val LANGUAGE_ENGLISH = "en"
    private const val LANGUAGE_INDONESIA = "id"
}