package ru.annin.truckmonitor.data.repository

import ru.annin.truckmonitor.data.network.TruckMonitorApi
import ru.annin.truckmonitor.data.network.TruckMonitorApiService
import ru.annin.truckmonitor.data.repository.RestApiRepository.token
import ru.annin.truckmonitor.domain.model.AccountResponse
import ru.annin.truckmonitor.domain.model.SignInResponse
import rx.Observable

/**
 * Репозиторий инкапсулирующий в себя работу с сервером.
 *
 * @property token Пользовательский токен, по умолчанию будет подставляться во все запросы.
 *
 * @author Pavel Annin.
 */

object RestApiRepository {

    // Component's
    private val service: TruckMonitorApi = TruckMonitorApiService.service

    // Data's
    var token: String? = null

    /**
     * Авторизация.
     *
     * @param userName Имя пользователя.
     * @param password Пароль пользователя.
     */
    fun signIn(userName: String,
               password: String): Observable<SignInResponse> = service.signIn(userName, password)

    /**
     * Информация о пользователе.
     * @param token Токен поьзователя.
     **/
    fun getMe(token: String? = this.token): Observable<AccountResponse> = service.getMe(token)
}