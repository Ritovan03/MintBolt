package com.example.mintbolt.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ResetPass2(navController: NavHostController) {

    var otpValue by remember { mutableStateOf("") }
    val focusRequesters = remember { List(4) { FocusRequester() } }


    Box(modifier = Modifier.fillMaxSize()) {
        // Back button
        IconButton(
            onClick = { navController.navigate("rp1") },
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Text(
                text = "Verification",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We've send you the verification\ncode on your email address",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(4) { index ->
                    BasicTextField(
                        value = otpValue.getOrNull(index)?.toString() ?: "",
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                val newOtp = otpValue.toMutableList()
                                if (it.isEmpty()) {
                                    if (index > 0) {
                                        newOtp.removeAt(index - 1)
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                } else {
                                    if (index == otpValue.length) newOtp.add(it[0])
                                    else newOtp[index] = it[0]
                                    if (index < 3) focusRequesters[index + 1].requestFocus()
                                }
                                otpValue = newOtp.joinToString("")
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Backspace && otpValue.getOrNull(index) == null && index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                    true
                                } else false
                            },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("rp3") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4E5AF2),
                    contentColor = Color.White
                ),
                enabled = otpValue.length == 4
            ) {
                Text("CONTINUE", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))




        }
    }
}