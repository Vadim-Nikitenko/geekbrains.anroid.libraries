package ru.geekbrains.poplib.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.geekbrains.poplib.databinding.ItemUserBinding
import ru.geekbrains.poplib.mvp.model.image.IImageLoader
import ru.geekbrains.poplib.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.poplib.mvp.view.list.UserItemView
import javax.inject.Inject

class UsersRvAdapter(val presenter: IUsersListPresenter) :
    RecyclerView.Adapter<UsersRvAdapter.ViewHolder>() {

    @Inject lateinit var imageLoader: IImageLoader<ImageView>

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding).apply {
            binding.container.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root), UserItemView {
        override var pos = -1
        override fun setLogin(text: String) {
            binding.tvLogin.text = text
        }

        override fun loadImage(urL: String) {
            imageLoader.loadInto(urL, binding.tvImage)
        }
    }

}