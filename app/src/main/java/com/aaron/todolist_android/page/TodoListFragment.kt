package com.aaron.todolist_android.page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaron.todolist_android.*
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment() {
    private lateinit var cIntent:Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        val adapter = TodoAdapter(0).apply {
            onTodoChangeListener = object :
                OnTodoChangeListener {
                override fun onTodoItemChange(todo: Todo.Item) {
                    todoViewModel.updateItem(todo)
                }

                override fun onTodoItemDelete(todo: Todo.Item) {
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        todoViewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        cIntent = Intent(context,TodoService::class.java)

        todoViewModel.getCheckNumLiveData().observe(viewLifecycleOwner, Observer {
            if(it!=0) {
                cIntent.putExtra("num", it)
                context?.startService(cIntent)
            }else
                context?.stopService(cIntent)
        })

        addButton.setOnClickListener {
            findNavController().navigate(
                TodoListFragmentDirections.actionMainFragmentToAddTodoFragment(
                    ""
                )
            )
        }
    }
}