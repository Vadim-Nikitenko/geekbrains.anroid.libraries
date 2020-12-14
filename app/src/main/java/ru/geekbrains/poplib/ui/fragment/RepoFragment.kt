package ru.geekbrains.poplib.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.poplib.databinding.FragmentRepoBinding
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.presenter.RepoPresenter
import ru.geekbrains.poplib.mvp.view.RepoView
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.BackButtonListener

class RepoFragment : MvpAppCompatFragment(), RepoView, BackButtonListener {

    private var binding: FragmentRepoBinding? = null

    companion object {
        val USER_KEY = "repo_key"
        fun newInstance(repo: GithubRepository) = RepoFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_KEY, repo)
            }
        }
    }

    val presenter by moxyPresenter {
        val repo = arguments?.get(RepoFragment.USER_KEY) as GithubRepository
        RepoPresenter(repo).apply { App.instance.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoBinding.inflate(inflater, container, false);
        return binding?.root
    }

    override fun backPressed() = presenter.backClick()

    override fun setRepoName(repoName: String) {
        binding?.repoName?.text = repoName
    }

}