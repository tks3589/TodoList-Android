package com.aaron.todolist_android

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.aaron.todolist_android.database.TodoItem
import kotlinx.coroutines.launch
import java.util.*

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private var repository: TodoItemRepository = TodoItemRepository(application)
    private val loadTitle = Todo.Title("讀取中...")
    private val emptyListTitle = Todo.Title("空空如也，什麼也沒有")
    private val emptyRecycleTitle = Todo.Title("沒有垃圾還能叫垃圾桶嗎")

    private val todoLiveData = MediatorLiveData<List<Todo>>().apply{
        val source = repository.getTodoItems().map {
            it.map { todoItem ->
                Todo.Item(todoItem.id,todoItem.title,todoItem.done,todoItem.recycled,todoItem.createAt)
            }
        }
        addSource(source){
            value = if(it.isNotEmpty())
                it
            else
                listOf(emptyListTitle)
        }
        value = listOf(loadTitle)
    }

    private val todoRecycledLiveData = MediatorLiveData<List<Todo>>().apply{
        val source = repository.getRecycledTodoItems().map {
            it.map { todoItem ->
                Todo.Item(todoItem.id,todoItem.title,todoItem.done,todoItem.recycled,todoItem.createAt)
            }
        }
        addSource(source){
            value = if(it.isNotEmpty())
                it
            else
                listOf(emptyRecycleTitle)
        }
        value = listOf(loadTitle)
    }

    private val todoCheckNum = MediatorLiveData<Int>().apply {
        val source = repository.getCheckNum()
        addSource(source){
            value = it
        }
    }

    fun addItem(content: String){
        val todoItem = TodoItem(content,false, recycled = false, createAt = Date())
        viewModelScope.launch {
            repository.insertTodoItem(todoItem)
        }
    }

    fun updateItem(todo: Todo.Item){
        val todoItem = TodoItem(todo.memo,todo.checked,todo.recycled,todo.createdAt).apply {
           id = todo.id
        }
        viewModelScope.launch {
            repository.updateTodoItem(todoItem)
        }
    }

    fun deleteItem(todo: Todo.Item){
        val todoItem = TodoItem(todo.memo,todo.checked,todo.recycled,todo.createdAt).apply {
            id = todo.id
        }
        viewModelScope.launch {
            repository.deleteTodoItem(todoItem)
        }
    }

    fun getLiveData(): MutableLiveData<List<Todo>>{
        return todoLiveData
    }

    fun getRecycledLiveData(): MutableLiveData<List<Todo>>{
        return todoRecycledLiveData
    }

    fun getCheckNumLiveData(): MutableLiveData<Int>{
        return todoCheckNum
    }

    suspend fun login(id: String): String{
        return repository.login(id)
    }
}