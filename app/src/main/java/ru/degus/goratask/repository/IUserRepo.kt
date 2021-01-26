package ru.degus.goratask.repository

import io.reactivex.Observable
import ru.degus.goratask.models.PhotosItem
import ru.degus.goratask.models.UsersItem

interface IUserRepo {
    fun downloadUsers(): Observable<List<UsersItem>>  //загрузка списка пользоателей
    fun downloadPhotos(albumId: String): Observable<List<PhotosItem>> //загрузка фотографий выбранного пользователя
}