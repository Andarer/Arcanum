package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.CardArtGraphic
import com.example.ui.theme.*

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChestTierModel(
    val id: String,
    val name: String,
    val price: Int,
    val color: Color
)

@Composable
fun ChestScreen(
    gold: Int,
    onOpenChest: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedChestTier by remember { mutableStateOf("common") }
    var isOpening by remember { mutableStateOf(false) }

    val chestTiers = listOf(
        ChestTierModel("common", "Обычный сундук", 50, RareUncommon),
        ChestTierModel("rare", "Редкий сундук", 150, RareRare),
        ChestTierModel("legendary", "Легендарный сундук", 400, RareLegendary)
    )

    val currentTier = chestTiers.find { it.id == selectedChestTier } ?: chestTiers[0]
    val canOpen = gold >= currentTier.price && !isOpening

    val scale by animateFloatAsState(
        targetValue = if (isOpening) 1.15f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "chestScale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "СУНДУКИ СОКРОВИЩ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Баланс: ◉ $gold",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Big Chest Graphic Showcase
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(2.dp, currentTier.color),
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
                .clickable(enabled = canOpen) {
                    isOpening = true
                    onOpenChest(currentTier.id, currentTier.price)
                    // Reset animation state after short delay
                    coroutineScope.launch {
                        delay(600)
                        isOpening = false
                    }
                }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_magic_chest),
                    contentDescription = "Magic Chest Art",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                )
                Surface(
                    color = Color.Black.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = currentTier.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = currentTier.color
                        )
                        Text(
                            text = "Нажми чтобы открыть",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isOpening = true
                onOpenChest(currentTier.id, currentTier.price)
            },
            enabled = canOpen,
            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Открыть сундук (◉ ${currentTier.price})",
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ВЫБЕРИ ТИП СУНДУКА",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            chestTiers.forEach { tier ->
                val selected = selectedChestTier == tier.id
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (selected) tier.color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.5.dp, if (selected) tier.color else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedChestTier = tier.id }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = tier.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = tier.color
                            )
                            Text(
                                text = when (tier.id) {
                                    "common" -> "Шанс: Обычные / Необычные карты"
                                    "rare" -> "Шанс: Редкие / Эпические карты"
                                    else -> "Шанс: Легендарные / Мифические карты!"
                                },
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }

                        Text(
                            text = "◉ ${tier.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )
                    }
                }
            }
        }
    }
}
