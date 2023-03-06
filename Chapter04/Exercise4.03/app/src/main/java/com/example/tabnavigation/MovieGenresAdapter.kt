package com.example.tabnavigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

val TAB_GENRES_SCROLLABLE = listOf(
    R.string.action,
    R.string.comedy,
    R.string.drama,
    R.string.sci_fi,
    R.string.family,
    R.string.crime,
    R.string.history
)
val TAB_GENRES_FIXED = listOf(R.string.action, R.string.comedy, R.string.drama)

class MovieGenresAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
       return TAB_GENRES_SCROLLABLE.size
    }

    override fun createFragment(position: Int): Fragment {
        return MoviesFragment()
    }
}
