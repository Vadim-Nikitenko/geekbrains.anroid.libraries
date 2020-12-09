package ru.geekbrains.poplib.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomCachedImage(
    @PrimaryKey var url: String,
    var path: String?
)