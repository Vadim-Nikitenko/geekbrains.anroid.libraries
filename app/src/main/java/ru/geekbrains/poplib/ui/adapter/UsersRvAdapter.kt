package ru.geekbrains.poplib.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.poplib.databinding.ItemUserBinding
import ru.geekbrains.poplib.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.poplib.mvp.view.list.UserItemView

class UsersRvAdapter(val presenter: IUsersListPresenter) :
    RecyclerView.Adapter<UsersRvAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemUserBinding = ItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemUserBinding).apply {
            itemUserBinding.container.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }
    }

    class ViewHolder(private val itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root), UserItemView {
        override var pos = -1
        override fun setLogin(text: String) {
            itemUserBinding.tvLogin.text = text
        }
    }

}