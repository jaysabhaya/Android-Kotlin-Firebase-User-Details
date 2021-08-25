package com.appfireusers.firebaseuserdetail.base

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showSnackBar(layout: View, message: String) {
        val snackBar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun openActivity(destination: Class<*>) {
        startActivity(Intent(this, destination))
    }
}