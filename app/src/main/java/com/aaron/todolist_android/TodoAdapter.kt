package com.aaron.todolist_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_title.view.*
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var todos = listOf<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Todo.TYPE_TITLE -> TodoTitleHolder(parent)
            else -> TodoViewHolder(parent)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todo = todos[position]
        when(holder){
            is TodoTitleHolder -> {
                holder.title.text = (todo as Todo.Title).content
            }
            is TodoViewHolder -> {
                holder.checkBox.text = (todo as Todo.Item).memo
                holder.checkBox.isChecked = todo.checked
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return todos[position].viewType
    }

    fun refresh(todos: List<Todo>){
        this.todos = todos
        //notifyDataSetChanged()
        notifyItemInserted(todos.size-1)
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



