package com.example.myapplication.Util

import com.example.myapplication.Activity.TodoActivity

interface TodoFragmentInterface {
    val parent: TodoActivity
    fun onWindowFocusChanged(hasFocus: Boolean)
    fun onTodoUpdated()
}