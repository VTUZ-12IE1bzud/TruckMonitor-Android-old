package ru.annin.truckmonitor.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
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
 *  - Header;
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
    private const val HEADER_APPLICATION = "X-Parse-Application-Id"
    private const val HEADER_REST_KEY = "X-Parse-REST-API-Key"
    private val LOG_ENABLE = BuildConfig.DEBUG

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
                .addInterceptor(configHeaderInterceptor())
                .addInterceptor(configLoggingInterceptor())
                .build()

    }

    /** Конфигурация Header. */
    private fun configHeaderInterceptor(): Interceptor {
        return Interceptor {
            val originalRequest: Request = it.request()
            val newRequest: Request = originalRequest.newBuilder()
                    .addHeader(HEADER_APPLICATION, BuildConfig.KEY_APPLICATION)
                    .addHeader(HEADER_REST_KEY, BuildConfig.KEY_REST)
                    .build()
            return@Interceptor it.proceed(newRequest)
        }
    }

    /** Конфигурация логов. */
    private fun configLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (LOG_ENABLE) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }
}