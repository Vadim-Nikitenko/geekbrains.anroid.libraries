package ru.geekbrains.poplib.di.modules

import android.widget.ImageView
import dagger.Module
import dagger.Provides
import ru.geekbrains.poplib.mvp.model.cache.IImageCache
import ru.geekbrains.poplib.mvp.model.image.IImageLoader
import ru.geekbrains.poplib.mvp.model.network.INetworkStatus
import ru.geekbrains.poplib.ui.image.GlideImageLoader
import javax.inject.Singleton

@Module
class ImageModule {

    @Singleton
    @Provides
    fun  glideImageLoader(imageCache: IImageCache, networkStatus: INetworkStatus): IImageLoader<ImageView> = GlideImageLoader(imageCache, networkStatus)
}