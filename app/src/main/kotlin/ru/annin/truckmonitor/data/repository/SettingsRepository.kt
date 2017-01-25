package ru.annin.truckmonitor.data.repository

import android.content.Context
import android.content.SharedPreferences

/**
 * Репозиторий инкапсулирующий в себя работу с настройками.
 *
 * @property userToken Пользовательский токен.
 *
 * @author Pavel Annin.
 */
object  SettingsRepository {

    private const val NAME = "Setting"
    private const val PREF_USER_TOKEN = "UserToken"

    // Component's
    private lateinit var prefs: SharedPreferences

    // Properties
    var userToken: String?
        get() = prefs.getString(PREF_USER_TOKEN, null)
        set(value) {
            prefs.edit()
                    .putString(PREF_USER_TOKEN, value)
                    .apply()
        }

    /**
     * Инициализация репозитория.
     * Выполнить в Application.
     */
    fun init(context: Context) {
        prefs = context.applicationContext.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
}