package com.example.p2notesapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoutes {

    @Serializable
    object NoteScreen : NavRoutes()

    @Serializable
    data class UpdateNoteScreen(val noteId : Int) : NavRoutes()

}