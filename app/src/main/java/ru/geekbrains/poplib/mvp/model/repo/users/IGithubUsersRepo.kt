package ru.geekbrains.poplib.mvp.model.repo.users

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepositories(url: String): Single<List<GithubRepository>>
}