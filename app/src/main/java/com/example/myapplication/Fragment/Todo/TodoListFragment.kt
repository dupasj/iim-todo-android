package com.example.myapplication.Fragment.Todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activity.TodoActivity
import com.example.myapplication.Adapater.TodoAdapter
import com.example.myapplication.R
import com.example.myapplication.Util.TodoFragmentInterface
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class TodoListFragment private constructor(override val parent: TodoActivity) : Fragment() ,
    TodoFragmentInterface {
    var container: ConstraintLayout? = null;
    var recycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.recycler = view.findViewById(R.id.recycler)
        this.container = view.findViewById(R.id.container)

        view.findViewById<TextView>(R.id.title).text = String.format(getString(R.string.hello),this.parent.firstname,this.parent.lastname);

        recycler?.let {
            it.adapter = TodoAdapter(parent)
        }

        view.findViewById<ExtendedFloatingActionButton>(R.id.add_todo).setOnClickListener{
            this.parent.addTodoPage()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {

    }

    override fun onTodoUpdated(){
        recycler?.let {
            val adapter = it.adapter
            val manager = it.layoutManager

            it.adapter = null
            it.layoutManager = null
            it.layoutManager = manager
            it.adapter = adapter
        }
    }

    companion object{
        fun instantiate(parent: TodoActivity): TodoListFragment {
            return TodoListFragment(parent)
        }
    }
}