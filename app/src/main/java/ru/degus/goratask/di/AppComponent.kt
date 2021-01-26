package ru.degus.goratask.di

import androidx.fragment.app.FragmentActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import ru.degus.goratask.App
import ru.degus.goratask.di.modules.ActivityModule
import ru.degus.goratask.di.modules.ApiFactoryModule
import ru.degus.goratask.di.modules.UserRepoModule
import ru.degus.goratask.fragments.PhotoFragment
import ru.degus.goratask.fragments.UserFragment
import ru.degus.goratask.viewmodels.PhotoViewModel
import ru.degus.goratask.viewmodels.UserViewModel
import javax.inject.Singleton

@Component(
    modules = [
        ApiFactoryModule::class,
        UserRepoModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(appModule: App): Builder
    }

    fun viewModelSubComponentBuilder(): ViewModelSubComponent.Builder                   //методы создания sub компонентов
    fun activitySubComponentBuilder(): ActivitySubComponent.Builder
}

@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun inject(vModel: UserViewModel)                                                   //методы инъекции зависимостей во viewModel
    fun inject(vModel: PhotoViewModel)

}

@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(activity: FragmentActivity): Builder

        fun build(): ActivitySubComponent
    }

    fun inject(userFragment: UserFragment)                                              //методы инъекции зависимостей во фрагменты
    fun inject(albumFragment: PhotoFragment)
}