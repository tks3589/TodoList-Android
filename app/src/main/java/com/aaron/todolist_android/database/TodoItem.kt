package com.aaron.todolist_android.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TodoItem (
    var title: String,
    var done: Boolean,
    var createAt: Date
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}