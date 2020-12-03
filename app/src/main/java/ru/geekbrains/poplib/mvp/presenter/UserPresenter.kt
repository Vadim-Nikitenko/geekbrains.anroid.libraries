package ru.geekbrains.poplib.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.repo.repos.IGithubReposRepo
import ru.geekbrains.poplib.mvp.presenter.list.IReposListPresenter
import ru.geekbrains.poplib.mvp.view.UserView
import ru.geekbrains.poplib.mvp.view.list.RepoItemView
import ru.geekbrains.poplib.navigation.Screens
import ru.terrakok.cicerone.Router

class UserPresenter(
    private val router: Router,
    private val user: GithubUser,
    private val reposRepo: IGithubReposRepo,
    private val scheduler: Scheduler
) : MvpPresenter<UserView>() {

    val reposListPresenter = ReposListPresenter()
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        reposListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.RepoScreen(reposListPresenter.repos[view.pos]))
        }
    }

    private fun loadData() {
        viewState.showProgressBar()
        viewState.setLogin(user.login)
        reposRepo.getUserRepositories(user.reposUrl)
            .observeOn(scheduler)
            .subscribe({
                reposListPresenter.repos.clear()
                reposListPresenter.repos.addAll(it)
                viewState.updateReposList()
                viewState.hideProgressBar()
            }, {
                it.printStackTrace()
            }).addTo(compositeDisposable)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun swipeRefreshed() = loadData()


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    inner class ReposListPresenter : IReposListPresenter {
        override var itemClickListener: ((RepoItemView) -> Unit)? = null

        val repos = mutableListOf<GithubRepository>()

        override fun bindView(view: RepoItemView) {
            val repo = repos[view.pos]
            view.setRepositoryName(repo.name)
        }

        override fun getCount() = repos.size
    }
}