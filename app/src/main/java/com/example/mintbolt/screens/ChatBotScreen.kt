package com.example.mintbolt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mintbolt.R

@Composable
fun ChatBotScreen(navController: NavHostController) {
ChatbotUI()
}

@Composable
fun ChatbotUI() {
    var messages by remember { mutableStateOf(listOf("Hello! How can I help you today?")) }
    var showOptions by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat messages
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            messages.forEach { message ->
                ChatMessage(message)
            }
        }

        // Show options for the user
        if (showOptions) {
            ChatbotOptions(onOptionSelected = { option ->
                messages = messages + "User: $option"
                showOptions = false
                // Add chatbot response based on the selection
                when (option) {
                    "Book a Flight" -> messages = messages + "Great! Let's book your flight."
                    "Book a Hotel" -> messages = messages + "Sure! Let's find a hotel for you."
                    "Check Booking" -> messages = messages + "Let me fetch your booking details."
                }
            })
        }
    }
}

@Composable
fun ChatMessage(message: String) {
    Text(
        text = message,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    )
}

@Composable
fun ChatbotOptions(onOptionSelected: (String) -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val options = listOf("Book a Flight", "Book a Hotel", "Check Booking")

        options.forEach { option ->
            Text(
                text = option,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = colorResource(id = R.color.bgcolor),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .clickable { onOptionSelected(option) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatbotUI() {
    ChatbotUI()
}