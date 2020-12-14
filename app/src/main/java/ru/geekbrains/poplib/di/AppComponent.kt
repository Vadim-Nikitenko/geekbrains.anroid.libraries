package ru.geekbrains.poplib.di

import dagger.Component
import ru.geekbrains.poplib.di.modules.*
import ru.geekbrains.poplib.mvp.presenter.MainPresenter
import ru.geekbrains.poplib.mvp.presenter.RepoPresenter
import ru.geekbrains.poplib.mvp.presenter.UserPresenter
import ru.geekbrains.poplib.mvp.presenter.UsersPresenter
import ru.geekbrains.poplib.ui.activity.MainActivity
import ru.geekbrains.poplib.ui.fragment.RepoFragment
import ru.geekbrains.poplib.ui.fragment.UserFragment
import ru.geekbrains.poplib.ui.fragment.UsersFragment
import ru.geekbrains.poplib.ui.image.GlideImageLoader
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        NavigationModule::class,
        RepoModule::class,
        CacheModule::class,
        ImageModule::class
    ]
)
interface AppComponent {
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(mainPresenter: MainPresenter)
    fun inject(repoPresenter: RepoPresenter)
    fun inject(glideImageLoader: GlideImageLoader)
    fun inject(usersFragment: UsersFragment)
    fun inject(mainActivity: MainActivity)
}