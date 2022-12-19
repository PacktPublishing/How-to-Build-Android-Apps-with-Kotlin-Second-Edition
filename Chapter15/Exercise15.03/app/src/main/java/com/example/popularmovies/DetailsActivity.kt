package com.example.popularmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_RELEASE = "release"
        const val EXTRA_OVERVIEW = "overview"
        const val EXTRA_POSTER = "poster"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val titleText: TextView = findViewById(R.id.title_text)
        val releaseText: TextView = findViewById(R.id.release_text)
        val overviewText: TextView = findViewById(R.id.overview_text)
        val poster: ImageView = findViewById(R.id.movie_poster)

        val extras = intent.extras

        titleText.text = extras?.getString(EXTRA_TITLE).orEmpty()
        releaseText.text = extras?.getString(EXTRA_RELEASE).orEmpty().take(4)

        overviewText.text =
            getString(R.string.movie_overview, extras?.getString(EXTRA_OVERVIEW).orEmpty())

        val posterPath = extras?.getString(EXTRA_POSTER).orEmpty()
        Glide.with(this@DetailsActivity)
            .load("$IMAGE_URL$posterPath")
            .placeholder(R.mipmap.ic_launcher)
            .fitCenter()
            .into(poster)
    }
}