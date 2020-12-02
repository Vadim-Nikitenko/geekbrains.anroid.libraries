package ru.geekbrains.poplib.mvp.presenter

import moxy.MvpPresenter
import ru.geekbrains.poplib.mvp.view.MainView
import ru.geekbrains.poplib.navigation.Screens
import ru.terrakok.cicerone.Router

class MainPresenter(val router: Router): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClick() = router.exit()

}