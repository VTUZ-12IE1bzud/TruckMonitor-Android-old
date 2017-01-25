package ru.annin.truckmonitor.presentation.ui.view

import com.arellomobile.mvp.MvpView

/**
 * Представлеиние экрана "Заставка".
 *
 * @author Pavel Annin.
 */
interface SplashView : MvpView {

    /** Переход на экран авторизации. */
    fun navigate2Auth()

    /** Переход на главный экран. */
    fun navigate2Main()
}