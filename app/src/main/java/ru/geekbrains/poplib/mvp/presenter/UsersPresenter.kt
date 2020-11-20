package ru.geekbrains.poplib.mvp.presenter

import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.repo.GithubUsersRepo
import ru.geekbrains.poplib.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.poplib.mvp.view.UsersView
import ru.geekbrains.poplib.mvp.view.list.UserItemView
import ru.geekbrains.poplib.navigation.Screens
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class UsersPresenter(val router: Router, val usersRepo: GithubUsersRepo) : MvpPresenter<UsersView>() {

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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.UserScreen(usersListPresenter.users[view.pos]))
        }
    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.clear()
        usersListPresenter.users.addAll(users)
        viewState.updateUsersList()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}