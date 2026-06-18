package com.example.p2notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.p2notesapp.ui.screens.NoteScreen
import com.example.p2notesapp.ui.screens.UpdateNoteScreen
import com.example.p2notesapp.viewmodel.NoteViewModel


@Composable
fun NavGraph(viewModel: NoteViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.NoteScreen
    ) {

        composable<NavRoutes.NoteScreen> {
            NoteScreen(navController, viewModel = viewModel)
        }


        composable<NavRoutes.UpdateNoteScreen> {backStackEntry->
            val data = backStackEntry.toRoute<NavRoutes.UpdateNoteScreen>()

            UpdateNoteScreen(navController = navController,viewModel =viewModel,
                noteId = data.noteId)

        }

    }
}