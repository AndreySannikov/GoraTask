package ru.degus.goratask.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.degus.goratask.api.ApiFactory
import ru.degus.goratask.models.PhotosItem
import ru.degus.goratask.models.UsersItem

class UserRepo (val apiFactory: ApiFactory) : IUserRepo {
    override fun downloadUsers(): Observable<List<UsersItem>> {                  //асинхронная загрузка списка пользователей
        return apiFactory.getITypicodeApi().getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downloadPhotos(albumId: String): Observable<List<PhotosItem>> {         //асинхронная загрузка фотографий пользователя
        return apiFactory.getITypicodeApi().getPhotos(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}