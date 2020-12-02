package ru.geekbrains.poplib.mvp.model.repo

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

class GithubUsersRepo {

    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5"),
        GithubUser("login6")
    )

    fun getUsers(): @NonNull Observable<GithubUser>? {
        return Observable.fromIterable(repositories);
    }
}