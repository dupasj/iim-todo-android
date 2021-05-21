package com.example.myapplication.Adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activity.TodoActivity
import com.example.myapplication.R
import com.example.myapplication.TodoDatabase

class TodoAdapter(private val parent: TodoActivity) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView
        val divider: View
        val drop: Button

        init {
            drop = view.findViewById(R.id.button_drop)
            divider = view.findViewById(R.id.divider)
            content = view.findViewById(R.id.content)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.todo_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.divider.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE;

        viewHolder.drop.setOnClickListener {
            val db = this.parent.database.writableDatabase

            val selection = "${TodoDatabase.Companion.COLUMN.ID}=?"
            val selectionArgs = arrayOf(this.parent.todos[position].id.toString())
            db.delete(TodoDatabase.TABLE_NAME, selection, selectionArgs)

            this.parent.update()
        }

        viewHolder.content.text = this.parent.todos[position].content
    }

    override fun getItemCount() = this.parent.todos.size

}