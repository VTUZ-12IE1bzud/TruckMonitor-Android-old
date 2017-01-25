package ru.annin.truckmonitor

import android.app.Application
import android.support.v7.app.AppCompatDelegate

/**
 * Класс приложения.
 *
 * @author Pavel Annin.
 */
class TruckMonitorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}