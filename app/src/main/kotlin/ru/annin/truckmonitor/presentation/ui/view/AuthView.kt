package ru.annin.truckmonitor.presentation.ui.view

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView

/**
 * Представление экрана "Авторизация".
 *
 * @author Pavel Annin.
 */
interface AuthView : MvpView {

    /** Переход на главный экран. */
    fun navigate2Main()

    /** Переход на экран QR сканера. */
    fun navigate2QrScan()

    /** Ошибка в логине. */
    fun errorLogin(@StringRes res: Int?)

    /** Ошибка в пароле. */
    fun errorPassword(@StringRes res: Int?)
}