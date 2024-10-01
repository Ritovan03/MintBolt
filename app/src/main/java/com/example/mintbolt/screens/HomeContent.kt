package com.example.mintbolt.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeContent(navController: NavHostController)
{

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("this is home screen")
    }
}
