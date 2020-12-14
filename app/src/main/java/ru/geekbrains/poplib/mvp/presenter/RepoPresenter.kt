package ru.geekbrains.poplib.mvp.presenter

import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.view.RepoView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class RepoPresenter(val repo: GithubRepository) : MvpPresenter<RepoView>() {

    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repo.name?.let { viewState.setRepoName(it) }
    }

    private val compositeDisposable = CompositeDisposable()

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}