package ru.geekbrains.poplib.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IGithubUsersCache {
    fun putUsers(users: List<GithubUser>?): Completable
    fun getUsers(): Single<List<GithubUser>>
}