package com.example.mintbolt.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mintbolt.R

@Composable
fun HomeContent(navController: NavHostController)
{

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
            TopBar()
            Spacer(modifier = Modifier.height(16.dp))
            ExpenseSummaryCard(cardColor)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 52.dp, topEnd = 52.dp))
        ) {
            ChatbotButton(navController)
            Spacer(modifier = Modifier.height(16.dp))
            FeaturesGrid()

        }

    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Hi Alice Smith",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Good Morning",
                color = Color.White,
                fontSize = 14.sp
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
fun ExpenseSummaryCard(backgroundColor: Color) {
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

@Composable
fun ChatbotButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("chatbot")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp,end = 24.dp,top = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bgcolor))
    ) {
        Text("Converse With Chatbot", color = Color.Black)
    }
}

@Composable
fun FeaturesGrid() {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FeatureItem("Document\nClassification", R.drawable.document_icon)
            FeatureItem("Manage\nTransactions", R.drawable.transactions_icon)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FeatureItem("Query\nDocuments", R.drawable.query_icon)
            FeatureItem("Bill Split", R.drawable.bill_split_icon)
        }
    }
}

@Composable
fun FeatureItem(title: String, iconResId: Int) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bgcolor))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier.size(65.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    HomeContent(navController = NavHostController(LocalContext.current))
}

@Composable
fun CustomProgressBar(progress: Float, amount: String) {
    val progressColor = Color(0xFF003238)  // Dark color for the progress bar
    val trackColor = Color(0xFFD8F3DC)     // Light background track color
    val backgroundColor = Color(0xFF2EC4B6) // Green background

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(50.dp)) // Background rounded
            .padding(4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .clip(RoundedCornerShape(50.dp)),
            color = progressColor,
            trackColor = trackColor
        )

        // Overlay for percentage and dollar amount
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${(progress * 100).toInt()}%",
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                color = Color.White,

            )
            Text(
                text = amount,
                color = Color(0xFF003238),

            )
        }
    }
}

