package ru.degus.goratask.di.modules

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.degus.goratask.api.ApiFactory
import ru.degus.goratask.components.I_TYPICODE_URL
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiFactoryModule {

    @Singleton              //метод предоставляющий Api с базовым url
    @Provides
    fun apiFactory(@Named("iTypicodeUrl") iTypicodeUrl: String, @Named("okhhtp_logging")client: OkHttpClient): ApiFactory {
        return ApiFactory(iTypicodeUrl, client)
    }

    @Provides
    @Named("iTypicodeUrl")      //метод предоставляющий базовый url
    fun iTypicodeUrl() = I_TYPICODE_URL

    @Provides
    @Named("okhhtp_logging")
    fun getOkHttpClient(): OkHttpClient {
        val c = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("OkHttpLogger", it)
        })

        logging.level = HttpLoggingInterceptor.Level.BASIC
        c.addInterceptor(logging)
        return c.build()
    }
}