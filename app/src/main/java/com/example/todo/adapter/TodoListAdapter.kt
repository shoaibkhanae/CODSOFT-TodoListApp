package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Item
import com.google.android.material.card.MaterialCardView

class TodoListAdapter: ListAdapter<Item,TodoListAdapter.TodoViewHolder>(ItemsComparator()) {

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

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnCheckBoxClickListener {
        fun onCheckboxClick(position: Int, isChecked: Boolean)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var onCheckboxClickListener: OnCheckBoxClickListener? = null
    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun setOnCheckboxClickListener(listener: OnCheckBoxClickListener) {
        this.onCheckboxClickListener = listener
    }

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        this.onDeleteClickListener = listener
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

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }

        holder.title.setOnCheckedChangeListener { _, isChecked ->
            onCheckboxClickListener?.onCheckboxClick(position, isChecked)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(position)
        }
    }
}