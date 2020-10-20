package com.example.flyingcatmemes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val imageView: ImageView = findViewById(R.id.memeImageView)
        loadMeme()
    }

    private fun loadMeme() {

        val imageView: ImageView = findViewById(R.id.memeImageView)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                Log.d("success Request", response.substring(0, 500))
                val imageUrl = response.getString("url")
                Glide.with(this).load(imageUrl).into(imageView)
            },
            {
//                Log.d("error", it.localizedMessage)
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT ).show()
            })

            // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun nextMeme(view: View) {
//        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show()
        loadMeme()
    }
    fun shareMeme(view: View) {
        Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
    }
}