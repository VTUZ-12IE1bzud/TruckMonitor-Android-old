package ru.annin.truckmonitor.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import ru.annin.truckmonitor.presentation.ui.activity.AuthActivity
import ru.annin.truckmonitor.presentation.ui.activity.QrScannerActivity

/**
 * Объект инкапсулирующий в себя перемещение пользователя по приложению.
 *
 * @author Pavel Annin.
 */
object Navigator {

    /** Переход на главный экран. */
    fun navigate2Main(context: Context) {

    }

    /** Переход на экран авторизации. */
    fun navigate2Auth(context: Context) {
        val intent = Intent(context, AuthActivity::class.java)
        context.startActivity(intent)
    }

    /** Переход на экран QR сканера. */
    fun navigate2QrScan(activity: Activity, request: Int) {
        val intent = Intent(activity, QrScannerActivity::class.java)
        activity.startActivityForResult(intent, request)
    }
}