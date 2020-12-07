package ru.geekbrains.poplib.mvp.view.list

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadImage(urL: String)
}