package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.testable.myapplication.api.Post
import com.android.testable.myapplication.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {

    fun getPosts(): LiveData<List<Post>> = postRepository.getPosts()
}