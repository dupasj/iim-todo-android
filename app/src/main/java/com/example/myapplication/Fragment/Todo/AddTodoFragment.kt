package com.example.myapplication.Fragment.Todo

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.myapplication.Activity.TodoActivity
import com.example.myapplication.R
import com.example.myapplication.TodoDatabase
import com.example.myapplication.Util.TodoFragmentInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddTodoFragment private constructor(override val parent: TodoActivity) : Fragment(),
    TodoFragmentInterface {
    lateinit var container: ConstraintLayout;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        this.container = view.findViewById(R.id.container);

        val input = view.findViewById<TextInputLayout>(R.id.input_todo)

        fun updateField(container: TextInputLayout){
            if (container.editText?.text!!.trim().length <= 0){
                container.error = getString(R.string.empty_field);
            }else{
                container.error = null;
            }
        }

        input.editText?.doOnTextChanged { _, _, _, _ ->
            input.error = null;
        }

        view.findViewById<Button>(R.id.button_leave).setOnClickListener{
            this.parent.viewTodoPage();
        }
        view.findViewById<Button>(R.id.button_add).setOnClickListener{
            updateField(input);

            if (input.error === null){
                val db = this.parent.database.writableDatabase

                val values = ContentValues().apply {
                    put(TodoDatabase.Companion.COLUMN.CONTENT, input.editText?.text.toString())
                }

                db?.insert(TodoDatabase.TABLE_NAME, null, values)

                this.parent.update();
                this.parent.viewTodoPage();
            }
        }

        this.onWindowFocusChanged(this.parent.hasWindowFocus());
    }

    override fun onTodoUpdated() {
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            val height = (this.container.parent as View).measuredHeight;

            this.container.minimumHeight = height;
            this.container.minHeight = height;
        }
    }

    companion object{
        fun instantiate(parent: TodoActivity): AddTodoFragment {
            return AddTodoFragment(parent);
        }
    }
}