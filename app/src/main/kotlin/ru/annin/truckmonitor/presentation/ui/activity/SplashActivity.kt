package ru.annin.truckmonitor.presentation.ui.activity

import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.annin.truckmonitor.data.repository.SettingsRepository
import ru.annin.truckmonitor.presentation.Navigator
import ru.annin.truckmonitor.presentation.presenter.SplashPresenter
import ru.annin.truckmonitor.presentation.ui.view.SplashView

/**
 * Экран заставки.
 *
 * @author Pavel Annin.
 */
class SplashActivity : MvpAppCompatActivity(), SplashView {

    // Component's
    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var presenter: SplashPresenter

    @ProvidePresenter(type = PresenterType.LOCAL)
    fun providerPresenter(): SplashPresenter = SplashPresenter(SettingsRepository)

    override fun navigate2Main() {
        Navigator.navigate2Main(this)
        finish()
    }

    override fun navigate2Auth() {
        Navigator.navigate2Auth(this)
        finish()
    }
}