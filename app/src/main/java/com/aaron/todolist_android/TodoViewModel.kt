package com.aaron.todolist_android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    private val todoLiveData = MutableLiveData<List<Todo>>(
            listOf(Todo.Title("備忘錄"))
    )
    fun addItem(){
        val newItem = Todo.Item("789",false)
        //todoLiveData.value = todoLiveData.value!! + listOf(newItem)
        todoLiveData.value = todoLiveData.value!!.toMutableList().apply {
            add(newItem)
        }
    }

    fun getLiveData(): MutableLiveData<List<Todo>>{
        return todoLiveData
    }

}