package ru.geekbrains.poplib.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.poplib.databinding.FragmentUsersBinding
import ru.geekbrains.poplib.mvp.model.api.ApiHolder
import ru.geekbrains.poplib.mvp.model.repo.users.RetrofitGithubUsersRepo
import ru.geekbrains.poplib.mvp.presenter.UsersPresenter
import ru.geekbrains.poplib.mvp.view.UsersView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener
import ru.geekbrains.poplib.ui.adapter.UsersRvAdapter
import ru.geekbrains.poplib.ui.image.GlideImageLoader

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    private var binding: FragmentUsersBinding? = null

    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter by moxyPresenter {
        UsersPresenter(App.instance.router,
            RetrofitGithubUsersRepo(
                ApiHolder.api
            ), AndroidSchedulers.mainThread())
    }

    val adapter by lazy {
        UsersRvAdapter(presenter.usersListPresenter, GlideImageLoader())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun init() {
        binding?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvUsers?.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MvpAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun updateUsersList() = adapter.notifyDataSetChanged()

    override fun backPressed() = presenter.backClick()

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}