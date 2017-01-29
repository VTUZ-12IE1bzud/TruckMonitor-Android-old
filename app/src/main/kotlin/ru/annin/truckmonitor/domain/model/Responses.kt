package ru.annin.truckmonitor.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Файл инкапсулирует в себя модели REST Api.
 *
 * @author Pavel Annin.
 */

/**
 * Модель данных авторизации.
 *
 * @param token Токен пользователя.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class SignInResponse(@JsonProperty("token", required = true) val token: String): Serializable
