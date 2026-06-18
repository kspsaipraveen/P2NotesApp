package com.example.p2notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2notesapp.data.local.model.NoteEntity
import com.example.p2notesapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    val notes = repository.getAllNotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(),emptyList())

    fun getNoteById(noteId: Int): Flow<NoteEntity?> {
        return repository.getNoteById(noteId)
    }

    fun newNote(title:String,description:String) {
        viewModelScope.launch {
            repository.addNote(NoteEntity(0,title,description))
        }
    }

    fun updateNote(noteEntity: NoteEntity){
        viewModelScope.launch {
            repository.updateNote(noteEntity)
        }
    }

    fun deleteNote(noteEntity: NoteEntity){
    viewModelScope.launch {  repository.deleteNote(noteEntity) }
    }


}
