package ru.annin.truckmonitor.presentation.presenter

import android.text.TextUtils
import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.annin.truckmonitor.R
import ru.annin.truckmonitor.data.network.ApiException
import ru.annin.truckmonitor.data.repository.RestApiRepository
import ru.annin.truckmonitor.presentation.ui.view.AuthView
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.addTo
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Presenter экрана "Авторизация".
 *
 * @author Pavel Annin.
 */
@InjectViewState
class AuthPresenter(val apiRepository: RestApiRepository) : MvpPresenter<AuthView>() {

    // Component's
    private val rxSubscription: CompositeSubscription = CompositeSubscription()

    /** Авторизация. */
    fun onSignIn(login: CharSequence?, password: CharSequence?) {
        if (validateAuthData(login, password)) {
            apiRepository.signIn(login!!.toString(), password!!.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { signIn -> viewState.navigate2Main() },
                            { error ->
                                if (error is ApiException && error.code < 500) {
                                    viewState.error(R.string.error_invalid_login_password)
                                } else {
                                    viewState.error(R.string.error_server_not_available)
                                }
                            })
                    .addTo(rxSubscription)
        }
    }

    /** Открыть QR сканер. */
    fun onQrScanOpen() = viewState.navigate2QrScan()

    /** Валидация пользовательских данных. */
    private fun validateAuthData(login: CharSequence?, password: CharSequence?): Boolean {
        var validation = true
        with(viewState) {
            // Очистка ошибок
            errorLogin(null)
            errorPassword(null)

            // Email
            if (TextUtils.isEmpty(login) || !Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
                validation = false
                viewState.errorLogin(R.string.error_field_filled_correctly)
            }
            // Password
            if (TextUtils.isEmpty(password)) {
                validation = false
                viewState.errorPassword(R.string.error_field_filled_correctly)
            }
        }
        return validation
    }
}