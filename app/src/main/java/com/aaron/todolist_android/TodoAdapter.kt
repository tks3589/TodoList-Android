package com.aaron.todolist_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_title.view.*
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var todos = listOf<Todo>()
    private val TYPE_TITLE = 0
    private val TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_TITLE -> TodoTitleHolder(parent)
            else -> TodoViewHolder(parent)
        }
    }

    override fun getItemCount(): Int {
        return todos.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TodoViewHolder) {
            val todo = todos[position-1]
            holder.checkBox.text = todo.memo
            holder.checkBox.isChecked = todo.checked
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> TYPE_TITLE
            else -> TYPE_ITEM
        }
    }

    fun refresh(todos: List<Todo>){
        this.todos = todos
        //notifyDataSetChanged()
        notifyItemChanged(todos.size)
    }

}

class TodoTitleHolder(group: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(group.context).inflate(R.layout.item_title,group,false)){
    val title = itemView.textTitle
}

class TodoViewHolder(group: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(group.context).inflate(R.layout.item_todo,group,false)){
    val checkBox = itemView.checkbox
}



