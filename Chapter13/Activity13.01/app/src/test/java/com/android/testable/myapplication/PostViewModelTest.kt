package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import com.android.testable.myapplication.api.Post
import com.android.testable.myapplication.repository.PostRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PostViewModelTest {

    @InjectMocks
    lateinit var postViewModel: PostViewModel

    @Mock
    lateinit var postRepository: PostRepository

    @Test
    fun getPosts() {
        val expected = mock<LiveData<List<Post>>>()
        whenever(postRepository.getPosts()).thenReturn(expected)

        val result = postViewModel.getPosts()

        assertEquals(expected, result)
    }
}