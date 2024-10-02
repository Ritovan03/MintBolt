package com.example.mintbolt.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mintbolt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController)
{

    val backgroundColor = Color(0xFF00C853)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.bgcolor)

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome",
                modifier = Modifier.padding(top = 64.dp),
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RoundedCornerShape(topStart = 52.dp, topEnd = 52.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.primary_bg)
                ),
            ) {
                Column(
                    modifier = Modifier.padding(top = 52.dp, start = 32.dp, end = 32.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("example@example.com") }, // This is the label, which floats to the top
                        placeholder = { Text("Enter your email", color = colorResource(id = R.color.primary_text)) }, // Optional placeholder
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(38.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.primary_text),
                            unfocusedBorderColor = colorResource(id = R.color.primary_text),
                            containerColor = colorResource(id = R.color.primary_text)
                        )
                    )


                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(38.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) {
                                            R.drawable.visibility
                                        } else {
                                            R.drawable.visibility_off
                                        }
                                    ), contentDescription = " "
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.primary_text),
                            unfocusedBorderColor = colorResource(id = R.color.primary_text),
                            containerColor = colorResource(id = R.color.primary_text)
                        )
                    )
                }


                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        navController.navigate("main")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 128.dp, end = 128.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bgcolor))
                ) {
                    Text("Log In", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(2.dp))

                TextButton(onClick = { /* Handle forgot password */
                    navController.navigate("rp1")
                },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 3.dp)){
                    Text("Forgot Password?", color = Color.Black,
                        fontSize = 14.sp)

                }

                Spacer(modifier = Modifier.height(2.dp))

                Button(onClick = { /* Handle sign up */
                    navController.navigate("signup")
                },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.secondary_text))
                ) {
                    Text("Sign Up", color = colorResource(id = R.color.tertiary_text))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Use  ", color = Color.Black)
                    Text(text = "Fingerprint  ",color = Color.Blue)
                    Text("To Access", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))


            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavHostController(LocalContext.current))
}


