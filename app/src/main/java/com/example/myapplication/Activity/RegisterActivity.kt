package com.example.myapplication.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class RegisterActivity : Activity() {
    var container: ConstraintLayout? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val input_lastname = findViewById<TextInputLayout>(R.id.input_lastname);
        val input_firstname = findViewById<TextInputLayout>(R.id.input_firstname);
        val input_email = findViewById<TextInputLayout>(R.id.input_email);
        val input_password = findViewById<TextInputLayout>(R.id.input_password);

        fun updateField(container: TextInputLayout){
            if (container.editText?.text!!.trim().length <= 0){
                container.error = getString(R.string.empty_field);
            }else{
                container.error = null;
            }
        }

        input_lastname.editText?.doOnTextChanged { _, _, _, _ ->
            input_lastname.error = null;
        }
        input_firstname.editText?.doOnTextChanged { _, _, _, _ ->
            input_firstname.error = null;
        }
        input_email.editText?.doOnTextChanged { _, _, _, _ ->
            input_email.error = null;
        }
        input_password.editText?.doOnTextChanged { _, _, _, _ ->
            input_password.error = null;
        }

        val queue = Volley.newRequestQueue(this)

        findViewById<Button>(R.id.button_register).setOnClickListener {
            updateField(input_lastname);
            updateField(input_firstname);
            updateField(input_email);
            updateField(input_password);

            if (
                input_lastname.error === null
                && input_firstname.error === null
                && input_email.error === null
                && input_password.error === null
            ){
                val req = StringRequest(
                    Request.Method.GET,
                    "https://bf1c2958-117f-4bbb-9b96-b77ded2ea179.mock.pstmn.io/login",
                    { response ->
                        val json = JSONObject(response)

                        val intent = Intent(this, TodoActivity::class.java)
                        intent.putExtra(TodoActivity.Companion.BUNDLE.FIRSTNAME,json.getString("prenom"));
                        intent.putExtra(TodoActivity.Companion.BUNDLE.LASTNAME,json.getString("nom"));
                        startActivity(intent)
                    },
                    { }
                );

                queue.add(req);
            }
        }
        findViewById<Button>(R.id.button_login).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        this.container = findViewById<ConstraintLayout>(R.id.container);
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            this.container?.let{
                val height = (it.parent as View).measuredHeight;

                it.minimumHeight = height;
                it.minHeight = height;
            }
        }
    }
}