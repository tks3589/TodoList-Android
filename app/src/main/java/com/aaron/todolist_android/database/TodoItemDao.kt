package com.aaron.todolist_android.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoItemDao {
    @Insert
    suspend fun insert(item: TodoItem)

    @Query("select * from TodoItem order by createAt desc")
    fun findAll(): LiveData<List<TodoItem>>
}