package com.example.boaviagem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onRegistrar(view: View) {
        startActivity(Intent(this, ActivityRegistrar::class.java));
    }

    fun onEntrar(view: View) {
        startActivity(Intent(this, ActivityHome::class.java));
    }
}