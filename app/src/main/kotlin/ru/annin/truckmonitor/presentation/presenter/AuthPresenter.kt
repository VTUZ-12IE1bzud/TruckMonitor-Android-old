package ru.annin.truckmonitor.presentation.presenter

import android.text.TextUtils
import android.util.Patterns
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.annin.truckmonitor.R
import ru.annin.truckmonitor.presentation.ui.view.AuthView

/**
 * Presenter экрана "Авторизация".
 *
 * @author Pavel Annin.
 */
@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    /** Авторизация. */
    fun onSignIn(login: CharSequence?, password: CharSequence?) {
        if (validateAuthData(login, password)) {
            viewState.navigate2Main()
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