package com.example.myapplication.Activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.Data.TodoData
import com.example.myapplication.Fragment.Todo.AddTodoFragment
import com.example.myapplication.Fragment.Todo.TodoListFragment
import com.example.myapplication.R
import com.example.myapplication.TodoDatabase
import com.example.myapplication.Util.TodoFragmentInterface

class TodoActivity : FragmentActivity() {
    var lastname: String? = null;
    var firstname: String? = null;

    val database = TodoDatabase(this);
    val todos = mutableListOf<TodoData>()
    var fragment: TodoFragmentInterface? = null;

    companion object{
        object BUNDLE {
            const val LASTNAME = "lastname"
            const val FIRSTNAME = "firstname"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        intent.extras?.let {
            this.lastname = it.getString(BUNDLE.LASTNAME);
            this.firstname = it.getString(BUNDLE.FIRSTNAME);
        }

        this.viewTodoPage();
        this.update();
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus);

        this.fragment?.let {
            it.onWindowFocusChanged(hasFocus);
        }
    }

    fun addTodoPage() {
        val fragment = AddTodoFragment.instantiate(this);

        val ft = this.supportFragmentManager.beginTransaction()
        this.fragment = fragment;
        ft.replace(R.id.fragment, fragment)
        ft.commit()
    }

    fun viewTodoPage() {
        val fragment = TodoListFragment.instantiate(this);

        val ft = this.supportFragmentManager.beginTransaction()
        this.fragment = fragment;
        ft.replace(R.id.fragment, fragment)
        ft.commit()
    }

    fun update(){
        val db = this.database.readableDatabase;

        val cursor = db.query(
            TodoDatabase.TABLE_NAME,
            arrayOf(TodoDatabase.Companion.COLUMN.ID, TodoDatabase.Companion.COLUMN.CONTENT),
            null,
            null,
            null,
            null,
            "${TodoDatabase.Companion.COLUMN.ID} DESC"
        )

        todos.clear();
        with(cursor) {
            while (moveToNext()) {
                val todo = TodoData(
                    getLong(getColumnIndexOrThrow(TodoDatabase.Companion.COLUMN.ID)),
                    getString(getColumnIndexOrThrow(TodoDatabase.Companion.COLUMN.CONTENT))
                )

                todos.add(todo);
            }
        }

        this.fragment?.let {
            it.onTodoUpdated()
        }
    }

    override fun onBackPressed() {
        if (this.fragment is AddTodoFragment){
            this.viewTodoPage();
            return;
        }

        super.onBackPressed();
    }

    override fun onDestroy() {
        this.database.close();

        super.onDestroy()
    }
}