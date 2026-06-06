package com.example.p2notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.p2notesapp.data.local.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)


    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)


    @Query("SELECT * FROM notes ORDER BY id ASC ")
    fun getAllNotes() : Flow<List<NoteEntity>>


    @Query("SELECT * FROM notes WHERE id= :noteId")
    fun getNoteById(noteId : Int):Flow<NoteEntity?>



}