package com.aaron.todolist_android.page

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aaron.todolist_android.page.AddTodoFragmentArgs
import com.aaron.todolist_android.R
import com.aaron.todolist_android.TodoViewModel
import kotlinx.android.synthetic.main.fragment_add_todo.*

class AddTodoFragment : Fragment() {

    private val args by navArgs<AddTodoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTodo.requestFocus()
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)

        val todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        editTodo.setText(args.memo)

        buttonAdd.setOnClickListener {
            if(editTodo.text.isNullOrEmpty()){
                editTodo.error = "請輸入您的待辦事項！"
            }else {
                todoViewModel.addItem(editTodo.text.toString())
                inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
                findNavController().popBackStack()
            }
        }

    }
}