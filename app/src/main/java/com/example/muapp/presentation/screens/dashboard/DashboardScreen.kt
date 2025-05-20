@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.muapp.presentation.screens.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.muapp.data.model.TodoItem
import com.example.muapp.presentation.components.DrawerContent
import com.example.muapp.presentation.components.TodoItemCard
import com.example.muapp.presentation.components.onCompleteChange
import com.example.muapp.presentation.screens.addToDo.AddToDoForm
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

// is screen to contain composable elements to display my list of things
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()) {
    // fetch our todos from viewmodel
    val todos by viewModel.todos.collectAsState()
    val firebasetodos by viewModel.firebaseTodos.collectAsState()
    // to create a list of composables
    // add a dialog
    val showAddDialog = remember { mutableStateOf(false) }
    // show edit  dialog
    val showEditDialog = remember { mutableStateOf(false) }
    // selecccted item
    val todoBeingEdited = remember { mutableStateOf<TodoItem?>(null) }
    // drawer state reference
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // coroutine scope  : will handle configs on device change
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent ={DrawerContent(
            onNavigateToHome = {
                navController.navigate("dashboard")
            },
            onLogout = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login"){
                    popUpTo("dashboard")
                    {inclusive = true}
                }
            }
        )} ,
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Dash ,board")},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu,
                                contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddDialog.value = true }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Todo"
                    )
                }
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(firebasetodos) { todo ->
                    TodoItemCard(
                        // passing info to composable
                        todo = todo,
                        onCompleteChange = { viewModel.toogleTodoCompletion(todo.id) },
                        onEditClick = {
                            // raise an alert dialog
                            todoBeingEdited.value = todo
                            showEditDialog.value =  true


                        },
                        onDeleteClick = {
                            viewModel.deleteTodoFromFirebase(todo)

                        }
                    )

                }
            }
            if (showAddDialog.value) {
                // show pop up
                // AlertDialog is used to show pop up
                AlertDialog(
                    onDismissRequest = { showAddDialog.value = false },
                    title = { Text("Add Todo") },
                    text = {
                        AddToDoForm(
                            viewModel = viewModel,
                            onDismiss = { showAddDialog.value = false }
                        )
                    },
                    confirmButton = {},
                    dismissButton = {}
                )

            }

            if (showEditDialog.value &&
                todoBeingEdited.value != null){
                // show pop up
                AlertDialog(
                    onDismissRequest = {showEditDialog.value = false},
                    title =  {Text("Edit Todo")},
                    text = {Text("Edit your todo")},
                    confirmButton = {}
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview(){
    DashboardScreen(rememberNavController())
}