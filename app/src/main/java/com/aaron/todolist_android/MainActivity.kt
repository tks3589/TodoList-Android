package com.aaron.todolist_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = TodoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        val todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.getLiveData().observe(this, Observer {
            adapter.submitList(it)
        })

        addButton.setOnClickListener {
            todoViewModel.addItem()
        }

    }
}