package com.example.flyingcatmemes

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    private var currentImageURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val imageView: ImageView = findViewById(R.id.memeImageView)
        loadMeme()

        val share: Button = findViewById(R.id.shareButton)
        share.setOnLongClickListener {
            Toast.makeText(this, "Share the URL", Toast.LENGTH_LONG).show()
            shareURLMeme()
            true
        }

    }

    private fun loadMeme() {

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val imageView: ImageView = findViewById(R.id.memeImageView)

        // Instantiate the RequestQueue.
//        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                Log.d("success Request", response.substring(0, 500))
                currentImageURL = response.getString("url")

                Glide.with(this).load(currentImageURL).listener(object : RequestListener<Drawable> {
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

/*
                Can also use PICASSO API to load images from URL to Image Views

                Picasso.get().load(CurrentImageURL).into(imageView)
                progressBar.visibility = View.GONE

 */

            },
            {
//                Log.d("error", it.localizedMessage)
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun nextMeme() {
//        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show()
        loadMeme()

    }

    private fun shareURLMeme() {

//        val imageView: ImageView = findViewById(R.id.memeImageView)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey, checkout this meme I found on Reddit $currentImageURL"
        )
//        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(intent)
    }


    fun shareMeme() {

        val imageView: ImageView = findViewById(R.id.memeImageView)
        val image: Bitmap? = getBitmapFromView(imageView)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, getImageUri(this, image!!))
//        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(intent)

    }


    private fun getBitmapFromView(imageView: ImageView): Bitmap? {

        val bitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        imageView.draw(canvas)
        return bitmap

    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

}