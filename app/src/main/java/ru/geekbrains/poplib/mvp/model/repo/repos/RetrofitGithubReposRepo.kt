package ru.geekbrains.poplib.mvp.model.repo.repos

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.repo.users.IGithubUsersRepo

class RetrofitGithubReposRepo(val api: IDataSource) : IGithubReposRepo {
    override fun getUserRepositories(url: String) = api.getUserRepos(url).subscribeOn(Schedulers.io())
}