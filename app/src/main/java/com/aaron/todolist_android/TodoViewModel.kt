package com.aaron.todolist_android

import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    private var todos = listOf<Todo>(
            Todo.Title("備忘錄")
    )
    fun addItem(){
        val newItem = Todo.Item("789",false)
        todos = todos.toMutableList().apply {
            add(newItem)
        }
    }
    fun getData(): List<Todo> {
        return todos
    }
}