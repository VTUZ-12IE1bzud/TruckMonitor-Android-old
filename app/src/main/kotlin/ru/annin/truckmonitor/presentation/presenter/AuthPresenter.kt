package ru.annin.truckmonitor.presentation.presenter

import android.text.TextUtils
import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.annin.truckmonitor.R
import ru.annin.truckmonitor.data.network.ApiException
import ru.annin.truckmonitor.data.repository.RestApiRepository
import ru.annin.truckmonitor.data.repository.SettingsRepository
import ru.annin.truckmonitor.domain.value.Role
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
class AuthPresenter(val apiRepository: RestApiRepository,
                    val settingsRepository: SettingsRepository) : MvpPresenter<AuthView>() {

    // Component's
    private val rxSubscription: CompositeSubscription = CompositeSubscription()

    override fun onDestroy() {
        super.onDestroy()
        rxSubscription.unsubscribe()
    }

    /** Авторизация. */
    fun onSignIn(login: CharSequence?, password: CharSequence?) {
        if (validateAuthData(login, password)) {
            signIn(login.toString(), password.toString())
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

    private fun signIn(login: String, password: String) {
        viewState.toggleLoading(true)
        apiRepository.signIn(login!!.toString(), password!!.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { signIn -> getMe(signIn.token) },
                        { err ->
                            viewState.toggleLoading(false)
                            if (err is ApiException && err.error != null) {
                                viewState.error(err.error)
                            } else {
                                viewState.error(R.string.error_server_not_available)
                            }
                        })
                .addTo(rxSubscription)
    }

    private fun getMe(token: String) {
        apiRepository.getMe(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { account ->
                            viewState.toggleLoading(false)
                            account.run {
                                // Валидация пользователя
                                if (role.role == Role.DRIVER) {
                                    // Кеширование токена
                                    settingsRepository.userToken = token
                                    apiRepository.token = token

                                    viewState.navigate2Main()
                                } else {
                                    // Пользователь не водитель, сообщеине о запрете входа.
                                    viewState.error(R.string.auth_error_role_incorrect)
                                }
                            }
                        },
                        { err ->
                            viewState.toggleLoading(false)
                            if (err is ApiException && err.error != null) {
                                viewState.error(err.error)
                            } else {
                                viewState.error(R.string.error_server_not_available)
                            }
                        })
                .addTo(rxSubscription)
    }
}