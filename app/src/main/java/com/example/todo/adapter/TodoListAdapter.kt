package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Item
import com.example.todo.ui.factory.TodoViewModel
import com.example.todo.ui.fragments.HomeFragmentDirections
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class TodoListAdapter(private val viewModel: TodoViewModel):
    ListAdapter<Item,TodoListAdapter.TodoViewHolder>(ItemsComparator()) {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.todo_card)
        val title: CheckBox = view.findViewById(R.id.todo_title)
        private val description: TextView = view.findViewById(R.id.todo_description)
        val deleteButton: ImageView = view.findViewById(R.id.delete)

        fun bind(item: Item) {
            title.text = item.title
            description.text = item.description
            title.isChecked = item.checked == 1
        }
        companion object {
            fun create(parent: ViewGroup): TodoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item,parent,false)
                return TodoViewHolder(view)
            }
        }
    }

    class ItemsComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val current = getItem(position)
        holder.bind(current)

        holder.card.setOnClickListener {

            val action = HomeFragmentDirections
                .actionHomeFragmentToUpdateFragment(current.id,current.title,current.description)
            holder.itemView.findNavController().navigate(action)
        }

        holder.title.setOnClickListener {
            if (holder.title.isChecked) current.checked = 1 else current.checked = 0
            viewModel.update(current)
        }

        holder.deleteButton.setOnClickListener {
            viewModel.delete(current)

            Snackbar.make(it,"Task deleted.",Snackbar.LENGTH_SHORT).apply {
                setAction("Undo") {
                    viewModel.insert(current)
                }
            }.show()
        }
    }
}