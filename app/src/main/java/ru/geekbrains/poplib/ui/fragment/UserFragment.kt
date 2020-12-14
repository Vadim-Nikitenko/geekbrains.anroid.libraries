package ru.geekbrains.poplib.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.poplib.databinding.FragmentUserBinding
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.presenter.UserPresenter
import ru.geekbrains.poplib.mvp.view.UserView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener
import ru.geekbrains.poplib.ui.adapter.ReposRvAdapter

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
        UserPresenter(user).apply { App.instance.appComponent.inject(this) }
    }

    private val adapter by lazy {
        ReposRvAdapter(presenter.reposListPresenter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding?.root
    }

    override fun backPressed() = presenter.backClick()

    override fun setLogin(login: String) {
        binding?.userLogin?.text = login
    }

    override fun init() {
        binding?.rvRepos?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvRepos?.adapter = adapter
    }

    override fun updateReposList() = adapter.notifyDataSetChanged()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MvpAppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setOnSwipeRefreshBehaviour()
    }

    private fun setOnSwipeRefreshBehaviour() {
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.swipeRefreshed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.backClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProgressBar() {
        binding?.swipeRefresh?.isRefreshing = true
    }

    override fun hideProgressBar() {
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}