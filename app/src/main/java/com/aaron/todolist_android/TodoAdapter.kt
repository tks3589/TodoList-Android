package com.aaron.todolist_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_title.view.*
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoAdapter : ListAdapter<Todo, RecyclerView.ViewHolder>(
        object :DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.viewType == newItem.viewType
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

        }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Todo.TYPE_TITLE -> TodoTitleHolder(parent)
            else -> TodoItemHolder(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val todo = getItem(position)){
            is Todo.Title -> (holder as TodoTitleHolder).bind(todo)
            is Todo.Item  -> (holder as TodoItemHolder).bind(todo)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

}

class TodoTitleHolder(group: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(group.context).inflate(R.layout.item_title,group,false)){
    private val title = itemView.textTitle

    fun bind(todo:Todo.Title){
        title.text = todo.content
    }
}

class TodoItemHolder(group: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(group.context).inflate(R.layout.item_todo,group,false)){
    private val checkBox = itemView.checkbox

    fun bind(todo:Todo.Item){
        checkBox.text = todo.memo
        checkBox.isChecked = todo.checked
    }
}



