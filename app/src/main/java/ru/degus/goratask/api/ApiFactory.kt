package ru.degus.goratask.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(private val iTypicodeUrl: String, private val client: OkHttpClient) {

    private inline fun <reified T> getApi(baseUrl: String): T { //создание Retrofit cо стандартными параметрами
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }

    fun getITypicodeApi(): ITypicodeApi = getApi(iTypicodeUrl) // метод возвращающий Api с url "https://jsonplaceholder.typicode.com/"

}