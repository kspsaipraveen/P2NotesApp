package com.example.p2notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import com.example.p2notesapp.data.local.db.NoteDataBase
import com.example.p2notesapp.data.repository.NoteRepository
import com.example.p2notesapp.ui.navigation.NavGraph
import com.example.p2notesapp.ui.screens.NoteScreen
import com.example.p2notesapp.ui.theme.P2NotesAppTheme
import com.example.p2notesapp.viewmodel.NoteViewModel
import com.example.p2notesapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. create a data base object
        val database = NoteDataBase.getDataBase(this)

        // 2. create repo with db object
        val repository = NoteRepository(database.noteDAO())

        //3. then viewmodel factory
        val factory  = NoteViewModelFactory(repository)

        // 4. view model
        noteViewModel = ViewModelProvider(
            this,
            factory
        )[NoteViewModel::class.java]

        enableEdgeToEdge()
        setContent {

            P2NotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(viewModel = noteViewModel)
                }
            }
        }
    }
}
