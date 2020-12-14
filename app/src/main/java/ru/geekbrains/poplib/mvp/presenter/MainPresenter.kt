package ru.geekbrains.poplib.mvp.presenter

import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.view.MainView
import ru.geekbrains.poplib.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter : MvpPresenter<MainView>() {
    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClick() = router.exit()

}