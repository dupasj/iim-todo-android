package com.example.myapplication.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject


class LoginActivity : Activity() {
    var container: ConstraintLayout? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        this.container = findViewById(R.id.container);
        val queue = Volley.newRequestQueue(this)

        val input_email = findViewById<TextInputLayout>(R.id.input_email);
        val input_password = findViewById<TextInputLayout>(R.id.input_password);

        fun updateField(container: TextInputLayout){
            if (container.editText?.text!!.trim().length <= 0){
                container.error = getString(R.string.empty_field);
            }else{
                container.error = null;
            }
        }

        input_email.editText?.doOnTextChanged { _, _, _, _ ->
            input_email.error = null;
        }
        input_password.editText?.doOnTextChanged { _, _, _, _ ->
            input_password.error = null;
        }


        findViewById<Button>(R.id.button_register).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button_login).setOnClickListener {
            updateField(input_email);
            updateField(input_password);

            if (
                input_email.error === null
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