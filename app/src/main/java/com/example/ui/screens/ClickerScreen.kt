package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ClickerScreen(
    gold: Int,
    onAddGold: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var clickPower by remember { mutableIntStateOf(1) }
    var autoMiners by remember { mutableIntStateOf(0) }
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        animationSpec = tween(durationMillis = 100),
        label = "crystalScale"
    )

    // Auto-miner coroutine loop
    LaunchedEffect(autoMiners) {
        if (autoMiners > 0) {
            while (true) {
                kotlinx.coroutines.delay(1000)
                onAddGold(autoMiners)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "КРИСТАЛЛ АРКАНУМА",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )

        Text(
            text = "Кликайте по кристаллу для добычи Золота!",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Big Tap Target Crystal
        Surface(
            shape = CircleShape,
            color = BgDark2,
            border = BorderStroke(3.dp, GoldAccent),
            shadowElevation = 12.dp,
            modifier = Modifier
                .size(160.dp)
                .scale(scale)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isPressed = true
                    onAddGold(clickPower)
                }
        ) {
            LaunchedEffect(isPressed) {
                if (isPressed) {
                    kotlinx.coroutines.delay(100)
                    isPressed = false
                }
            }

            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "💎",
                    fontSize = 72.sp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "За клик: +$clickPower ◉", fontWeight = FontWeight.Bold, color = GoldLight)
            Text(text = "Авто-Майнеры: $autoMiners/сек", fontWeight = FontWeight.Bold, color = GoldAccent)
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BgDark1),
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        if (gold >= 50) {
                            onAddGold(-50)
                            clickPower += 1
                        }
                    },
                    enabled = gold >= 50,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.TouchApp, contentDescription = null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Улучшить Клик (+1) — 50 ◉", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        if (gold >= 150) {
                            onAddGold(-150)
                            autoMiners += 1
                        }
                    },
                    enabled = gold >= 150,
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Купить Авто-Майнер (+1/сек) — 150 ◉", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
