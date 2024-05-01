package com.example.todo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.adapter.TodoListAdapter
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.ui.factory.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val shareViewModel: TodoViewModel by activityViewModels()

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
        setupUI()
        binding.addButton.setOnClickListener { goToAddScreen() }
    }

    private fun setupUI() {
        val adapter = TodoListAdapter()
        binding.recyclerview.adapter = adapter

        /**
         * when any item is clicked we are going to update fragment
         * getting the current item
         * going to update fragment
         * with arguments
         */
        adapter.setOnItemClickListener(object : TodoListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val current = adapter.currentList[position]

                val action = HomeFragmentDirections
                    .actionHomeFragmentToUpdateFragment(current.id,current.title,current.description)
                findNavController().navigate(action)
            }
        })

        /**
         * when any checkbox is clicked we are updating the item
         * getting the current item
         * changing its state of checked
         * update it in database
         */
        adapter.setOnCheckboxClickListener(object : TodoListAdapter.OnCheckBoxClickListener {
            override fun onCheckboxClick(position: Int, isChecked: Boolean) {
                val current = adapter.currentList[position]
                if (isChecked) {
                    current.checked = 1
                } else {
                    current.checked = 0
                }
                shareViewModel.update(current)
            }
        })

        /**
         * when any item delete button clicked deleting that from database
         * getting current item
         * deleting from database
         * if undo press insert that item again
         */
        adapter.setOnDeleteClickListener(object : TodoListAdapter.OnDeleteClickListener{
            override fun onDeleteClick(position: Int) {
                val current = adapter.currentList[position]
                shareViewModel.delete(current)

                Snackbar.make(binding.recyclerview,"Task Deleted.",Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        shareViewModel.insert(current)
                    }
                }.show()
            }

        })

        shareViewModel.allItems.observe(viewLifecycleOwner) {items ->
            adapter.submitList(items)
        }
    }


    private fun goToAddScreen() {
        findNavController().navigate(R.id.action_homeFragment_to_addFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}