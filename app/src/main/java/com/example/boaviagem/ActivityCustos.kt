package com.example.boaviagem

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class ActivityCustos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custos)
    }

    fun onNovoGasto(view: View) {
        val output = Intent()
        output.putExtra("Codigo", 66)
        setResult(RESULT_OK, output)
        finish()
    }
}