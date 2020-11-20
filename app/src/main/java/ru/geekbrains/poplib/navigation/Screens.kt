package ru.geekbrains.poplib.navigation

import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.ui.fragment.UserFragment
import ru.geekbrains.poplib.ui.fragment.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen() : SupportAppScreen() {
        override fun getFragment() = UsersFragment.newInstance()
    }

    class UserScreen(val user: GithubUser) : SupportAppScreen() {
        override fun getFragment() = UserFragment.newInstance(user)
    }
}