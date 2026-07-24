package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CustomQrCodeCanvas
import com.example.ui.theme.*

@Composable
fun QrScreen(
    modifier: Modifier = Modifier
) {
    var cardIdText by remember { mutableStateOf("ARC-0001-WARRIOR") }
    var extraDataText by remember { mutableStateOf("hp:120;str:18;def:14") }

    val fullPayload = remember(cardIdText, extraDataText) {
        "$cardIdText|$extraDataText"
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "QR-ГЕНЕРАТОР",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Создай QR-код для физической карты или синхронизации",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cardIdText,
            onValueChange = { cardIdText = it },
            label = { Text("ID карты") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = extraDataText,
            onValueChange = { extraDataText = it },
            label = { Text("Дополнительные данные") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                cardIdText = "ARC-${(1000..9999).random()}-RANDOM"
                extraDataText = "hp:${(50..200).random()};str:${(10..40).random()}"
            },
            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⟳ Сгенерировать случайный", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // QR Output Card
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "РЕЗУЛЬТАТ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomQrCodeCanvas(
                    data = fullPayload,
                    modifier = Modifier
                        .size(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = fullPayload,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
