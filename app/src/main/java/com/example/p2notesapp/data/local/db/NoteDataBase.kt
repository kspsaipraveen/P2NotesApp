package com.example.p2notesapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.p2notesapp.data.local.dao.NotesDAO
import com.example.p2notesapp.data.local.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDAO() : NotesDAO

    companion object{
        @Volatile
        private var INSTANCE: NoteDataBase ?= null

        fun getDataBase(context: Context) : NoteDataBase{
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "note_db"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }
    }

}