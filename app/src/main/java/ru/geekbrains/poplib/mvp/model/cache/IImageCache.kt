package ru.geekbrains.poplib.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IImageCache {
    fun putImage(url: String, bytes: ByteArray?): Completable
    fun getBytes(url: String): Single<ByteArray>
}