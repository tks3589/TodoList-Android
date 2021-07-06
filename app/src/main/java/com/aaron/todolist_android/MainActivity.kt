package com.aaron.todolist_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var todos = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = TodoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        todos.add(Todo.Title("備忘錄"))
        //todos.add(Todo.Title("456"))
        todos.add(Todo.Item("111",false))
        adapter.refresh(
            todos
        )
        addButton.setOnClickListener {
            todos.add(Todo.Item("789",false))
            //todos.add(Todo.Title("xxx"))
            adapter.refresh(
                todos
            )
        }

    }
}