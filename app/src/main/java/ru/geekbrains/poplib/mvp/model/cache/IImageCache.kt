package ru.geekbrains.poplib.mvp.model.cache

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IImageCache {
    fun putImage(url: String, bytes: ByteArray?): Completable
    fun getBytes(url: String): Single<ByteArray>
    fun convertBitmapToByteArray(bmp: Bitmap): ByteArray
}