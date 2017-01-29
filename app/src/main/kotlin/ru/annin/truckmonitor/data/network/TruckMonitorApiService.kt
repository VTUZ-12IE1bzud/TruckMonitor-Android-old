package ru.annin.truckmonitor.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.annin.truckmonitor.BuildConfig
import ru.annin.truckmonitor.data.network.TruckMonitorApiService.service
import java.util.concurrent.TimeUnit

/**
 * Сервис реализации REST API.
 * Объект конфигурирует:
 *  - Базовый URL;
 *  - Логи;
 *  - Тайминги.
 *
 *  @property service Сервис REST API.
 *
 * @author Pavel Annin.
 */
object TruckMonitorApiService {

    // Configuration's
    private const val SERVER_URL = BuildConfig.API_BASE_URL
    private val LOG_ENABLE = BuildConfig.DEBUG
    const val HEADER_AUTH ="X-Auth-Token"

    // Timings
    private const val TIMEOUT_SEC = 60L
    private const val TIMEOUT_READ_SEC = 60L
    private const val TIMEOUT_WRITE_SEC = 5 * 60L

    // Component's
    val service: TruckMonitorApi

    init {
        service = configRetrofit().create(TruckMonitorApi::class.java)
    }

    /** Конфигурация Retrofit. */
    private fun configRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addCallAdapterFactory(RxErrorHandlingAdapterFactory())
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(configHttpClient())
                .build()
    }

    /** Конфигурация HttpClient. */
    private fun configHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ_SEC, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_WRITE_SEC, TimeUnit.SECONDS)
                .addInterceptor(configLoggingInterceptor())
                .build()

    }

    /** Конфигурация логов. */
    private fun configLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (LOG_ENABLE) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }
}