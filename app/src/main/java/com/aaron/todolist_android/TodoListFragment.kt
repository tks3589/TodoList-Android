package com.aaron.todolist_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_todo_list.*

class TodoListFragment : Fragment() {
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
        val adapter = TodoAdapter().apply {
            onTodoChangeListener = object :OnTodoChangeListener{
                override fun onCheckBoxChange(todo: Todo.Item) {
                    todoViewModel.updateItem(todo)
                }

                override fun onTodoItemDelete(todo: Todo.Item) {
                    todoViewModel.deleteItem(todo)
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        todoViewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        addButton.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionMainFragmentToAddTodoFragment(""))
        }
    }
}