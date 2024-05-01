package com.example.todo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.Item
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.ui.factory.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    private val shareViewModel: TodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener { taskSubmitted() }
    }

    private fun taskSubmitted() {

        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()

        if (title.isEmpty()) {
            setUpError(true)
        } else{
            setUpError(false)

            val item = Item(title = title, description = description)
            shareViewModel.insert(item)

            Toast.makeText(requireContext(),"Task inserted.",Toast.LENGTH_SHORT).show()
            goToHomeScreen()
        }
    }

    private fun setUpError(error: Boolean) {
        if (error) {
            binding.tilTodoTitle.isErrorEnabled = true
            binding.tilTodoTitle.error = "Please input title"
        } else {
            binding.tilTodoTitle.isErrorEnabled = false
            binding.tilTodoTitle.error = null
        }
    }

    private fun goToHomeScreen() {
        findNavController().navigate(R.id.action_addFragment_to_homeFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}