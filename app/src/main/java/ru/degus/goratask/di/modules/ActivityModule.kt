package ru.degus.goratask.di.modules

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ru.degus.goratask.viewmodels.PhotoViewModel
import ru.degus.goratask.viewmodels.UserViewModel

@Module
class ActivityModule {

    @Provides       //Метод предоставляющий UserViewModel
    fun getUserViewModel(activity: FragmentActivity): UserViewModel {
        return ViewModelProvider(activity).get(UserViewModel::class.java)
    }

    @Provides      //Метод предоставляющий PhotoViewModel
    fun getPhotoViewModel(activity: FragmentActivity): PhotoViewModel {
        return ViewModelProvider(activity).get(PhotoViewModel::class.java)
    }
}