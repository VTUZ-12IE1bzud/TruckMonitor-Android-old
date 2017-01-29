package ru.annin.truckmonitor.domain.model

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
data class SignInResponse(@JsonProperty("token", required = true) val token: String): Serializable

/**
 * Модель данных, информация о пользователе.
 *
 * @param id Идентификатор пользователя.
 * @param roleId Идентификатор роли.
 * @param email Email пользователя.
 * @param password Пароль пользователя.
 * @param surname Фамилия пользователя.
 * @param name Имя пользователя.
 * @param patronymic Отчетсов пользователя.
 * @param dateOfBirth Дата рождения пользователя.
 * @param photo Фотография пользователя.
 * @param phone Телефон пользователя.
 */
data class MeResponse(@JsonProperty("Id", required = true) val id: Long,
                      @JsonProperty("RoleId", required = true) val roleId: Long,
                      @JsonProperty("Email", required = true) val email: String,
                      @JsonProperty("Password", required = true) val password: String,
                      @JsonProperty("Surname", required = true) val surname: String,
                      @JsonProperty("Name", required = true) val name: String,
                      @JsonProperty("Patronymic", required = true) val patronymic: String,
                      @JsonProperty("DateOfBirth", required = true) val dateOfBirth: String,
                      @JsonProperty("Photo", required = true) val photo: String,
                      @JsonProperty("Phone", required = true) val phone: String) : Serializable