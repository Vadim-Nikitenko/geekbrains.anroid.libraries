package ru.geekbrains.poplib.di.modules

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.geekbrains.poplib.mvp.model.cache.IGithubReposCache
import ru.geekbrains.poplib.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.poplib.mvp.model.cache.IImageCache
import ru.geekbrains.poplib.mvp.model.entity.room.MIGRATION_1_2
import ru.geekbrains.poplib.mvp.model.entity.room.MIGRATION_2_3
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.cache.RoomGithubRepositoriesCache
import ru.geekbrains.poplib.ui.cache.RoomGithubUsersCache
import ru.geekbrains.poplib.ui.cache.RoomImageCache
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App): Database = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .addMigrations(MIGRATION_1_2)
        .addMigrations(MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun usersCache(database: Database): IGithubUsersCache = RoomGithubUsersCache(database)

    @Singleton
    @Provides
    fun reposCache(database: Database): IGithubReposCache = RoomGithubRepositoriesCache(database)

    @Singleton
    @Provides
    fun imageCache(app: App, database: Database): IImageCache = RoomImageCache(app, database)

}