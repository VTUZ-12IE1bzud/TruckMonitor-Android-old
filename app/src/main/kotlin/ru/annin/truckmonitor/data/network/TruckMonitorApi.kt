package ru.annin.truckmonitor.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.annin.truckmonitor.domain.model.MeResponse
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
     * @param userName Имя пользователя.
     * @param password Пароль пользователя.
     */
    @GET("/api/v1/login")
    fun signIn(@Query("email") userName: String,
               @Query("password") password: String): Observable<SignInResponse>

    /** Информация о пользователе. */
    @GET("/api/v1/me")
    fun getMe(@Header(TruckMonitorApiService.HEADER_AUTH) token: String?): Observable<MeResponse>
}