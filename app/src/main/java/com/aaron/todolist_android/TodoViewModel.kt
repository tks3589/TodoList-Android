package com.aaron.todolist_android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    private val todoLiveData = MutableLiveData<List<Todo>>(
            listOf(Todo.Title("備忘錄"))
    )
    fun addItem(content: String){
        val newItem = Todo.Item(content,false)
        todoLiveData.value = todoLiveData.value!! + listOf(newItem)
    }

    fun getLiveData(): MutableLiveData<List<Todo>>{
        return todoLiveData
    }

}