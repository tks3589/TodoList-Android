package com.aaron.todolist_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


sealed class Todo(val viewType: Int) {
    data class Title(
        val content: String
    ): Todo(TYPE_TITLE)

    @Parcelize
    data class Item(
        val id: Int,
        val memo: String,
        val checked: Boolean,
        val recycled: Boolean,
        val createdAt: Date
    ): Todo(TYPE_ITEM),Parcelable

    companion object{
        const val TYPE_TITLE = 0
        const val TYPE_ITEM = 1
    }
}