package com.quantam.it.assignment.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.quantam.it.assignment.R

private lateinit var alertDialog: AlertDialog

fun startLoadingDialog(activity: Activity){
    val builder = AlertDialog.Builder(activity)
    val inflater: LayoutInflater = activity.layoutInflater
    builder.setView(inflater.inflate(R.layout.loading_alert_box,null))
    builder.setCancelable(false)

    alertDialog = builder.create()
    alertDialog.show()
}
fun loadingDialogDismiss(){
    alertDialog.dismiss()
}