package com.aaron.todolist_android

import android.app.Application
import androidx.lifecycle.LiveData
import com.aaron.todolist_android.database.AppDatabase
import com.aaron.todolist_android.database.TodoItem

class TodoItemRepository(application: Application) {
    private val database = AppDatabase.getInstance(application)

    suspend fun insertTodoItem(todoItem: TodoItem){
        database.todoItemDao().insert(todoItem)
    }

    suspend fun updateTodoItem(todoItem: TodoItem){
        database.todoItemDao().update(todoItem)
    }

    suspend fun deleteTodoItem(todoItem: TodoItem){
        database.todoItemDao().delete(todoItem)
    }

    fun getTodoItems(): LiveData<List<TodoItem>>{
        return database.todoItemDao().findAll()
    }

    fun getRecycledTodoItems(): LiveData<List<TodoItem>>{
        return database.todoItemDao().findAllRecycled()
    }

    fun getCheckNum(): LiveData<Int>{
        return database.todoItemDao().findAllCheckNum()
    }
}