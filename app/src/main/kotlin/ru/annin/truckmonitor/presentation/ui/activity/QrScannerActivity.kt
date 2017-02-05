package ru.annin.truckmonitor.presentation.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Экран QR сканера.
 *
 * @author Pavel Annin.
 */
class QrScannerActivity : MvpAppCompatActivity(), ZXingScannerView.ResultHandler {

    companion object {
        @JvmField val EXTRA_QR_DATA = "ru.annin.truckmonitor.extra.qr"
    }

    // Component's
    private val viewDelegate: ZXingScannerView by lazy {
        ZXingScannerView(this).apply {
            flash = false
            setAutoFocus(true)
            setFormats(listOf(BarcodeFormat.QR_CODE))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDelegate)
    }

    override fun onStart() {
        super.onStart()
        viewDelegate.run {
            setResultHandler(this@QrScannerActivity)
            startCamera()
        }
    }

    override fun onStop() {
        super.onStop()
        viewDelegate.run { stopCamera() }
    }

    override fun handleResult(result: Result) {
        viewDelegate.resumeCameraPreview(this)
        val intent = Intent().apply {
            putExtra(EXTRA_QR_DATA, result.text)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}