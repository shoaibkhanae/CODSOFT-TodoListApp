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
import com.example.todo.databinding.FragmentUpdateBinding
import com.example.todo.ui.factory.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding
        get() = _binding!!

    private val shareViewModel: TodoViewModel by activityViewModels()

    // for receiving the arguments
    private var id: Int = 0
    private lateinit var title: String
    private lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            id = UpdateFragmentArgs.fromBundle(it).id
            title = UpdateFragmentArgs.fromBundle(it).title
            description = UpdateFragmentArgs.fromBundle(it).description
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Got updated data from home screen
        binding.etUpdateTitle.setText(title)
        binding.etUpdateDescription.setText(description)

        // go to home screen with updates
        binding.updateButton.setOnClickListener { updateTask() }
    }

    private fun updateTask() {
        // taking updated item
        val title = binding.etUpdateTitle.text.toString()
        val description = binding.etUpdateDescription.text.toString()

        if (title.isEmpty()) {
            setUpError(true)
        } else {
            setUpError(false)

            val item = Item(id,title,description)
            shareViewModel.update(item)

            Toast.makeText(requireContext(),"Task Updated",Toast.LENGTH_SHORT).show()
            goToHomeFragment()
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

    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}