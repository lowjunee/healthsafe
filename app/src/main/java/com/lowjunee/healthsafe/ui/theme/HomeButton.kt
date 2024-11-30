package com.lowjunee.healthsafe.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import com.lowjunee.healthsafe.ui.theme.WhiteColor

@Composable
fun HomeButton(
    text: String,
    iconId: Int? = null, // Optional icon
    color: Color = PrimaryColor,
    width: Dp = 350.dp,
    height: Dp = 90.dp,
    iconSize: Dp = 60.dp, // Icon size if used
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width, height)
            .background(color = color, shape = RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.White)
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (iconId != null) Arrangement.spacedBy(10.dp) else Arrangement.Center
        ) {
            // Show the icon only if iconId is provided
            iconId?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = text,
                    modifier = Modifier
                        .size(iconSize)
                        .padding(start = 16.dp), // Add left padding
                    tint = WhiteColor
                )
            }
            Text(
                text = text,
                color = WhiteColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = if (iconId != null) 8.dp else 0.dp) // Add padding only if icon is present
            )
        }
    }
}
