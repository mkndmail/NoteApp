package com.i.notemakingapp.repository

import com.i.notemakingapp.dao.NoteDao
import com.i.notemakingapp.entity.Note


/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */
class NoteRepository(private val noteDao: NoteDao) {
    val allNotes = noteDao.getAllNotes()

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun editNote(id: Int, note: Note) {
        noteDao.updateNote(id, note.title, note.content, note.createDate, note.isEdited)
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }
}