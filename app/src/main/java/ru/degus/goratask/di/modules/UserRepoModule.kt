package ru.degus.goratask.di.modules

import dagger.Module
import dagger.Provides
import ru.degus.goratask.api.ApiFactory
import ru.degus.goratask.repository.IUserRepo
import ru.degus.goratask.repository.UserRepo
import javax.inject.Singleton

@Module(includes = [ApiFactoryModule::class])
class UserRepoModule {

    @Singleton //метод предоставляющий UserRepo
    @Provides
    fun getUserRepo(apiFactory: ApiFactory): IUserRepo {
        return UserRepo(apiFactory)
    }

}