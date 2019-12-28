package com.i.notemakingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.i.notemakingapp.database.NoteDataBase
import com.i.notemakingapp.entity.Note
import com.i.notemakingapp.repository.NoteRepository
import kotlinx.coroutines.launch


/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository

    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteDataBase.getDatabaseInstance(application, viewModelScope).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.allNotes
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }

    fun editNote(id: Int, note: Note) {
        viewModelScope.launch {
            noteRepository.editNote(id, note)
        }

    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(id)
        }

    }
}