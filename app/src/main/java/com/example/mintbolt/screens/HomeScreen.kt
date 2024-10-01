package com.example.mintbolt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ContentAlpha
import com.example.mintbolt.navigation.BottomNavItem


@Composable
fun HomeScreen(navController: NavHostController)  {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            selectedIcon = Icons.Outlined.Home,
            route = "homecontent"
        ),
        BottomNavItem(
            label = "Analytics",
            icon = Icons.Filled.ThumbUp,
            selectedIcon = Icons.Outlined.ThumbUp,
            route = "analytics"
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            selectedIcon = Icons.Outlined.Person,
            route = "profile"
        )
    )

    Scaffold(
        bottomBar = {
            Box {
                BottomAppBar(
                    modifier = Modifier
                        .height(88.dp)
                        .background(Color.White),
                    containerColor = Color.White,
                    content = {
                        items.forEachIndexed { index, item ->
                            if (index != 3) {
                                IconButton(
                                    onClick = { selectedItem = index },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = if (selectedItem == index) item.selectedIcon else item.icon,
                                            contentDescription = item.label,
                                            tint = if (selectedItem == index) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(
                                                alpha = ContentAlpha.medium
                                            )
                                        )
                                        Text(
                                            text = item.label,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedItem == index) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(
                                                alpha = ContentAlpha.medium
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> HomeContent(navController)
                1 -> AnalyticsScreen(navController)
                2 -> ProfileScreen(navController)
            }
        }
    }
}