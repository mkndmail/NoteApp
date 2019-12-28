package com.i.notemakingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.i.notemakingapp.dao.NoteDao
import com.i.notemakingapp.entity.Note
import kotlinx.coroutines.CoroutineScope


/**
 * Created by Mukund, mkndmail@gmail.com on 25, December, 2019
 */
const val DATABASE_NAME = "note_database"

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        var INSTANCE: NoteDataBase? = null

        fun getDatabaseInstance(context: Context, scope: CoroutineScope): NoteDataBase {
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext, NoteDataBase::class.java,
                        DATABASE_NAME
                    )
                        .build()
                    INSTANCE = instance
                    return instance
                }
        }
    }

}