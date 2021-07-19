package com.aaron.todolist_android

import android.app.Application
import androidx.lifecycle.*
import com.aaron.todolist_android.database.TodoItem
import kotlinx.coroutines.launch
import java.util.*

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private var repository: TodoItemRepository = TodoItemRepository(application)
    //private val title = Todo.Title("備忘錄")

    private val todoLiveData = MediatorLiveData<List<Todo>>().apply{
        val source = repository.getTodoItems().map {
            it.map { todoItem ->
                Todo.Item(todoItem.id,todoItem.title,todoItem.done,todoItem.createAt)
            }
        }
        addSource(source){
            value =  it
        }
        //value = listOf(title)
    }

    fun addItem(content: String){
        val todoItem = TodoItem(content,false, Date())
        viewModelScope.launch {
            repository.insertTodoItem(todoItem)
        }
    }

    fun updateItem(todo: Todo.Item){
        val todoItem = TodoItem(todo.memo,todo.checked,todo.createdAt).apply {
           id = todo.id
        }
        viewModelScope.launch {
            repository.updateTodoItem(todoItem)
        }
    }

    fun deleteItem(todo: Todo.Item){
        val todoItem = TodoItem(todo.memo,todo.checked,todo.createdAt).apply {
            id = todo.id
        }
        viewModelScope.launch {
            repository.deleteTodoItem(todoItem)
        }
    }

    fun getLiveData(): MutableLiveData<List<Todo>>{
        return todoLiveData
    }

}