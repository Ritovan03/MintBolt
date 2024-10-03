package com.example.mintbolt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mintbolt.R

@Composable
fun ProfileScreen(navController: NavHostController) {
    val backgroundColor = Color(0xFF00C853)
    val cardColor = Color(0xFF00A846)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bgcolor))

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TopBarr()
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseSummaryCards(cardColor)
        }
        Spacer(modifier = Modifier.height(48.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 52.dp, topEnd = 52.dp))
        ) {

        }
    }
}