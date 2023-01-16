package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class NoteListViewModelTest {

    @InjectMocks
    lateinit var noteListViewModel: NoteListViewModel

    @Mock
    lateinit var noteRepository: NoteRepository

    @Test
    fun getNoteListLiveData() {
        val notes = mock<LiveData<List<Note>>>()
        whenever(noteRepository.getAllNotes()).thenReturn(notes)

        val result = noteListViewModel.getNoteListLiveData()

        assertEquals(notes, result)
    }
}