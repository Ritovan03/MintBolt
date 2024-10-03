package com.example.mintbolt.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mintbolt.R

@Composable
fun AnalyticsScreen(navController: NavHostController) {
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
                Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){

                    Image(painter = painterResource(id = R.drawable.daytime),
                        contentDescription = "day and month",
                        modifier = Modifier.padding(top = 0.dp)
                            .offset(y = -96.dp)
                            .size(250.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(painter = painterResource(id = R.drawable.analytics_drawing),
                        contentDescription = "day and month",
                        modifier = Modifier.padding(top = 0.dp)
                            .offset(y = -100.dp)
                            .height(500.dp),
                        contentScale = ContentScale.FillBounds
                    )


                }

        }

    }
}

@Composable
fun TopBarr() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Analysis",
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 28.dp),
                textAlign = TextAlign.Center
            )

        }
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "Notifications",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ExpenseSummaryCards(backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bgcolor))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Total Balance", color = Color.White, fontSize = 14.sp)
                    Text("$7,783.00", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                VerticalDivider(modifier = Modifier.height(64.dp),
                    thickness = 2.dp)
                Column(horizontalAlignment = Alignment.End) {
                    Text("Total Expense", color = Color.White, fontSize = 14.sp)
                    Text("-$1,187.40", color = Color.Red, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomProgressBar(progress = 0.30f, amount = "$20,000.00")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "35% of Your Expenses Looks Good",
                color = Color.White,
                fontSize = 14.sp
            )


        }


    }
}

