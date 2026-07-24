package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CardEntity
import com.example.ui.components.CardArtGraphic
import com.example.ui.components.CustomQrCodeCanvas
import com.example.ui.components.RarityBadge
import com.example.ui.components.getRarityColor
import com.example.ui.theme.*

@Composable
fun CollectionScreen(
    cards: List<CardEntity>,
    onUpgradeCard: (CardEntity) -> Unit,
    onAddToDeck: (CardEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    CardCollectionScreen(
        cards = cards,
        onUpgradeCard = onUpgradeCard,
        onAddToDeck = onAddToDeck,
        modifier = modifier
    )
}

@Composable
fun CardItem(card: CardEntity, onClick: () -> Unit) {
    val rarityColor = getRarityColor(card.rarity)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.5.dp, rarityColor.copy(alpha = 0.8f)),
        shadowElevation = 4.dp,
        modifier = Modifier
            .aspectRatio(0.7f)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.type.uppercase(),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                RarityBadge(rarity = card.rarity)
            }

            Spacer(modifier = Modifier.height(6.dp))

            CardArtGraphic(
                artKey = card.art,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = card.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = GoldLight,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (card.hp > 0) StatChip("❤ ${card.hp}")
                if (card.str > 0) StatChip("⚔ ${card.str}")
                if (card.def > 0) StatChip("🛡 ${card.def}")
            }
        }
    }
}

@Composable
fun StatChip(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun CardDetailDialog(
    card: CardEntity,
    onDismiss: () -> Unit,
    onUpgrade: () -> Unit,
    onAddToDeck: () -> Unit
) {
    val upgradeCost = (card.level + 1) * 50

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.5.dp, GoldAccent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = card.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RarityBadge(rarity = card.rarity)
                    Text(
                        text = "УРОВЕНЬ ${card.level}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurpleAccent
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                CardArtGraphic(
                    artKey = card.art,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = card.desc,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Stats Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("HP", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("${card.hp}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RedDanger)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("MP", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("${card.mp}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PurpleAccent)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Сила", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("${card.str}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Защита", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("${card.def}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CyanAccent)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Custom QR Code Preview
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomQrCodeCanvas(
                        data = "ARCANUM:${card.id}|HP:${card.hp}|STR:${card.str}",
                        modifier = Modifier.size(70.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Синхронизация ID", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                        Text(card.id, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onUpgrade,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("⬆ Улучшить ($upgradeCost ◉)", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onAddToDeck,
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("+ В колоду", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
