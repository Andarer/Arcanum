package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CardEntity
import com.example.ui.components.CardArtGraphic
import com.example.ui.components.RarityBadge
import com.example.ui.components.getRarityColor
import com.example.ui.theme.*

/**
 * CardCollectionScreen displays a grid of collectible card items with card images,
 * names, stats, and rarity indicators using LazyVerticalGrid.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCollectionScreen(
    cards: List<CardEntity>,
    onCardClick: (CardEntity) -> Unit = {},
    onUpgradeCard: (CardEntity) -> Unit = {},
    onTranscendCard: (CardEntity) -> Unit = {},
    onAddToDeck: (CardEntity) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("all") }
    var selectedRarity by remember { mutableStateOf("all") }
    var selectedCardForDetail by remember { mutableStateOf<CardEntity?>(null) }

    val filteredCards = remember(cards, searchQuery, selectedCategory, selectedRarity) {
        cards.filter { card ->
            val matchesSearch = card.name.contains(searchQuery, ignoreCase = true) ||
                    card.desc.contains(searchQuery, ignoreCase = true) ||
                    card.type.contains(searchQuery, ignoreCase = true)

            val matchesCategory = when (selectedCategory) {
                "hero" -> card.type.equals("hero", ignoreCase = true)
                "creature" -> card.type.equals("creature", ignoreCase = true)
                "item" -> card.type.equals("item", ignoreCase = true)
                else -> true
            }

            val matchesRarity = when (selectedRarity) {
                "common" -> card.rarity.equals("common", ignoreCase = true)
                "rare" -> card.rarity.equals("rare", ignoreCase = true) || card.rarity.equals("uncommon", ignoreCase = true)
                "epic" -> card.rarity.equals("epic", ignoreCase = true)
                "legendary" -> card.rarity.equals("legendary", ignoreCase = true) || card.rarity.equals("mythic", ignoreCase = true)
                else -> true
            }

            matchesSearch && matchesCategory && matchesRarity
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("card_collection_screen")
    ) {
        // Title Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "КОЛЛЕКЦИЯ КАРТ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = GoldLight,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Собрано: ${filteredCards.size} из ${cards.size}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Ранг",
                        tint = GoldAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${cards.count { it.rarity == "legendary" || it.rarity == "mythic" }} Легендарных",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Input Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Поиск карт...", fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск",
                    tint = GoldAccent
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить",
                            tint = Color.Gray
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldAccent,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("card_search_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category & Rarity Filter Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedCategory == "all",
                onClick = { selectedCategory = "all" },
                label = { Text("Все типы", fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GoldAccent.copy(alpha = 0.2f),
                    selectedLabelColor = GoldLight
                )
            )
            FilterChip(
                selected = selectedCategory == "hero",
                onClick = { selectedCategory = "hero" },
                label = { Text("Герои", fontSize = 11.sp) }
            )
            FilterChip(
                selected = selectedCategory == "creature",
                onClick = { selectedCategory = "creature" },
                label = { Text("Существа", fontSize = 11.sp) }
            )
            FilterChip(
                selected = selectedCategory == "item",
                onClick = { selectedCategory = "item" },
                label = { Text("Предметы", fontSize = 11.sp) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Cards Grid Layout
        if (filteredCards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🎴 Карты не найдены",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Попробуйте изменить запрос поиска или фильтр",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .testTag("card_collection_grid")
            ) {
                items(filteredCards, key = { it.id }) { card ->
                    CollectibleCardGridItem(
                        card = card,
                        onClick = {
                            onCardClick(card)
                            selectedCardForDetail = card
                        }
                    )
                }
            }
        }
    }

    // Modal Card Detail Dialog
    selectedCardForDetail?.let { card ->
        CollectibleCardDetailModal(
            card = card,
            onDismiss = { selectedCardForDetail = null },
            onUpgrade = {
                onUpgradeCard(card)
                selectedCardForDetail = null
            },
            onTranscend = {
                onTranscendCard(card)
                selectedCardForDetail = null
            },
            onAddToDeck = {
                onAddToDeck(card)
                selectedCardForDetail = null
            }
        )
    }
}

/**
 * Grid Item representing a single collectible card with artwork, stats, and rarity indicator.
 */
@Composable
fun CollectibleCardGridItem(
    card: CardEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rarityColor = getRarityColor(card.rarity)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.5.dp, rarityColor),
        shadowElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.72f)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("collectible_card_item_${card.id}")
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Info Bar: Type & Rarity Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color.Black.copy(alpha = 0.4f),
                        border = BorderStroke(0.5.dp, rarityColor.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = card.type.uppercase(),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    RarityBadge(rarity = card.rarity)
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Card Graphic Artwork
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, rarityColor.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                ) {
                    CardArtGraphic(
                        artKey = card.art,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Level Badge Overlay
                    if (card.level > 1) {
                        Surface(
                            shape = CircleShape,
                            color = GoldAccent,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(20.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "L${card.level}",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Card Title
                Text(
                    text = card.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Card Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (card.hp > 0) StatBadge(label = "❤", value = card.hp.toString(), color = RedDanger)
                    if (card.str > 0) StatBadge(label = "⚔", value = card.str.toString(), color = GoldAccent)
                    if (card.def > 0) StatBadge(label = "🛡", value = card.def.toString(), color = PurpleAccent)
                    if (card.abilityCost > 0) StatBadge(label = "💧", value = card.abilityCost.toString(), color = Color(0xFF4A90E2))
                }
            }
        }
    }
}

@Composable
fun StatBadge(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = Color.Black.copy(alpha = 0.5f),
        border = BorderStroke(0.5.dp, color.copy(alpha = 0.6f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = label, fontSize = 8.sp)
            Text(text = value, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

/**
 * Detail Modal for inspecting a card, upgrading its level, or adding it to the active battle deck.
 */
@Composable
fun CollectibleCardDetailModal(
    card: CardEntity,
    onDismiss: () -> Unit,
    onUpgrade: () -> Unit,
    onTranscend: () -> Unit = {},
    onAddToDeck: () -> Unit
) {
    val rarityColor = getRarityColor(card.rarity)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = BgDark1,
            border = BorderStroke(2.dp, rarityColor),
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .testTag("collectible_card_detail_modal")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RarityBadge(rarity = card.rarity)
                    Text(
                        text = "Уровень ${card.level}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Card Art
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.5.dp, rarityColor, RoundedCornerShape(14.dp))
                ) {
                    CardArtGraphic(
                        artKey = card.art,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Name & Type
                Text(
                    text = card.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = GoldLight,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = card.desc,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Stats Table
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.4f),
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("❤ Здоровье", fontSize = 9.sp, color = Color.Gray)
                            Text("${card.hp}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RedDanger)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("⚔ Атака", fontSize = 9.sp, color = Color.Gray)
                            Text("${card.str}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🛡 Защита", fontSize = 9.sp, color = Color.Gray)
                            Text("${card.def}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PurpleAccent)
                        }
                    }
                }

                if (card.abilityName != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = PurpleAccent.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, PurpleAccent.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "✨ Способность: ${card.abilityName} (${card.abilityCost} MP)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                            if (card.abilityDesc != null) {
                                Text(
                                    text = card.abilityDesc,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onUpgrade,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("⬆ Уровень (${(card.level + 1) * 50}◉)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    if (!card.rarity.equals("mythic", ignoreCase = true)) {
                        Button(
                            onClick = onTranscend,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("✨ Эволюция (${card.level * 300}◉)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Buttons Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        border = BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Закрыть", fontSize = 11.sp, color = Color.White)
                    }

                    Button(
                        onClick = onAddToDeck,
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("⚔ В колоду", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
