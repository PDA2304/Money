package com.example.money

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class LaunchScreen  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        Thread.sleep(1000)
        startActivity(intent)
        finish()
    }
}