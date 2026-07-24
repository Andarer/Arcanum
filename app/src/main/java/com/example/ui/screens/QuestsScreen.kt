package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlayerStatsEntity
import com.example.data.QuestEntity
import com.example.ui.theme.*

@Composable
fun QuestsScreen(
    quests: List<QuestEntity>,
    playerStats: PlayerStatsEntity,
    totalCardsCount: Int,
    deckSizeCount: Int,
    onClaimQuest: (QuestEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = quests.count { it.isCompleted }

    val visitedCount = playerStats.visitedLocations.split(",").filter { it.isNotBlank() }.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "КВЕСТЫ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "$completedCount / ${quests.size} выполнено",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(quests, key = { it.id }) { quest ->
                val currentStatVal = when (quest.statKey) {
                    "battlesWon" -> playerStats.battlesWon
                    "totalCards" -> totalCardsCount
                    "crafted" -> playerStats.crafted
                    "gold" -> playerStats.gold
                    "level" -> playerStats.level
                    "visitedLocations" -> visitedCount
                    "chestsOpened" -> playerStats.chestsOpened
                    "deckSize" -> deckSizeCount
                    else -> 0
                }

                val current = minOf(currentStatVal, quest.target)
                val pct = if (quest.target > 0) current.toFloat() / quest.target else 0f
                val canClaim = !quest.isCompleted && current >= quest.target

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (quest.isCompleted) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(
                        1.dp,
                        if (canClaim) GoldAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (quest.isCompleted) GreenSuccess else GoldAccent,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (quest.isCompleted) Icons.Default.CheckCircle else Icons.Default.Assignment,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = quest.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                            Text(
                                text = quest.desc,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            LinearProgressIndicator(
                                progress = { pct.coerceIn(0f, 1f) },
                                color = GoldAccent,
                                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "$current / ${quest.target}",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = buildString {
                                        if (quest.xpReward > 0) append("✦ ${quest.xpReward} XP ")
                                        if (quest.goldReward > 0) append("◉ ${quest.goldReward}")
                                    },
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PurpleAccent
                                )
                            }
                        }

                        if (canClaim) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onClaimQuest(quest) },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                            ) {
                                Text("Забрать", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
