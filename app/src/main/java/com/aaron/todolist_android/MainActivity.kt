package com.aaron.todolist_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var todos = listOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = TodoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        todos = todos.toMutableList().apply {
            add(Todo.Title("備忘錄"))
            //add(Todo.Item("111",false))
        }

        adapter.submitList(todos)
        //var count = 0
        addButton.setOnClickListener {
            todos = todos.toMutableList().apply {
                add(Todo.Item("789",false))
                //add(Todo.Item(count.toString(),false))
                //count++
            }
            //todos = todos.toMutableList()
            //(todos as MutableList<Todo>).add(Todo.Item("789",false))
            adapter.submitList(todos)
        }

    }
}