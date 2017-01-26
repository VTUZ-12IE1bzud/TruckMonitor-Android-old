package ru.annin.truckmonitor.data.network

import retrofit2.http.GET
import retrofit2.http.Query
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
    @GET("/parse/truckmonitor/login")
    fun signIn(@Query("username") userName: String,
               @Query("password") password: String): Observable<SignInResponse>
}