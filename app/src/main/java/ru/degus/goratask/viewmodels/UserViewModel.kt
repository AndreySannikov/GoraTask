package ru.degus.goratask.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.degus.goratask.models.UsersItem
import ru.degus.goratask.repository.IUserRepo
import javax.inject.Inject

class UserViewModel : AbstractViewModel() {
    @Inject
    lateinit var userRepo: IUserRepo

    val usersData: MutableLiveData<List<UsersItem>> by lazy { MutableLiveData<List<UsersItem>>() }
    val errorData: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }


    fun downloadUsers() {                                   //загрузка списка users
        addDisposable(
                userRepo.downloadUsers()
                        .subscribe({
                            Log.d("LoadUsers", it.toString())
                            usersData.value = it        //установка списка users в значение LiveData
                        }, {
                            errorData.value = it
                            Log.d("LoadUsers", it.toString())
                        })
        )
    }
}