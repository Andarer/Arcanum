package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.hypot
import kotlin.random.Random

data class TargetBall(
    var id: Long,
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float = 40f
)

@Composable
fun ShooterScreen(
    onAddGold: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var score by remember { mutableIntStateOf(0) }
    var isGameRunning by remember { mutableStateOf(false) }
    val targets = remember { mutableStateListOf<TargetBall>() }

    // Game Loop
    LaunchedEffect(isGameRunning) {
        if (isGameRunning) {
            targets.clear()
            score = 0
            var idCounter = 0L

            while (isGameRunning) {
                delay(30) // ~30 FPS frame tick

                // Spawn new targets periodically
                if (targets.size < 5 && Random.nextFloat() < 0.08f) {
                    targets.add(
                        TargetBall(
                            id = idCounter++,
                            x = Random.nextFloat() * 700f + 50f,
                            y = 0f,
                            speed = Random.nextFloat() * 4f + 2f
                        )
                    )
                }

                // Move targets
                val iterator = targets.iterator()
                while (iterator.hasNext()) {
                    val ball = iterator.next()
                    ball.y += ball.speed
                    if (ball.y > 1000f) {
                        iterator.remove()
                    }
                }
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
            text = "АРКАДНЫЙ ТИР",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Счет: $score", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldAccent)

            Button(
                onClick = { isGameRunning = !isGameRunning },
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isGameRunning) "Стоп" else "Старт Игры",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Shooter Canvas Game Screen
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            border = BorderStroke(2.dp, GoldAccent),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(isGameRunning) {
                        if (isGameRunning) {
                            detectTapGestures { offset ->
                                val iterator = targets.iterator()
                                while (iterator.hasNext()) {
                                    val ball = iterator.next()
                                    val dist = hypot(ball.x - offset.x, ball.y - offset.y)
                                    if (dist <= ball.radius * 2f) {
                                        iterator.remove()
                                        score += 10
                                        onAddGold(5)
                                        break
                                    }
                                }
                            }
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    targets.forEach { ball ->
                        drawCircle(
                            color = GoldAccent,
                            radius = ball.radius,
                            center = Offset(ball.x, ball.y)
                        )
                        drawCircle(
                            color = GoldLight,
                            radius = ball.radius * 0.5f,
                            center = Offset(ball.x, ball.y)
                        )
                    }
                }

                if (!isGameRunning) {
                    Text(
                        text = "Нажмите 'Старт Игры' и касайтесь летящих шаров!",
                        color = GoldLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
