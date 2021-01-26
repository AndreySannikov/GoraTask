package ru.degus.goratask.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.degus.goratask.models.PhotosItem
import ru.degus.goratask.models.UsersItem

interface ITypicodeApi {
    @GET("users")
    fun getUsers(): Observable<List<UsersItem>> // get запрос на сервер, возвращающий список пользователей

    @GET("photos")
    fun getPhotos(@Query("albumId")albumId: String): Observable<List<PhotosItem>> // get запрос на сервер, возращающий все фотографии выбранного пользователя
}