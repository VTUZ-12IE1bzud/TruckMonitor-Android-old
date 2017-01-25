package ru.annin.truckmonitor.presentation.presenter

import android.text.TextUtils
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.annin.truckmonitor.data.repository.SettingsRepository
import ru.annin.truckmonitor.presentation.ui.view.SplashView

/**
 * Presenter экрана "Эаставки".
 *
 * @author Pavel Annin.
 */
@InjectViewState
class SplashPresenter(val settingsRepository: SettingsRepository) : MvpPresenter<SplashView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        with(viewState) {
            // Если токен пустой, значит пользователь не авторизован, или вышел.
            if (TextUtils.isEmpty(settingsRepository.userToken)) navigate2Auth() else navigate2Main()
        }
    }
}