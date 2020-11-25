package ru.geekbrains.poplib.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.poplib.databinding.FragmentUserBinding
import ru.geekbrains.poplib.databinding.FragmentUsersBinding
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.presenter.UserPresenter
import ru.geekbrains.poplib.mvp.view.UserView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    private var binding: FragmentUserBinding? = null

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding  = FragmentUserBinding.inflate(inflater, container, false);
        return binding?.root
    }

    override fun backPressed() = presenter.backClick()

    override fun setLogin(login: String) {
        binding?.userLogin?.text = login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MvpAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.backClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding  = null
    }

}