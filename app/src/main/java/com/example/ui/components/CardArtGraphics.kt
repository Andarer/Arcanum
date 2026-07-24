package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun CardArtGraphic(
    artKey: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF1A1B3A)
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(backgroundColor, Color(0xFF0A0B1A))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            when (artKey.lowercase()) {
                "warrior" -> {
                    // Shield & Sword graphic
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(GoldAccent.copy(alpha = 0.4f), Color.Transparent),
                            center = Offset(cx, cy)
                        ),
                        radius = w * 0.4f
                    )
                    // Shield
                    val path = Path().apply {
                        moveTo(cx - w * 0.25f, cy - h * 0.25f)
                        lineTo(cx + w * 0.25f, cy - h * 0.25f)
                        lineTo(cx + w * 0.25f, cy + h * 0.1f)
                        quadraticTo(cx + w * 0.25f, cy + h * 0.35f, cx, cy + h * 0.45f)
                        quadraticTo(cx - w * 0.25f, cy + h * 0.35f, cx - w * 0.25f, cy + h * 0.1f)
                        close()
                    }
                    drawPath(
                        path = path,
                        brush = Brush.linearGradient(
                            colors = listOf(GoldAccent, Color(0xFF8B6A1F))
                        )
                    )
                    drawPath(
                        path = path,
                        color = GoldLight,
                        style = Stroke(width = 3f)
                    )
                    // Cross emblem
                    drawRect(
                        color = Color(0xFFE05A6A),
                        topLeft = Offset(cx - 4f, cy - h * 0.15f),
                        size = Size(8f, h * 0.4f)
                    )
                    drawRect(
                        color = Color(0xFFE05A6A),
                        topLeft = Offset(cx - w * 0.15f, cy - 4f),
                        size = Size(w * 0.3f, 8f)
                    )
                }

                "archer" -> {
                    // Bow graphic
                    val bowPath = Path().apply {
                        moveTo(cx - w * 0.2f, cy - h * 0.35f)
                        quadraticTo(cx - w * 0.4f, cy, cx - w * 0.2f, cy + h * 0.35f)
                    }
                    drawPath(
                        path = bowPath,
                        brush = Brush.linearGradient(listOf(Color(0xFF8B6A1F), Color(0xFF5A3A0F))),
                        style = Stroke(width = 8f, cap = StrokeCap.Round)
                    )
                    // String
                    drawLine(
                        color = Color.White.copy(alpha = 0.8f),
                        start = Offset(cx - w * 0.2f, cy - h * 0.35f),
                        end = Offset(cx - w * 0.2f, cy + h * 0.35f),
                        strokeWidth = 2f
                    )
                    // Arrow
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(cx - w * 0.25f, cy),
                        end = Offset(cx + w * 0.3f, cy),
                        strokeWidth = 4f
                    )
                    val head = Path().apply {
                        moveTo(cx + w * 0.3f, cy - 8f)
                        lineTo(cx + w * 0.42f, cy)
                        lineTo(cx + w * 0.3f, cy + 8f)
                        close()
                    }
                    drawPath(head, color = Color(0xFFE05A6A))
                }

                "mage" -> {
                    // Staff with glowing orb
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White, PurpleAccent, Color.Transparent),
                            center = Offset(cx, cy - h * 0.15f),
                            radius = w * 0.35f
                        )
                    )
                    // Orb
                    drawCircle(
                        brush = Brush.linearGradient(listOf(PurpleLight, PurpleAccent)),
                        radius = w * 0.18f,
                        center = Offset(cx, cy - h * 0.15f)
                    )
                    // Staff handle
                    drawRect(
                        brush = Brush.linearGradient(listOf(Color(0xFF8B6A1F), Color(0xFF5A3A0F))),
                        topLeft = Offset(cx - 6f, cy - h * 0.05f),
                        size = Size(12f, h * 0.45f)
                    )
                }

                "necro" -> {
                    // Skull / dark aura
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(GreenSuccess.copy(alpha = 0.5f), Color.Transparent),
                            center = Offset(cx, cy)
                        ),
                        radius = w * 0.4f
                    )
                    drawCircle(
                        color = Color(0xFFF0E8D0),
                        radius = w * 0.22f,
                        center = Offset(cx, cy - 10f)
                    )
                    // Eyes
                    drawCircle(color = Color.Black, radius = w * 0.05f, center = Offset(cx - 12f, cy - 15f))
                    drawCircle(color = Color.Black, radius = w * 0.05f, center = Offset(cx + 12f, cy - 15f))
                    drawCircle(color = GreenSuccess, radius = w * 0.02f, center = Offset(cx - 12f, cy - 15f))
                    drawCircle(color = GreenSuccess, radius = w * 0.02f, center = Offset(cx + 12f, cy - 15f))
                }

                "dragon" -> {
                    // Fire dragon head silhouette
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFFDD44), Color(0xFFE05A6A), Color.Transparent),
                            center = Offset(cx, cy)
                        ),
                        radius = w * 0.45f
                    )
                    val dragonHead = Path().apply {
                        moveTo(cx - w * 0.25f, cy + h * 0.2f)
                        lineTo(cx - w * 0.1f, cy - h * 0.25f)
                        lineTo(cx + w * 0.15f, cy - h * 0.35f)
                        lineTo(cx + w * 0.35f, cy - h * 0.1f)
                        lineTo(cx + w * 0.2f, cy + h * 0.15f)
                        lineTo(cx, cy + h * 0.3f)
                        close()
                    }
                    drawPath(
                        path = dragonHead,
                        brush = Brush.linearGradient(listOf(Color(0xFFE05A6A), Color(0xFF8A2A3A)))
                    )
                }

                "goblin" -> {
                    drawCircle(
                        color = Color(0xFF7AB04A),
                        radius = w * 0.25f,
                        center = Offset(cx, cy)
                    )
                    // Ears
                    val leftEar = Path().apply {
                        moveTo(cx - 15f, cy)
                        lineTo(cx - w * 0.35f, cy - 20f)
                        lineTo(cx - 10f, cy + 15f)
                        close()
                    }
                    val rightEar = Path().apply {
                        moveTo(cx + 15f, cy)
                        lineTo(cx + w * 0.35f, cy - 20f)
                        lineTo(cx + 10f, cy + 15f)
                        close()
                    }
                    drawPath(leftEar, color = Color(0xFF7AB04A))
                    drawPath(rightEar, color = Color(0xFF7AB04A))
                    // Eyes
                    drawCircle(color = Color.Yellow, radius = 8f, center = Offset(cx - 10f, cy - 5f))
                    drawCircle(color = Color.Yellow, radius = 8f, center = Offset(cx + 10f, cy - 5f))
                    drawCircle(color = Color.Black, radius = 4f, center = Offset(cx - 10f, cy - 5f))
                    drawCircle(color = Color.Black, radius = 4f, center = Offset(cx + 10f, cy - 5f))
                }

                "sword" -> {
                    // Sword blade
                    val blade = Path().apply {
                        moveTo(cx, cy - h * 0.4f)
                        lineTo(cx + 10f, cy - h * 0.32f)
                        lineTo(cx + 8f, cy + h * 0.15f)
                        lineTo(cx - 8f, cy + h * 0.15f)
                        lineTo(cx - 10f, cy - h * 0.32f)
                        close()
                    }
                    drawPath(
                        path = blade,
                        brush = Brush.linearGradient(listOf(Color.White, Color.LightGray, Color.DarkGray))
                    )
                    // Guard
                    drawRect(
                        brush = Brush.linearGradient(listOf(GoldLight, GoldAccent)),
                        topLeft = Offset(cx - w * 0.22f, cy + h * 0.15f),
                        size = Size(w * 0.44f, 12f)
                    )
                    // Handle
                    drawRect(
                        color = Color(0xFF5A3A0F),
                        topLeft = Offset(cx - 6f, cy + h * 0.15f + 12f),
                        size = Size(12f, h * 0.18f)
                    )
                }

                "shield" -> {
                    val shield = Path().apply {
                        moveTo(cx - w * 0.28f, cy - h * 0.3f)
                        lineTo(cx + w * 0.28f, cy - h * 0.3f)
                        lineTo(cx + w * 0.28f, cy + h * 0.05f)
                        quadraticTo(cx + w * 0.28f, cy + h * 0.35f, cx, cy + h * 0.42f)
                        quadraticTo(cx - w * 0.28f, cy + h * 0.35f, cx - w * 0.28f, cy + h * 0.05f)
                        close()
                    }
                    drawPath(
                        path = shield,
                        brush = Brush.linearGradient(listOf(GoldAccent, Color(0xFF6A4A0F)))
                    )
                    drawPath(
                        path = shield,
                        color = GoldLight,
                        style = Stroke(width = 4f)
                    )
                }

                "potion" -> {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(RedDanger.copy(alpha = 0.5f), Color.Transparent),
                            center = Offset(cx, cy + 10f)
                        ),
                        radius = w * 0.35f
                    )
                    // Bottle
                    val bottle = Path().apply {
                        moveTo(cx - 12f, cy - h * 0.25f)
                        lineTo(cx + 12f, cy - h * 0.25f)
                        lineTo(cx + 12f, cy - h * 0.1f)
                        lineTo(cx + w * 0.25f, cy + h * 0.1f)
                        quadraticTo(cx + w * 0.25f, cy + h * 0.35f, cx, cy + h * 0.35f)
                        quadraticTo(cx - w * 0.25f, cy + h * 0.35f, cx - w * 0.25f, cy + h * 0.1f)
                        lineTo(cx - 12f, cy - h * 0.1f)
                        close()
                    }
                    drawPath(bottle, color = RedDanger)
                    drawPath(bottle, color = Color.White.copy(alpha = 0.6f), style = Stroke(width = 3f))
                    // Cork
                    drawRect(color = Color(0xFF8B6A1F), topLeft = Offset(cx - 10f, cy - h * 0.33f), size = Size(20f, h * 0.08f))
                }

                "ring" -> {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(CyanAccent.copy(alpha = 0.6f), Color.Transparent),
                            center = Offset(cx, cy)
                        ),
                        radius = w * 0.38f
                    )
                    drawCircle(
                        brush = Brush.linearGradient(listOf(GoldLight, GoldAccent)),
                        radius = w * 0.22f,
                        center = Offset(cx, cy + 10f),
                        style = Stroke(width = 16f)
                    )
                    // Gem
                    val gem = Path().apply {
                        moveTo(cx, cy - h * 0.22f)
                        lineTo(cx + 14f, cy - h * 0.1f)
                        lineTo(cx, cy)
                        lineTo(cx - 14f, cy - h * 0.1f)
                        close()
                    }
                    drawPath(gem, color = CyanAccent)
                }

                "crystal" -> {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(PurpleAccent.copy(alpha = 0.6f), CyanAccent.copy(alpha = 0.3f), Color.Transparent),
                            center = Offset(cx, cy)
                        ),
                        radius = w * 0.42f
                    )
                    val crystal = Path().apply {
                        moveTo(cx, cy - h * 0.38f)
                        lineTo(cx + w * 0.2f, cy - h * 0.1f)
                        lineTo(cx + w * 0.15f, cy + h * 0.3f)
                        lineTo(cx - w * 0.15f, cy + h * 0.3f)
                        lineTo(cx - w * 0.2f, cy - h * 0.1f)
                        close()
                    }
                    drawPath(
                        path = crystal,
                        brush = Brush.linearGradient(listOf(CyanAccent, PurpleAccent, Color(0xFFE05A9A)))
                    )
                    drawPath(crystal, color = Color.White.copy(alpha = 0.6f), style = Stroke(width = 2f))
                }

                "pet" -> {
                    // Winged pet
                    drawCircle(color = GoldAccent, radius = w * 0.2f, center = Offset(cx, cy))
                    // Wings
                    val leftWing = Path().apply {
                        moveTo(cx - 15f, cy)
                        quadraticTo(cx - w * 0.4f, cy - h * 0.25f, cx - w * 0.15f, cy + 10f)
                    }
                    val rightWing = Path().apply {
                        moveTo(cx + 15f, cy)
                        quadraticTo(cx + w * 0.4f, cy - h * 0.25f, cx + w * 0.15f, cy + 10f)
                    }
                    drawPath(leftWing, color = GoldLight)
                    drawPath(rightWing, color = GoldLight)
                    // Beak
                    val beak = Path().apply {
                        moveTo(cx - 6f, cy + 4f)
                        lineTo(cx, cy + 16f)
                        lineTo(cx + 6f, cy + 4f)
                    }
                    drawPath(beak, color = Color(0xFFE0A84A))
                }

                else -> {
                    drawCircle(color = GoldAccent.copy(alpha = 0.5f), radius = w * 0.3f, center = Offset(cx, cy))
                }
            }
        }
    }
}

