package ru.annin.truckmonitor.data.network

/**
 * Исключение вызванное во время выполнения API запроса.
 *
 * @param isNetworkException Флаг, ошибка вызвана проблемой с сетью.
 * @param code HTTP код ошибки.
 *
 * @author Pavel Annin.
 */
class ApiException(val isNetworkException: Boolean = false,
                   val code: Int = 0,
                   message: String? = null) : RuntimeException(message)
