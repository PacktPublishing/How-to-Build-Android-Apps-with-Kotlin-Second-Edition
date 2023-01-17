package com.android.testable.myapplication

import androidx.lifecycle.LiveData
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class NoteRepositoryImplTest {

    @InjectMocks
    lateinit var noteRepository: NoteRepositoryImpl

    @Mock
    lateinit var executor: Executor

    @Mock
    lateinit var noteDao: NoteDao

    @Test
    fun insertNote() {
        val note = Note(10, "text")
        doAnswer {
            (it.arguments[0] as Runnable).run()
        }.whenever(executor).execute(any())

        noteRepository.insertNote(note)

        verify(noteDao).insertNote(note)

    }

    @Test
    fun getAllNotes() {
        val notes = mock<LiveData<List<Note>>>()
        whenever(noteDao.loadNotes()).thenReturn(notes)

        val result = noteRepository.getAllNotes()

        assertEquals(notes, result)
    }

    @Test
    fun getNoteCount() {
        val count = mock<LiveData<Int>>()
        whenever(noteDao.loadNoteCount()).thenReturn(count)

        val result = noteRepository.getNoteCount()

        assertEquals(count, result)
    }
}