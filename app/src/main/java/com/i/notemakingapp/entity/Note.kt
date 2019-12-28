package com.i.notemakingapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */

@Entity(tableName = "note_list")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int=0,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "note_text")
    var content: String?,
    @ColumnInfo(name = "created_date")
    var createDate: Long,
    @ColumnInfo(name = "edited_flag")
    var isEdited: Boolean = false
)