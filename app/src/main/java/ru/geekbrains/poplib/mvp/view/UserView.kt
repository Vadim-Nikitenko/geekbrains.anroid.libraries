package ru.geekbrains.poplib.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserView : MvpView {
    fun setLogin(login: String)
    fun updateReposList()
    fun init()
    fun hideProgressBar()
    fun showProgressBar()
}