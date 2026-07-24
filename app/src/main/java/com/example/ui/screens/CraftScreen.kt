package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CardEntity
import com.example.ui.components.CardArtGraphic
import com.example.ui.components.RarityBadge
import com.example.ui.components.getRarityColor
import com.example.ui.theme.*

@Composable
fun CraftScreen(
    cards: List<CardEntity>,
    selectedCards: List<CardEntity>,
    onToggleSelectCard: (CardEntity) -> Unit,
    onCraft: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isValid = selectedCards.size == 3 &&
            selectedCards.all { it.type == selectedCards[0].type } &&
            selectedCards.all { it.rarity == selectedCards[0].rarity }

    val rarities = listOf("common", "uncommon", "rare", "epic", "legendary", "mythic")
    val nextRarity = if (isValid) {
        val idx = rarities.indexOf(selectedCards[0].rarity)
        if (idx != -1 && idx < rarities.size - 1) rarities[idx + 1] else null
    } else null

    val availableCards = cards.filter { card ->
        selectedCards.none { it.id == card.id }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "АЛТАРЬ СОЗДАНИЯ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Объедини 3 карты одного типа и редкости для синтеза новой",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Altar Slots Box
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ИНГРЕДИЕНТЫ (${selectedCards.size}/3)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0 until 3) {
                        val card = selectedCards.getOrNull(i)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(
                                1.dp,
                                if (card != null) getRarityColor(card.rarity) else MaterialTheme.colorScheme.outline
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(110.dp)
                                .clickable { card?.let { onToggleSelectCard(it) } }
                        ) {
                            if (card != null) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CardArtGraphic(
                                        artKey = card.art,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Text(
                                        text = card.name,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight,
                                        maxLines = 1
                                    )
                                }
                            } else {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("+", fontSize = 24.sp, color = MaterialTheme.colorScheme.outline)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isValid && nextRarity != null) {
                    Text(
                        text = "Результат синтеза: $nextRarity".uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = getRarityColor(nextRarity)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onCraft,
                    enabled = isValid && nextRarity != null,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("✦ Создать карту", fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "ДОСТУПНЫЕ КАРТЫ ДЛЯ СИНТЕЗА",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(availableCards) { card ->
                val isSelected = selectedCards.any { it.id == card.id }
                val rarityColor = getRarityColor(card.rarity)

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, rarityColor),
                    modifier = Modifier
                        .aspectRatio(0.7f)
                        .clickable { onToggleSelectCard(card) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RarityBadge(rarity = card.rarity)
                        Spacer(modifier = Modifier.height(4.dp))
                        CardArtGraphic(
                            artKey = card.art,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = card.name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
