package com.example.tvguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.tvguide.model.TVShow
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val tvShowAdapter by lazy {
        TVShowAdapter(object : TVShowAdapter.TVClickListener {
            override fun onShowClick(show: TVShow) {
                openShowDetails(show)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvShowRecyclerView: RecyclerView = findViewById(R.id.tv_show_list)
        tvShowRecyclerView.adapter = tvShowAdapter

        val tvShowRepository = (application as TVApplication).tvShowRepository
        val tvShowViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TVShowViewModel(tvShowRepository) as T
            }
        })[TVShowViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    tvShowViewModel.tvShows.collect { tvShows ->
                        tvShowAdapter.addTVShows(tvShows)
                    }
                }
                launch {
                    tvShowViewModel.error.collect { error ->
                        if (error.isNotEmpty()) Snackbar.make(tvShowRecyclerView, error, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun openShowDetails(tvShow: TVShow) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_TITLE, tvShow.name)
            putExtra(DetailsActivity.EXTRA_RELEASE, tvShow.firstAirDate)
            putExtra(DetailsActivity.EXTRA_OVERVIEW, tvShow.overview)
            putExtra(DetailsActivity.EXTRA_POSTER, tvShow.posterPath)
        }
        startActivity(intent)
    }
}