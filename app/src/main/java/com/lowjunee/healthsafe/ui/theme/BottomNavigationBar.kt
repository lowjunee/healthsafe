package com.lowjunee.healthsafe.ui.theme

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Define a lighter shade of PrimaryColor
val LighterPrimaryColor = PrimaryColor.copy(alpha = 0.6f)

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tips Tab
            NavigationBarItem(
                selected = selectedTab == "tips",
                onClick = { onTabSelected("tips") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tips",
                        modifier = Modifier
                            .size(if (selectedTab == "tips") 45.dp else 30.dp)
                            .animateContentSize(animationSpec = spring()),
                        tint = if (selectedTab == "tips") Color(0xFFB00020) else LighterPrimaryColor
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(50.dp)) // Add spacing between items

            // Home Tab (centered)
            NavigationBarItem(
                selected = selectedTab == "home",
                onClick = { onTabSelected("home") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier
                            .size(if (selectedTab == "home") 45.dp else 30.dp)
                            .animateContentSize(animationSpec = spring()), // Scale animation
                        tint = if (selectedTab == "home") Color(0xFFB00020) else LighterPrimaryColor
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(50.dp)) // Add spacing between items

            // Profile Tab
            NavigationBarItem(
                selected = selectedTab == "profile",
                onClick = { onTabSelected("profile") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(if (selectedTab == "profile") 45.dp else 30.dp)
                            .animateContentSize(animationSpec = spring()),
                        tint = if (selectedTab == "profile") Color(0xFFB00020) else LighterPrimaryColor
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
    }
}
