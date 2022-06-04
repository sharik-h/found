package com.example.found

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.found.LoginNavigation.NavGraph
import com.example.found.data.firestoreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: firestoreViewModel by viewModels()
           val navHostController = rememberNavController()

            NavGraph(navHostController = navHostController, viewModel = viewModel)
        }
    }
}

