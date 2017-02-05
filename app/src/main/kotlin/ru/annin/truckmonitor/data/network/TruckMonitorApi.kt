package ru.annin.truckmonitor.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.annin.truckmonitor.domain.model.AccountResponse
import ru.annin.truckmonitor.domain.model.SignInResponse
import rx.Observable

/**
 * Rest API.
 *
 * @author Pavel Annin.
 */
interface TruckMonitorApi {

    /**
     * Авторизация.
     *
     * @param email Email пользователя.
     * @param password Пароль пользователя.
     */
    @GET("/api/v1/login")
    fun signIn(@Query("email") email: String,
               @Query("password") password: String): Observable<SignInResponse>

    /** Информация о пользователе. */
    @GET("/api/v1/me")
    fun getMe(@Header(TruckMonitorApiService.HEADER_AUTH) token: String?): Observable<AccountResponse>
}