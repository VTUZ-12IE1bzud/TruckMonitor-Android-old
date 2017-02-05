package ru.annin.truckmonitor.presentation.ui.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.annin.truckmonitor.R
import ru.annin.truckmonitor.data.repository.RestApiRepository
import ru.annin.truckmonitor.data.repository.SettingsRepository
import ru.annin.truckmonitor.domain.interactor.QrDecoder
import ru.annin.truckmonitor.domain.model.ErrorResponse
import ru.annin.truckmonitor.presentation.Navigator
import ru.annin.truckmonitor.presentation.common.BaseViewDelegate
import ru.annin.truckmonitor.presentation.presenter.AuthPresenter
import ru.annin.truckmonitor.presentation.ui.view.AuthView

/**
 * Экран "Авторизации".
 *
 * @author Pavel Annin.
 */
class AuthActivity : MvpAppCompatActivity(), AuthView {

    companion object {
        private const val REQUEST_QR_SCAN: Int = 1
    }

    // Component's
    @InjectPresenter(type = PresenterType.LOCAL)
    lateinit var presenter: AuthPresenter
    private lateinit var viewDelegate: ViewDelegate
    private val progress: ProgressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage(getString(R.string.auth_loading))
        }
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    fun providerPresenter(): AuthPresenter = AuthPresenter(RestApiRepository, SettingsRepository, QrDecoder())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewDelegate = ViewDelegate(findViewById(R.id.root)).apply {
            onSignInClick = { login, password -> presenter.onSignIn(login, password) }
            onScanQrClick = { presenter.onQrScanOpen() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_QR_SCAN && resultCode == Activity.RESULT_OK) {
            data?.run {
                val qrData = extras.getString(QrScannerActivity.EXTRA_QR_DATA)
                presenter.onSignInWithQr(qrData)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun navigate2Main() {
        Navigator.navigate2Main(this)
        finish()
    }

    override fun navigate2QrScan() = Navigator.navigate2QrScan(this, REQUEST_QR_SCAN)

    override fun errorLogin(res: Int?) {
        viewDelegate.errorLogin = if (res != null) getString(res) else null
    }

    override fun errorPassword(res: Int?) {
        viewDelegate.errorPassword = if (res != null) getString(res) else null
    }

    override fun error(res: Int) = viewDelegate.error(getString(res))


    override fun error(err: ErrorResponse) = viewDelegate.error(err.message)

    override fun toggleLoading(isLoading: Boolean) {
        if (isLoading && !progress.isShowing) {
            progress.show()
        } else if (!isLoading && progress.isShowing) {
            progress.hide()
        }
    }

    /**
     * View Delegate экрана "Авторизации".
     *
     * @property errorLogin Ошибка в логине.
     * @property errorPassword Ошибка в пароле.
     * @property onSignInClick Событие, пользователь выбрал "Войти".
     * @property onScanQrClick Событие, пользователь выбрал "QR сканнер".
     */
    private class ViewDelegate(vRoot: View) : BaseViewDelegate(vRoot) {

        // View's
        private val edtUserLogin: EditText by findView(R.id.edt_auth_login)
        private val edtUserPassword: EditText by findView(R.id.edt_auth_password)
        private val tilUserLogin: TextInputLayout by findView(R.id.til_auth_login)
        private val tilUserPassword: TextInputLayout by findView(R.id.til_auth_password)
        private val btnSignIn: Button by findView(R.id.btn_auth_sign_in)
        private val btnScanQr: Button by findView(R.id.btn_scan_qr)

        // Properties
        var errorLogin: String? = null
            set(value) {
                tilUserLogin.error = value
            }
        var errorPassword: String? = null
            set(value) {
                tilUserPassword.error = value
            }

        // Listener's
        var onSignInClick: ((login: CharSequence, password: CharSequence) -> Unit)? = null
        var onScanQrClick: (() -> Unit)? = null

        init {
            btnSignIn.setOnClickListener { onSignInClick?.invoke(edtUserLogin.text, edtUserPassword.text) }
            btnScanQr.setOnClickListener { onScanQrClick?.invoke() }
        }

        fun error(message: CharSequence) = Snackbar.make(vRoot, message, Snackbar.LENGTH_LONG).show()
    }
}