package ru.geekbrains.poplib.di.modules

import dagger.Module
import dagger.Provides
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.cache.IGithubReposCache
import ru.geekbrains.poplib.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.poplib.mvp.model.network.INetworkStatus
import ru.geekbrains.poplib.mvp.model.repo.repos.IGithubReposRepo
import ru.geekbrains.poplib.mvp.model.repo.repos.RetrofitGithubReposRepo
import ru.geekbrains.poplib.mvp.model.repo.users.IGithubUsersRepo
import ru.geekbrains.poplib.mvp.model.repo.users.RetrofitGithubUsersRepo
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(api: IDataSource, cache: IGithubUsersCache, networkStatus: INetworkStatus) : IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, cache)


    @Singleton
    @Provides
    fun reposRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IGithubReposCache) : IGithubReposRepo = RetrofitGithubReposRepo(api, networkStatus, cache)

}