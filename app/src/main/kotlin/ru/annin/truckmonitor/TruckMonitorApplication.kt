package ru.annin.truckmonitor

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import ru.annin.truckmonitor.data.repository.SettingsRepository

/**
 * Класс приложения.
 *
 * @author Pavel Annin.
 */
class TruckMonitorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // Setup
        SettingsRepository.init(this)
    }
}