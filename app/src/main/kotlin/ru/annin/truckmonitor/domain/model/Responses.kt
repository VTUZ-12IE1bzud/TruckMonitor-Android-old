package ru.annin.truckmonitor.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import ru.annin.truckmonitor.domain.value.Role
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Файл инкапсулирует в себя модели REST Api.
 *
 * @author Pavel Annin.
 */

/**
 * Модель данных, ошибки.
 *
 * @param message Сообщение об ошибке.
 */
data class ErrorResponse(@JsonProperty("message", required = true) val message: String) : Serializable

/**
 * Модель данных авторизации.
 *
 * @param token Токен пользователя.
 */
data class SignInResponse(@JsonProperty("token", required = true) val token: String) : Serializable

/**
 * Модель данных, информация о пользователе.
 *
 * @param id Идентификатор пользователя.
 * @param role Роль пользователя.
 * @param email Email пользователя.
 * @param surname Фамилия пользователя.
 * @param name Имя пользователя.
 * @param patronymic Отчетсов пользователя.
 * @param dateOfBirthString Дата рождения пользователя.
 * @param photo Фотография пользователя.
 * @param phone Телефон пользователя.
 */
data class AccountResponse(@JsonProperty("id", required = true) val id: Long,
                           @JsonProperty("role", required = true) val role: RoleResponse,
                           @JsonProperty("email", required = true) val email: String,
                           @JsonProperty("surname", required = true) val surname: String,
                           @JsonProperty("name", required = true) val name: String,
                           @JsonProperty("patronymic", required = true) val patronymic: String,
                           @JsonProperty("dateOfBirth", required = true) val dateOfBirthString: String,
                           @JsonProperty("photo", required = true) val photo: String,
                           @JsonProperty("phone", required = true) val phone: String) : Serializable {

    val dateOfBirth: Date
        get() {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }
            return parser.parse(dateOfBirthString)
        }
}

/**
 * Модель данных роли пользователя.
 *
 * @param id Идентификатор роли.
 * @param name Наименование роли.
 * @property role Объект роли.
 */
data class RoleResponse(@JsonProperty("id", required = true) val id: Long,
                        @JsonProperty("name", required = true) val name: String) : Serializable {

    val role: Role?
        get() = Role.findByRole(name)
}