package com.lowjunee.healthsafe.ui.screen

import android.graphics.Bitmap
import android.graphics.Color as AndroidColor  // Import Android Color with alias to avoid conflicts
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color  // This is the Compose Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRResultScreen(onBackClick: () -> Unit) {
    // Simulate data to encode in QR code
    val randomData = "RandomData${(1..1000).random()}"
    val qrBitmap = generateQRCode(randomData)
    val imageBitmap = qrBitmap.asImageBitmap()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Generated QR Code", fontSize = 20.sp, color = Color.White) }, // Compose Color.White
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White // Compose Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                bitmap = imageBitmap,
                contentDescription = "QR Code",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

// Function to generate a QR Code from a string
fun generateQRCode(data: String): Bitmap {
    val qrCodeWriter = QRCodeWriter()
    val hints = mapOf(EncodeHintType.MARGIN to 1) // Optional hint for better margins
    val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512, hints)

    val width = bitMatrix.width
    val height = bitMatrix.height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) AndroidColor.BLACK else AndroidColor.WHITE) // Use AndroidColor for BLACK and WHITE
        }
    }

    return bitmap
}
