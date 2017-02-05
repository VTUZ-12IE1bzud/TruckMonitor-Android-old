package ru.annin.truckmonitor.domain.interactor

import android.util.Base64
import com.fasterxml.jackson.databind.ObjectMapper
import rx.Observable

/**
 * Декодер QR кодов.
 *
 * @author Pavel Annin.
 */
class QrDecoder {

    fun <T> toModel(qr: String, type: Class<T>): Observable<T> = Observable.just(qr)
            .map { decoder(it) }
            .map {
                val mapper = ObjectMapper()
                return@map mapper.readValue(it, type)
            }

    private fun decoder(encode: String): String = String(Base64.decode(encode, Base64.DEFAULT))
}