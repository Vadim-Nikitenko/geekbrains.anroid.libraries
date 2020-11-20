package ru.geekbrains.poplib.mvp.presenter

import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.view.UserView
import ru.terrakok.cicerone.Router

class UserPresenter(val router: Router, val user: GithubUser) : MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setText()
    }

    private fun setText() {
        viewState.setLogin(user.login)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}