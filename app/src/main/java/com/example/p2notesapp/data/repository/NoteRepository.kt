package com.example.p2notesapp.data.repository

import com.example.p2notesapp.data.local.dao.NotesDAO
import com.example.p2notesapp.data.local.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NoteRepository @Inject constructor(private val dao: NotesDAO) {


    suspend fun addNote(noteEntity: NoteEntity) {
        dao.insert(noteEntity)
    }

    suspend fun updateNote(noteEntity: NoteEntity){
        dao.update(noteEntity)
    }

    suspend fun deleteNote(noteEntity: NoteEntity){
        dao.delete(noteEntity)
    }

    fun getAllNotes(): Flow<List<NoteEntity>>{
        return dao.getAllNotes()
    }

    fun getNoteById(id:Int) : Flow<NoteEntity?> {
        return dao.getNoteById(id)
    }

}