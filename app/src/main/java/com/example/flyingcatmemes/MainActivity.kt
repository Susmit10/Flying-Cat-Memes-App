package com.example.flyingcatmemes

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var CurrentImageURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val imageView: ImageView = findViewById(R.id.memeImageView)
        loadMeme()
    }

    private fun loadMeme() {

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val imageView: ImageView = findViewById(R.id.memeImageView)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                Log.d("success Request", response.substring(0, 500))
                CurrentImageURL = response.getString("url")

                Glide.with(this).load(CurrentImageURL).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(imageView)
            },
            {
//                Log.d("error", it.localizedMessage)
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            })

            // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun nextMeme(view: View) {
//        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show()
        loadMeme()
    }
    fun shareMeme(view: View) {
//        Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()

//        val imageView: ImageView = findViewById(R.id.memeImageView)
//
//        val Curr_image: ImageView = imageView
//        val share = Intent(Intent.ACTION_SEND)
//        share.type = "image/jpeg"
//        val bytes = ByteArrayOutputStream()
//        icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val f =
//            File(Environment.getExternalStorageDirectory() + File.separator.toString() + "temporary_file.jpg")
//        try {
//            f.createNewFile()
//            val fo = FileOutputStream(f)
//            fo.write(bytes.toByteArray())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"))
//        startActivity(Intent.createChooser(share, "Share Image"))


        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this meme I found on Reddit $CurrentImageURL")
        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(intent)
    }
}