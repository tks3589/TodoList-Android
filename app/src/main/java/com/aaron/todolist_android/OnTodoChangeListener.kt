package com.aaron.todolist_android

interface OnTodoChangeListener {
    fun onTodoItemChange(todo: Todo.Item)
    fun onTodoItemDelete(todo: Todo.Item)
}