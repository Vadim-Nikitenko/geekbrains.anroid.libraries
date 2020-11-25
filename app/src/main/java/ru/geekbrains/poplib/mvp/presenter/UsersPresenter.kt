package ru.geekbrains.poplib.mvp.presenter

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.repo.GithubUsersRepo
import ru.geekbrains.poplib.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.poplib.mvp.view.UsersView
import ru.geekbrains.poplib.mvp.view.list.UserItemView
import ru.geekbrains.poplib.navigation.Screens
import ru.terrakok.cicerone.Router

class UsersPresenter(val router: Router, val usersRepo: GithubUsersRepo, val scheduler: Scheduler) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUsersListPresenter {
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        val users = mutableListOf<GithubUser>()

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()
    var usersDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.UserScreen(usersListPresenter.users[view.pos]))
        }
    }

    fun loadData() {
        usersListPresenter.users.clear()

        usersRepo.getUsers()?.map {
            usersListPresenter.users.add(it)
        }
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(scheduler)
            ?.doOnSubscribe {
                usersDisposable = it
            }?.subscribe()

        viewState.updateUsersList()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun dispose() {
        usersDisposable?.dispose()
    }
}