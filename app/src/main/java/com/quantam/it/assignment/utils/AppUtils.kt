package com.quantam.it.assignment.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun log(message: String){
    Log.d("quantam_it_assignemnt", message)
}

fun logError(message: String){
    Log.e("quantam_it_assignemnt", message)
}