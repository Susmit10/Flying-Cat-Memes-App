package com.example.flyingcatmemes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun nextMeme(view: View) {
        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show()
    }
    fun shareMeme(view: View) {
        Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
    }
}