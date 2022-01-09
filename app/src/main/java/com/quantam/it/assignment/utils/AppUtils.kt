package com.quantam.it.assignment.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Context.toast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun log(message: String){
    Log.d("quantam_it_assignemnt", message)
}

fun logError(message: String){
    Log.e("quantam_it_assignemnt", message)
}

fun dismissKeyboard(activity: Activity) {
    val imm = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
        activity.currentFocus!!
            .applicationWindowToken, 0
    )
}