/* Helper function for Rarity Color */
fun getRarityColor(rarity: String): Color {
    return when (rarity.lowercase()) {
        "common" -> RareCommon
        "uncommon" -> RareUncommon
        "rare" -> RareRare
        "epic" -> RareEpic
        "legendary" -> RareLegendary
        "mythic" -> RareMythic
        else -> RareCommon
    }
}

fun getRarityLabel(rarity: String): String {
    return when (rarity.lowercase()) {
        "common" -> "ОБЫЧНАЯ"
        "uncommon" -> "НЕОБЫЧНАЯ"
        "rare" -> "РЕДКАЯ"
        "epic" -> "ЭПИЧЕСКАЯ"
        "legendary" -> "ЛЕГЕНДАРНАЯ"
        "mythic" -> "МИФИЧЕСКАЯ"
        else -> rarity.uppercase()
    }
}

@Composable
fun RarityBadge(rarity: String, modifier: Modifier = Modifier) {
    val color = getRarityColor(rarity)
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Text(
            text = getRarityLabel(rarity),
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun CustomQrCodeCanvas(data: String, modifier: Modifier = Modifier, moduleSize: Float = 8f) {
    Canvas(modifier = modifier) {
        val size = 21
        val quiet = 2
        val totalModules = size + quiet * 2
        val modWidth = this.size.width / totalModules

        // White background
        drawRect(Color.White)

        // Deterministic matrix generation based on string hash
        var hash = 2166136261L
        for (char in data) {
            hash = hash xor char.code.toLong()
            hash = (hash * 16777619) and 0xFFFFFFFFL
        }

        fun pseudoRand(): Float {
            hash = (hash * 1664525 + 1013904223) and 0xFFFFFFFFL
            return (hash shr 8) / 16777216f
        }

        val matrix = Array(size) { BooleanArray(size) }
        for (y in 0 until size) {
            for (x in 0 until size) {
                matrix[y][x] = pseudoRand() > 0.5f
            }
        }

        // Draw finder patterns
        fun drawFinder(ox: Int, oy: Int) {
            for (y in 0 until 7) {
                for (x in 0 until 7) {
                    val isOuter = x == 0 || x == 6 || y == 0 || y == 6
                    val isInner = x in 2..4 && y in 2..4
                    matrix[oy + y][ox + x] = isOuter || isInner
                }
            }
        }

        drawFinder(0, 0)
        drawFinder(size - 7, 0)
        drawFinder(0, size - 7)

        // Clear timing / separator zones
        for (i in 0 until 8) {
            if (i < size) {
                matrix[7][i] = false
                matrix[i][7] = false
                matrix[7][size - 1 - i] = false
                matrix[i][size - 8] = false
                matrix[size - 8][i] = false
                matrix[size - 1 - i][7] = false
            }
        }

        // Render black modules
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (matrix[y][x]) {
                    drawRect(
                        color = Color(0xFF0A0A1A),
                        topLeft = Offset((x + quiet) * modWidth, (y + quiet) * modWidth),
                        size = Size(modWidth, modWidth)
                    )
                }
            }
        }
    }
}
