package ru.geekbrains.poplib.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.poplib.R
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.presenter.UserPresenter
import ru.geekbrains.poplib.mvp.view.UserView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    companion object {
        val USER_KEY = "user_key"
        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_KEY, user)
            }
        }
    }

    val presenter by moxyPresenter {
        val user = arguments?.get(USER_KEY) as GithubUser
        UserPresenter(App.instance.router, user)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_user, null)

    override fun backPressed() = presenter.backClick()

    override fun setLogin(login: String) {
        user_login.text = login
    }

}