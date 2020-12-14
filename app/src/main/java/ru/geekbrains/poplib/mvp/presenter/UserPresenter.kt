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
import javax.inject.Inject

class UserPresenter(private val user: GithubUser) : MvpPresenter<UserView>() {

    @Inject lateinit var reposRepo: IGithubReposRepo
    @Inject lateinit var scheduler: Scheduler
    @Inject lateinit var router: Router

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
        user.login?.let { viewState.setLogin(it) }
        reposRepo.getRepositories(user)
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
            repo.name?.let { view.setRepositoryName(it) }
        }

        override fun getCount() = repos.size
    }
}