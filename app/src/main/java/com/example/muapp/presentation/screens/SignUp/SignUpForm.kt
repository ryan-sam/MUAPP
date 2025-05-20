package com.example.muapp.presentation.screens.SignUp

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.muapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun SignUpForm(navController: NavController) {
    val context =LocalContext.current // declares current processing activity
    var confirmpassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember {mutableStateOf<String?>(null)} //capture and ref errors

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    painter = painterResource(R.drawable.sign),
                    contentDescription = "APP logo",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )
                // logo
                // space
                Spacer(modifier = Modifier.height(32.dp))
                // username text field


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(" Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(" Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Gray
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmpassword,
                    onValueChange = { confirmpassword = it },
                    label = { Text(" Confirm password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Gray
                    ),
                    visualTransformation = PasswordVisualTransformation()

                )
                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                       if (password != confirmpassword) {
                           error = "Password does not match!!"
                       }  else{
                           // register user
                           registerUser(email, password,
                               context,navController,
                               onError =  {errorMsg -> error = errorMsg})
                       }
                    }
                )
                 {
                    Text(text = "Sign up", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = { navController.navigate("login") },
                ){
                    Text("Already have an account?login")
                }
            }
        }
    }
}

 fun registerUser(
    email: String,
    password: String,
    context: Context,
    navController: NavController,
    onError: (String) -> Unit
) {
     Firebase.auth.createUserWithEmailAndPassword(email, password)
         .addOnCompleteListener{
         task -> if (task.isSuccessful){
             navController.navigate("login")
         } else{
             onError(task.exception?.message ?:
             "Registration Failed")
         }
     }
}

@Preview(showBackground = true)
@Composable
fun SignUpFormPreview(){
    SignUpForm(rememberNavController())


    }

