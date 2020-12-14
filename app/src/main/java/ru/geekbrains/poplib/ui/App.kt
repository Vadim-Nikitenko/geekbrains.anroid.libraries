package ru.geekbrains.poplib.ui

import android.app.Application
import ru.geekbrains.poplib.di.AppComponent
import ru.geekbrains.poplib.di.DaggerAppComponent
import ru.geekbrains.poplib.di.modules.AppModule
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}