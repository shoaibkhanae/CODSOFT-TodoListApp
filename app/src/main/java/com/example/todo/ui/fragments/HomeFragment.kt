package com.example.todo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.ItemApplication
import com.example.todo.R
import com.example.todo.adapter.TodoListAdapter
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.ui.factory.TodoViewModel
import com.example.todo.ui.factory.TodoViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val shareViewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory((requireActivity().application as ItemApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TodoListAdapter(shareViewModel)
        binding.recyclerview.adapter = adapter

        shareViewModel.allItems.observe(viewLifecycleOwner) {items ->
            adapter.submitList(items)
        }
        binding.addButton.setOnClickListener { goToAddScreen() }
    }


    private fun goToAddScreen() {
        findNavController().navigate(R.id.action_homeFragment_to_addFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}