package com.i.notemakingapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.i.notemakingapp.entity.Note


/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */
const val TABLE_NAME = "note_list"

@Dao
interface NoteDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM $TABLE_NAME WHERE id=:id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE $TABLE_NAME SET title=:title, note_text = :noteContent, created_date =:editedAt, edited_flag=:isEdited WHERE id=:id")
    suspend fun updateNote(id: Int, title: String?, noteContent: String?, editedAt: Long, isEdited: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)
}