package com.example.p2notesapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.p2notesapp.ui.navigation.NavRoutes
import com.example.p2notesapp.viewmodel.NoteViewModel


@Composable
fun NoteScreen(navController: NavController,
               viewModel: NoteViewModel) {

    // get notes at the real time for database
    val notes by viewModel.notes.collectAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Scaffold(
        topBar = { TopBar() }
    ) { innerpadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                value = title,
                onValueChange = { newText ->
                    title = newText
                },
                label = { Text("Note Title") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                value = description,
                onValueChange = { newText ->
                    description = newText
                },
                label = { Text("Note Description") },
                maxLines = 4
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    viewModel.newNote(title, description)
                    Toast.makeText(context,"note added",Toast.LENGTH_SHORT).show()
                    title = ""
                    description = ""
                },
                enabled = title.isNotBlank() || description.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black,
                    containerColor = Color.Black,
                    contentColor = Color.White
                )

            ) {
                Text(text = "Add Note")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your Notes",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notes) { note ->

                    Surface(
                        onClick = {
                           navController.navigate(NavRoutes.UpdateNoteScreen(noteId = note.id))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.Black
                                )
                            )
                            Text(
                                text = note.description,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = Color.Black, fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {

    TopAppBar(
        title = {
            Text(
                text = "Add Note",
                style = MaterialTheme.typography.displaySmall
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)


    )

}