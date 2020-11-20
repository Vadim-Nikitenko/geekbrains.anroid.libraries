package ru.geekbrains.poplib.mvp.presenter.list

import ru.geekbrains.poplib.mvp.view.list.IItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}