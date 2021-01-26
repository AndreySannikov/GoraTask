package ru.degus.goratask.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.degus.goratask.models.PhotosItem
import ru.degus.goratask.repository.IUserRepo
import javax.inject.Inject

class PhotoViewModel : AbstractViewModel() {
    @Inject
    lateinit var userRepo: IUserRepo

    val photosData: MutableLiveData<List<PhotosItem>> by lazy { MutableLiveData<List<PhotosItem>>() }
    val errorData: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }

    fun loadPhotos(albumId: String) {                                                           //загрузка фотографий пользователя
        Log.d("PhotoViewModel", "" + albumId.toString())
        addDisposable(
                userRepo.downloadPhotos(albumId)
                        .subscribe({
                            Log.d("PhotoViewModel", "size " + it.size.toString())
                            photosData.value = it                                    //установка списка в значения LiveData
                        }, {
                            errorData.value = it
                            Log.d("LoadPhotos", it.toString())
                        })
        )
    }
}