package ru.geekbrains.poplib.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.repo.users.IGithubUsersRepo
import ru.geekbrains.poplib.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.poplib.mvp.view.UsersView
import ru.geekbrains.poplib.mvp.view.list.UserItemView
import ru.geekbrains.poplib.navigation.Screens
import ru.geekbrains.poplib.ui.App
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class UsersPresenter : MvpPresenter<UsersView>() {

    @Inject lateinit var usersRepo: IGithubUsersRepo
    @Inject lateinit var scheduler: Scheduler
    @Inject lateinit var router: Router

    val usersListPresenter = UsersListPresenter()
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.UserScreen(usersListPresenter.users[view.pos]))
        }
    }

    private fun loadData() {
        usersRepo.getUsers()
            .observeOn(scheduler)
            .subscribe({
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(it)
                viewState.updateUsersList()
            }, {
                it.printStackTrace()
            }).addTo(compositeDisposable)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    class UsersListPresenter : IUsersListPresenter {
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        val users = mutableListOf<GithubUser>()

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            user.login?.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadImage(it) }
        }

        override fun getCount() = users.size
    }
}