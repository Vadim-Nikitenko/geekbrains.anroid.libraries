package ru.geekbrains.poplib.mvp.model.repo.users

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.repo.users.IGithubUsersRepo

class RetrofitGithubUsersRepo(val api: IDataSource) :
    IGithubUsersRepo {

    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())

}