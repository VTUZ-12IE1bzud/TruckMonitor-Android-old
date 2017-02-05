package ru.annin.truckmonitor.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Файл инкапсулирует в себя модели Qr кодов.
 *
 * @author Pavel Annin.
 */

/**
 * Модель данных авторизации.
 *
 * @param email Email пользователя.
 * @param password Пароль пользователя.
 */
data class SignInQr(@JsonProperty("email", required = true) val email: String,
                    @JsonProperty("password", required = true) val password: String) : Serializable