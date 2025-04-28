package com.jabozaroid.abopay.core.designsystem

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import java.util.Locale

object LanguageManager {
    var isRTL = false
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("aboPay", Application.MODE_PRIVATE)
        val savedLanguage = getSavedLanguage()
        setLocale(context, savedLanguage)
    }

    fun setLocale(context: Context, languageCode: String): Context {
        isRTL = languageCode == "fa"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        if (context != context.applicationContext && context.applicationContext != null) {
            context.applicationContext.resources.run {
                updateConfiguration(
                    configuration,
                    displayMetrics
                )
            }
        }
        saveLanguage(languageCode)
        return context.createConfigurationContext(configuration)
    }

    private fun saveLanguage(languageCode: String) {
        sharedPreferences.edit().putString("user_current_Language_name", languageCode).apply()
    }

    fun getSavedLanguage(): String =
        sharedPreferences.getString("user_current_Language_name", "") ?: ""

    fun getSystemLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales.get(0)
        } else {
            Resources.getSystem().configuration.locale
        }
    }

    fun wrapContext(context: Context): Context {
        val savedLanguage = getSavedLanguage()
        return setLocale(context, savedLanguage)
    }
}

