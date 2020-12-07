package ru.geekbrains.poplib.mvp.model.repo.repos

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IGithubReposRepo {
    fun getUserRepositories(url: String): Single<List<GithubRepository>>
}