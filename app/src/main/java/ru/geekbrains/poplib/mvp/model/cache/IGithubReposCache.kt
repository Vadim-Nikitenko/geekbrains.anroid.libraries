package ru.geekbrains.poplib.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IGithubReposCache {
    fun putRepos(repos: List<GithubRepository>, user: GithubUser): Completable
    fun getRepos(user: GithubUser): Single<List<GithubRepository>>
}