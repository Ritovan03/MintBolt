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
    var currentMainOption by remember { mutableStateOf("") }
    var userInput by remember { mutableStateOf("") }
    var allowUserInput by remember { mutableStateOf(true) }
    var allowCameraInput by remember { mutableStateOf(false) }
    var allowGalleryInput by remember { mutableStateOf(false) }
    var allowTextInput by remember { mutableStateOf(true) }
    var shouldFetchWithDelay by remember { mutableStateOf(false) }
    var isInGeneralQueryMode by remember { mutableStateOf(false) }

    //val generalQueryResponse by viewModel.generalQueryResponse.collectAsState()
    val err by viewModel.err.collectAsState()

//    LaunchedEffect(generalQueryResponse) {
//        generalQueryResponse?.let {
//            messages = messages + "ChatBot: $it"
//        }
//    }

    LaunchedEffect(err) {
        err?.let {
            messages = messages + "ChatBot: $it"
        }
    }

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
        Spacer(modifier = Modifier.height(24.dp))
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
                err?.let { errorMessage ->
                    Text("Error: $errorMessage", color = Color.Red)
                }
            }
        }

        // Options and input area (fixed at bottom)
        Column {
            if (showOptions) {
                ChatbotOptions(onOptionSelected = { option ->
                    messages = messages + "User: $option"
                    currentMainOption = option
                    if (option == "General Queries") {
                        isInGeneralQueryMode = true
                        showOptions = false
                        allowUserInput = true
                        messages = messages + "ChatBot: You're now in general query mode. Feel free to ask any question!"
                    } else {
                        showSubOptions = true
                        showOptions = false
                    }
                })
            }

            if (showSubOptions) {
                ChatbotSubOptions(
                    mainOption = currentMainOption,
                    onSubOptionSelected = { subOption ->
                        messages = messages + "User: $subOption"
                        showSubOptions = false
                        allowUserInput = true
                        when (currentMainOption) {
                            "Transaction Management & Analysis" -> handleTransactionManagement(subOption, viewModel, messages)
                            "Document Processing & Analysis" -> handleDocumentProcessing(subOption, messages)
                            "Payment & Invoicing" -> handlePaymentAndInvoicing(subOption, messages)
                            "Budgeting & Finance Management" -> handleBudgetingAndFinance(subOption, messages)
                            "General Queries" -> handleGeneralQueries(subOption, messages)
                        }
                    }
                )
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
                                if (isInGeneralQueryMode) {
                                    //viewModel.sendGeneralQuery(1000, userInput) // Assuming employee ID is 1000
                                    messages = messages + "ChatBot: Processing your query..."
                                } else {
                                    messages = messages + "ChatBot: I've received your message. How else can I assist you?"
                                }
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
        val options = listOf(
            "Document Processing & Analysis",
            "Payment & Invoicing",
            "Budgeting & Finance Management",
            "Transaction Management & Analysis",
            "General Queries"
        )

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
fun ChatbotSubOptions(mainOption: String, onSubOptionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val subOptions = when (mainOption) {
            "Transaction Management & Analysis" -> listOf("Summarise My Expenses", "Analyse Trends & Expenditure Patterns", "Expense Tracking", "Data Export to CSV")
            "Document Processing & Analysis" -> listOf("Named Entity Recognition", "AI Driven Document Classification", "Searching & Querying Document Data", "Digitise Document[Pdf/Png]")
            "Payment & Invoicing" -> listOf("Invoice Ingestion", "Debt & Loan Management", "Payment History")
            "Budgeting & Finance Management" -> listOf("Net Worth", "CTC Breakdown", "Set Monthly Budget")
            "General Queries" -> listOf("Start General Query Mode")
            else -> listOf()
        }

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

// Helper functions to handle different sub-options
private fun handleTransactionManagement(subOption: String, viewModel: ExpensesViewModel, messages: List<String>): List<String> {
    return when (subOption) {
        "Summarise My Expenses" -> {
            viewModel.fetchExpensesSummary(1000)
            messages + "ChatBot: Fetching your expense summary. Please wait..."
        }
        "Analyse Trends & Expenditure Patterns" -> {
            viewModel.fetchARIMA(1000)
            messages + "ChatBot: Analyzing trends and expenditure patterns. This may take a moment..."
        }
        "Expense Tracking" -> {
            viewModel.fetchBarPlot(1000)
            viewModel.fetchPieChart(1000)
            viewModel.fetchHeatmap(1000)
            messages + "ChatBot: Generating expense tracking visualizations. Please wait..."
        }
        "Data Export to CSV" -> {
            messages + "ChatBot: Preparing to export your data to CSV. This feature is coming soon."
        }
        else -> messages + "ChatBot: I'm sorry, I don't have a specific response for that option. How else can I assist you?"
    }
}

private fun handleDocumentProcessing(subOption: String, messages: List<String>): List<String> {
    return messages + when (subOption) {
        "Named Entity Recognition" -> {

            "ChatBot: Initiating Named Entity Recognition. Please upload a document to begin."
        }
        "AI Driven Document Classification" -> {
            "ChatBot: Ready to classify your document. Please upload a document to start."
        }
        "Searching & Querying Document Data" -> {
            "ChatBot: What would you like to search for in your documents?"
        }
        "Digitise Document[Pdf/Png]" -> {
            "ChatBot: Please upload a PDF or PNG file to digitize."
        }
        else -> "ChatBot: I'm sorry, I don't have a specific response for that option. How else can I assist you?"
    }
}

private fun handlePaymentAndInvoicing(subOption: String, messages: List<String>): List<String> {
    return messages + when (subOption) {
        "Invoice Ingestion" -> {
            "ChatBot: Ready to process your invoice. Please upload an invoice image or PDF."
        }
        "Debt & Loan Management" -> {
            "ChatBot: Let's review your debt and loan status. What specific information do you need?"
        }
        "Payment History" -> {
            "ChatBot: I'll fetch your payment history. Please specify the time period you're interested in."
        }
        else -> "ChatBot: I'm sorry, I don't have a specific response for that option. How else can I assist you?"
    }
}

private fun handleBudgetingAndFinance(subOption: String, messages: List<String>): List<String> {
    return messages + when (subOption) {
        "Net Worth" -> {
            "ChatBot: I'll calculate your net worth. Please provide your total assets and liabilities."
        }
        "CTC Breakdown" -> {
            "ChatBot: Let's break down your CTC. Can you provide your gross salary details?"
        }
        "Set Monthly Budget" -> {
            "ChatBot: Great! Let's set up your monthly budget. What's your target budget amount?"
        }
        else -> "ChatBot: I'm sorry, I don't have a specific response for that option. How else can I assist you?"
    }
}

private fun handleGeneralQueries(subOption: String, messages: List<String>): List<String> {
    return messages + "ChatBot: I'm ready to answer your general queries. What would you like to know?"
}


@Preview(showBackground = true)
@Composable
fun PreviewChatbotUI() {
    ChatbotUI()
}