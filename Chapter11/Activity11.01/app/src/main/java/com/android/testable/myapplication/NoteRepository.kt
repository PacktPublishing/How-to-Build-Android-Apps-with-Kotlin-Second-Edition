package com.android.testable.myapplication

import androidx.lifecycle.LiveData

interface NoteRepository {

    fun insertNote(note: Note)

    fun getAllNotes(): LiveData<List<Note>>

    fun getNoteCount(): LiveData<Int>
}