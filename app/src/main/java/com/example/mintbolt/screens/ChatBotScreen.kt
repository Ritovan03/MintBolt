package com.example.mintbolt.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mintbolt.R
import com.example.mintbolt.viewmodel.ExpensesViewModel
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun ChatBotScreen(navController: NavHostController) {
    Spacer(modifier = Modifier.height(12.dp))
    ChatbotUI()
}


class ComposeFileProvider : FileProvider(R.xml.filepaths) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file
            )
        }
    }
}

@Composable
fun ChatbotUI() {
    val viewModel: ExpensesViewModel = viewModel()
    var messages by remember { mutableStateOf(listOf("ChatBot: Hello! How can I help you today?")) }
    var showOptions by remember { mutableStateOf(true) }
    var showSubOptions by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }
    var allowUserInput by remember { mutableStateOf(true) }
    var allowCameraInput by remember { mutableStateOf(false) }
    var allowGalleryInput by remember { mutableStateOf(false) }
    var allowTextInput by remember { mutableStateOf(true) }
    var shouldFetchWithDelay by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            messages = messages + "User: [Image uploaded]"
            allowUserInput = true
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            messages = messages + "User: [Image uploaded]"
            allowUserInput = true
        }
    }

    // Collect state flows
    val expensesSummary by viewModel.expensesSummary.collectAsState()
    val barPlot by viewModel.barPlot.collectAsState()
    val pieChart by viewModel.pieChart.collectAsState()
    val heatmap by viewModel.heatmap.collectAsState()
    val arima by viewModel.arima.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(shouldFetchWithDelay) {
        if (shouldFetchWithDelay) {
            viewModel.fetchBarPlot(1000)
            delay(1000) // 1-second delay
            viewModel.fetchPieChart(1000)
            delay(1000) // 1-second delay
            viewModel.fetchHeatmap(1000)
            shouldFetchWithDelay = false // Reset the flag
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat messages (scrollable)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(messages) { message ->
                val (sender, content) = if (":" in message) {
                    message.split(": ", limit = 2)
                } else {
                    listOf("ChatBot", message)
                }
                ChatMessage(sender = sender, content = content)
            }

            // Display images if available
            item {
                expensesSummary?.let { summary ->
                    Text("Expenses Summary: $summary")
                }
                barPlot?.let { plotUrl ->
                    DisplayImage(base64String = plotUrl)
                }
                pieChart?.let { chartUrl ->
                    DisplayImage(base64String = chartUrl)
                }
                heatmap?.let { mapUrl ->
                    DisplayImage(base64String = mapUrl)
                }
                arima?.let { arimaUrl ->
                    DisplayImage(base64String = arimaUrl)
                }
                error?.let { errorMessage ->
                    Text("Error: $errorMessage", color = Color.Red)
                }
            }
        }

        // Options and input area (fixed at bottom)
        Column {
            // Show options for the user
            if (showOptions) {
                ChatbotOptions(onOptionSelected = { option ->
                    messages = messages + "User: $option"
                    when (option) {
                        "Give Historical Expense and Future Prediction" -> {
                            showSubOptions = true
                            showOptions = false
                        }
                        else -> {
                            showOptions = false
                            allowUserInput = true
                            messages = messages + when (option) {
                                "Query my Document" -> "ChatBot: Sure, I can help you query your document. What would you like to know?"
                                "Summarise this invoice" -> "ChatBot: Certainly! I'd be happy to summarize an invoice for you. Please upload or provide details of the invoice you'd like summarized."
                                else -> "ChatBot: I'm sorry, I don't have a specific response for that option. How else can I assist you?"
                            }
                        }
                    }
                })
            }

            // Show sub-options if "Give Historical Expense and Future Prediction" was selected
            if (showSubOptions) {
                ChatbotSubOptions(onSubOptionSelected = { subOption ->
                    messages = messages + "User: $subOption"
                    showSubOptions = false
                    allowUserInput = true
                    when (subOption) {
                        "Summarise My Expenses" -> {
                            viewModel.fetchExpensesSummary(1000) // Assuming employee ID is 1
                            messages = messages + "ChatBot: Fetching your expense summary. Please wait..."
                        }
                        "Analyse Trends & Expenditure Patterns" -> {
                            viewModel.fetchARIMA(1000) // Assuming employee ID is 1
                            messages = messages + "ChatBot: Analyzing trends and expenditure patterns. This may take a moment..."
                        }
                        "Expense Tracking" -> {
                            shouldFetchWithDelay = true
                            messages = messages + "ChatBot: Generating expense tracking visualizations. Please wait..."
                        }
                    }
                })
            }

            // User input section
            if (allowUserInput) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        modifier = Modifier.weight(1.5f),
                        placeholder = { Text("Type your message...") }
                    )
                    IconButton(
                        onClick = {
                            if (userInput.isNotBlank()) {
                                messages = messages + "User: $userInput"
                                messages = messages + "ChatBot: I've received your message. How else can I assist you?"
                                userInput = ""
                            }
                        },
                        enabled = allowTextInput
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                    }
                    IconButton(
                        onClick = {
                            imageUri = ComposeFileProvider.getImageUri(context)
                            cameraLauncher.launch(imageUri!!)
                        },
                        enabled = allowCameraInput
                    ) {
                        Icon(painter = painterResource(id = R.drawable.camera_ic), contentDescription = "Camera")
                    }
                    IconButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        enabled = allowGalleryInput
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_gallery), contentDescription = "Gallery")
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayImage(base64String: String) {
    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    val imageBitmap = bitmap.asImageBitmap()

    Image(
        bitmap = imageBitmap,
        contentDescription = "Generated Plot",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}



@Composable
fun ChatMessage(sender: String, content: String) {

    val isUser = sender == "User"
    val backgroundColor = if (isUser) colorResource(id = R.color.bgcolor) else Color(0xFFE0E0E0)
    val textColor = if (isUser) Color.White else Color.Black
    val alignment = if (isUser) Alignment.End else Alignment.Start
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = alignment
    ) {
        Text(
            text = content,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .align(alignment)
        )
    }
}

@Composable
fun ChatbotOptions(onOptionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val options = listOf("Query my Document", "Summarise this invoice", "Give Historical Expense and Future Prediction")

        options.forEach { option ->
            Text(
                text = option,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.bgcolor),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .clickable { onOptionSelected(option) }
            )
        }
    }
}

@Composable
fun ChatbotSubOptions(onSubOptionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val subOptions = listOf("Summarise My Expenses", "Analyse Trends & Expenditure Patterns", "Expense Tracking")

        subOptions.forEach { option ->
            Text(
                text = option,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.black),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.bgcolor),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .clickable { onSubOptionSelected(option) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewChatbotUI() {
    ChatbotUI()
}