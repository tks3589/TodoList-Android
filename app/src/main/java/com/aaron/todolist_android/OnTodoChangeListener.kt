package com.aaron.todolist_android

interface OnTodoChangeListener {
    fun onCheckBoxChange(todo: Todo.Item)
    fun onTodoItemDelete(todo: Todo.Item)
}