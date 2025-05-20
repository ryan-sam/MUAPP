 package com.example.muapp.presentation.screens.addToDo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.muapp.data.model.TodoItem
import com.example.muapp.data.repository.MockToDoRepository
import com.example.muapp.presentation.screens.dashboard.DashboardViewModel

// 1. add the viewmodel with fun to operate
// 2. onDismiss as our  form will be showcased on a pop up
@Composable
fun EditToDoForm(
    todo: TodoItem,
    onSubmit: (TodoItem) -> Unit,
    onDismiss: () -> Unit


){
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }   // create variables to save inputs
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? -> imageUri = uri }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tasker by remember { mutableStateOf("") }

    Column ( modifier = Modifier.padding(16.dp).fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = "Edit  To Do",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = title, onValueChange = {title = it},
            label = {Text("Title")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = description, onValueChange = {title = it},
            label = {Text("Description")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = tasker, onValueChange = {title = it},
            label = {Text("Tasker Name")},
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors()
            ){
                Text("Cancel")
            }
            Button(onClick = {
                if (title.isNotBlank()){
                    val updated = todo.copy(
                        title = title,
                        description = description,
                        tasker = tasker
                    )
                    onSubmit(updated)
                                onDismiss()
//
                }
            }, enabled = title.isNotBlank()) {
                Text("save changes")
            }
        }
    }
}


@Preview(showBackground = true)
    @Composable
    fun EditToDoFormPreview(){
        val todo = TodoItem(1,"1","food","playing football","","task",2025,false)
        EditToDoForm(todo, {}, {})
    }


