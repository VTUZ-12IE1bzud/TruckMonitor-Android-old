package ru.annin.truckmonitor.data.network

import ru.annin.truckmonitor.domain.model.ErrorResponse

/**
 * Исключение вызванное во время выполнения API запроса.
 *
 * @param isNetworkException Флаг, ошибка вызвана проблемой с сетью.
 * @param code HTTP код ошибки.
 * @param error Данные о ошибке.
 *
 * @author Pavel Annin.
 */
class ApiException(val isNetworkException: Boolean = false,
                   val code: Int = 0,
                   val error: ErrorResponse? = null,
                   message: String? = null) : RuntimeException(message)
