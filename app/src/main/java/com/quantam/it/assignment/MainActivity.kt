package com.quantam.it.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quantam.it.assignment.ui.auth.activity.AuthenticationActivity
import com.quantam.it.assignment.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }
}