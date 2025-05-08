package com.example.muapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.muapp.presentation.navigation.MUAPPNavGraph
import com.example.muapp.presentation.screens.Login.LoginForm
import com.example.muapp.ui.theme.MUAPPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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


