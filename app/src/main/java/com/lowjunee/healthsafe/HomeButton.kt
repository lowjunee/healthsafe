package com.lowjunee.healthsafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.ui.theme.PrimaryColor
import com.lowjunee.healthsafe.ui.theme.WhiteColor

@Composable
fun HomeButton(
    text: String,
    width: Dp = 150.dp,
    height: Dp = 60.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width, height)
            .background(
                color = PrimaryColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = WhiteColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
