package com.aqua30.otpdemo.base

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity() {

    lateinit var viewBinding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = binding()
        setContentView(viewBinding.root)
    }

    open fun showMessage(message: String?) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    abstract fun binding(): ViewBinding
}