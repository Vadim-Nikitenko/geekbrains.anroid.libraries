package ru.geekbrains.poplib.ui.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.cache.IGithubReposCache
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository

class RoomGithubRepositoriesCache(val db: Database) : IGithubReposCache {

    override fun putRepos(repos: List<GithubRepository>, user: GithubUser): Completable = Completable.fromCallable {
            val roomUser = user.login?.let { db.userDao.findByLogin(it) }
                ?: throw java.lang.RuntimeException("No such users in database")
            val roomRepos = repos.map {
                RoomGithubRepository(
                    it.id ?: "",
                    it.name ?: "",
                    roomUser.id,
                    it.fullName ?: ""
                )
            }
            db.repositoryDao.insert(roomRepos)
            repos
        }

    override fun getRepos(user: GithubUser): Single<List<GithubRepository>> = Single.fromCallable {
            val roomUser = user.login?.let { db.userDao.findByLogin(it) }
                ?: throw java.lang.RuntimeException("No such users in database")
            db.repositoryDao.findForUser(roomUser.id)
                .map {
                    GithubRepository(
                        it.id,
                        it.name,
                        it.fullName
                    )
                }
        }
}