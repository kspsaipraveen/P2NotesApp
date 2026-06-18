package com.example.p2notesapp.ui.screens

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.p2notesapp.data.local.model.NoteEntity
import com.example.p2notesapp.viewmodel.NoteViewModel

@Composable
fun UpdateNoteScreen(navController: NavHostController, noteId: Int, viewModel: NoteViewModel) {

    val note by viewModel.getNoteById(noteId = noteId).collectAsState(null)
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(note) {

        note?.let {

            title = it.title
            description = it.description

        }

    }
    Scaffold(
        containerColor = Color.White,
        topBar = { NoteTopBar(navController) },

        ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, color = Color.Red.copy(0.5f))

            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text(
                            text = "Note Title"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true
                )



                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = {
                        Text(
                            text = "Note Description"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(16.dp),
                    maxLines = 5
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Button(
                    modifier = Modifier.clip(shape = RoundedCornerShape(16.dp)),
                    enabled = title.isNotBlank() &&
                            description.isNotBlank() &&
                            (
                                    title != note?.title ||
                                            description != note?.description
                            ),
                    onClick = {
                        viewModel.updateNote(noteEntity = NoteEntity(noteId, title, description))
                        Toast.makeText(context, "note updated", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Update"
                    )
                }

                Button(
                    modifier = Modifier.clip(shape = RoundedCornerShape(16.dp)),
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(0.6f),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Delete"
                    )
                }
            }

        }


    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteNote(noteEntity = NoteEntity(noteId, title, description))
                        showDialog = false
                        Toast.makeText(context, "note deleted", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                ) {
                    Text(
                        text = "Confirm"
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            },
            title = {
                Text(
                    text = "Are you sure to Delete ?",
                    style = MaterialTheme.typography.labelMedium
                )
            }

        )

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopBar(navController: NavController) {

    TopAppBar(

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Update Note Screen",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold, color = Color.Black
                    )
                )
            }

        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },

        actions = {
            Spacer(modifier = Modifier.size(48.dp))
        }
    )
}
