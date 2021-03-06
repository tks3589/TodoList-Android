package com.aaron.todolist_android.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = arrayOf("createAt"), unique = true)])
data class TodoItem (
    var title: String,
    var done: Boolean,
    var recycled: Boolean,
    var createAt: Date
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}