package com.aaron.todolist_android.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoItemDao {
    @Insert
    suspend fun insert(item: TodoItem)

    @Update
    suspend fun update(item: TodoItem)

    @Delete
    suspend fun delete(item: TodoItem)

    @Query("select * from TodoItem where recycled = 0 order by createAt desc")
    fun findAll(): LiveData<List<TodoItem>>

    @Query("select * from TodoItem where recycled = 1 order by createAt desc")
    fun findAllRecycled(): LiveData<List<TodoItem>>
}