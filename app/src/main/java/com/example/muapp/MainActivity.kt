package com.example.muapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.muapp.presentation.navigation.MUAPPNavGraph
import com.example.muapp.ui.theme.MUAPPTheme
import com.google.firebase.FirebaseApp

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // initialize firebase for the app
        FirebaseApp.initializeApp(this)
        setContent {
            MUAPPTheme {
                val navController = rememberNavController()
                MUAPPNavGraph(navController)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // custom onstart logic for this activity

    }

}


