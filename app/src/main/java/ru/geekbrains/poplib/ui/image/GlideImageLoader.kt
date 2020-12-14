package ru.geekbrains.poplib.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.cache.IImageCache
import ru.geekbrains.poplib.mvp.model.image.IImageLoader
import ru.geekbrains.poplib.mvp.model.network.INetworkStatus
import java.io.ByteArrayOutputStream
import java.io.IOException

class GlideImageLoader(
    private val imageCache: IImageCache,
    private val networkStatus: INetworkStatus
) : IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        networkStatus.isOnlineSingle().subscribe { isOnline ->
            if (isOnline) {
                Glide.with(container.context)
                    .asBitmap()
                    .load(url)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            e?.printStackTrace()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageCache.putImage(url, convertBitmapToByteArray(resource))
                                .observeOn(Schedulers.io())
                                .subscribe()
                            return false
                        }
                    })
                    .into(container)
            } else {
                imageCache.getBytes(url).observeOn(AndroidSchedulers.mainThread())
                    .subscribe { byteArray ->
                        Glide.with(container.context)
                            .asBitmap()
                            .load(byteArray)
                            .into(container)
                    }
            }
        }
    }

    fun convertBitmapToByteArray(bmp: Bitmap): ByteArray {
        val out = ByteArrayOutputStream()
        try {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return out.toByteArray()
    }

//    override fun loadInto(url: String, container: ImageView) {
//
//        GlideApp.with(container.context)
//            .asBitmap()
//            .load(url)
//            .transition(withCrossFade())
//            .listener(object : RequestListener<Bitmap> {
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
//                    e?.printStackTrace()
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Bitmap?,
//                    model: Any?,
//                    target: Target<Bitmap>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    imageCache.putImage(url, resource?.ninePatchChunk)
//                Do stuff with result
//                сохраняем, берем путь куда сохранили
//                в сущность поммещаем урл и путь, сущность храним в рум
//                если офлайн, читаем файл getBytes(): BytesArray в load.
//                    return false
//                }
//            })
//            .into(container)
//    }

}