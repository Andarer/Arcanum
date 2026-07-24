package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
fun DeckScreen(
    deck: List<CardEntity>,
    allCards: List<CardEntity>,
    onAddToDeck: (CardEntity) -> Unit,
    onRemoveFromDeck: (Int) -> Unit,
    onAutoDeck: () -> Unit,
    onClearDeck: () -> Unit,
    modifier: Modifier = Modifier
) {
    val availableCards = allCards.filter { card ->
        deck.none { it.id == card.id } && card.abilityName != null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "КОЛОДА ДЛЯ БОЯ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Собери 5 карт для использования их способностей в бою (${deck.size}/5)",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Active Deck Slots (5 slots)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until 5) {
                val card = deck.getOrNull(i)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.65f)
                ) {
                    if (card != null) {
                        val rarityColor = getRarityColor(card.rarity)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(1.5.dp, rarityColor),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CardArtGraphic(
                                        artKey = card.art,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = card.name,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "${card.abilityCost} MP",
                                        fontSize = 8.sp,
                                        color = PurpleAccent,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                IconButton(
                                    onClick = { onRemoveFromDeck(i) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(20.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = RedDanger,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Empty Slot",
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Deck Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onAutoDeck,
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                modifier = Modifier.weight(1f)
            ) {
                Text("⟳ Авто-сбор", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = onClearDeck,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = RedDanger),
                modifier = Modifier.weight(1f)
            ) {
                Text("✕ Очистить", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "ДОСТУПНЫЕ КАРТЫ С НАВЫКАМИ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (availableCards.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Все доступные карты с навыками уже в колоде",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(availableCards) { card ->
                    val rarityColor = getRarityColor(card.rarity)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, rarityColor),
                        modifier = Modifier
                            .aspectRatio(0.7f)
                            .clickable { onAddToDeck(card) }
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
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight,
                                maxLines = 1
                            )
                            Text(
                                text = "${card.abilityName} (${card.abilityCost} MP)",
                                fontSize = 9.sp,
                                color = PurpleAccent,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
