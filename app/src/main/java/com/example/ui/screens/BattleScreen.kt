package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.CardEntity
import com.example.data.InventoryEntity
import com.example.ui.BattleState
import com.example.ui.FloatingDamage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.components.CardArtGraphic
import com.example.ui.theme.*

@Composable
fun BattleScreen(
    battleState: BattleState?,
    deck: List<CardEntity>,
    inventory: List<InventoryEntity>,
    floatingDamages: List<FloatingDamage>,
    onAction: (String) -> Unit,
    onUseDeckCard: (Int) -> Unit,
    onEndTurn: () -> Unit,
    onResetBattle: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (battleState == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GoldAccent)
        }
        return
    }

    val listState = rememberLazyListState()

    // Smooth scroll to latest combat log entry
    LaunchedEffect(battleState.log.size) {
        if (battleState.log.isNotEmpty()) {
            listState.animateScrollToItem(battleState.log.size - 1)
        }
    }

    val potionCount = inventory.find { it.id == "inv-potion" }?.count ?: 0
    var selectedCardIndex by remember { mutableStateOf<Int?>(null) }

    // Show Victory or Defeat Dialog when battle is finished or player health is 0
    if (battleState.isFinished || battleState.playerHp <= 0 || battleState.enemyHp <= 0) {
        BattleResultDialog(
            isWon = battleState.playerWon || (battleState.enemyHp <= 0 && battleState.playerHp > 0),
            enemyName = battleState.enemy.name,
            goldReward = battleState.enemy.goldReward,
            xpReward = battleState.enemy.xpReward,
            onRestart = onResetBattle,
            onHome = onNavigateToHome
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Title & Turn Indicator Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "АРЕНА БОЯ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Противник: ${battleState.enemy.name}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Turn Status Pill
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (battleState.isPlayerTurn) GreenSuccess.copy(alpha = 0.2f) else RedDanger.copy(alpha = 0.2f),
                border = BorderStroke(
                    1.dp,
                    if (battleState.isPlayerTurn) GreenSuccess else RedDanger
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(if (battleState.isPlayerTurn) GreenSuccess else RedDanger)
                    )
                    Text(
                        text = if (battleState.isPlayerTurn) "ВАШ ХОД (${battleState.turnNumber})" else "ХОД ВРАГА...",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (battleState.isPlayerTurn) GreenSuccess else RedDanger
                    )
                }
            }

            Button(
                onClick = onResetBattle,
                colors = ButtonDefaults.buttonColors(containerColor = RedDanger),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("↺ Сброс", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Main Battlefield Arena Card
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Combatants Arena Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Player Hero Box
                        CombatantBox(
                            name = "ГЕРОЙ",
                            artKey = "warrior",
                            hp = battleState.playerHp,
                            maxHp = battleState.playerHpMax,
                            mp = battleState.playerMp,
                            maxMp = battleState.playerMpMax,
                            isPlayer = true,
                            isDefending = battleState.isDefending,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "VS",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = GoldAccent,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )

                        // Enemy Monster Box
                        CombatantBox(
                            name = battleState.enemy.name,
                            artKey = battleState.enemy.art,
                            hp = battleState.enemyHp,
                            maxHp = battleState.enemyHpMax,
                            mp = 0,
                            maxMp = 0,
                            isPlayer = false,
                            isDefending = false,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Cards Hand Section with Animations
                    Text(
                        text = "КАРТЫ В РУКЕ (Нажмите для использования)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (deck.isEmpty()) {
                        Text(
                            text = "Колода пуста",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(deck) { index, card ->
                                val isUsed = battleState.usedDeckIndices.contains(index)
                                val canUse = !isUsed && battleState.playerMp >= card.abilityCost && battleState.isPlayerTurn && !battleState.isFinished
                                val isSelected = selectedCardIndex == index

                                val scaleState by animateFloatAsState(
                                    targetValue = if (isSelected) 1.08f else 1f,
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                                    label = "card_scale"
                                )

                                val translateYState by animateFloatAsState(
                                    targetValue = if (isSelected) -10f else 0f,
                                    animationSpec = tween(200),
                                    label = "card_translate"
                                )

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isUsed) Color.Gray.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        if (isSelected) 2.dp else 1.dp,
                                        if (canUse) GoldAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                    ),
                                    modifier = Modifier
                                        .width(76.dp)
                                        .height(100.dp)
                                        .graphicsLayer {
                                            scaleX = scaleState
                                            scaleY = scaleState
                                            translationY = translateYState
                                        }
                                        .clickable(enabled = canUse) {
                                            selectedCardIndex = index
                                            onUseDeckCard(index)
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        CardArtGraphic(
                                            artKey = card.art,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                        Text(
                                            text = card.abilityName ?: card.name,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            color = if (canUse) GoldLight else Color.Gray
                                        )
                                        Text(
                                            text = "${card.abilityCost} MP",
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PurpleAccent
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Scrollable Combat Log Section
                    Text(
                        text = "📜 ЖУРНАЛ БОЯ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Black.copy(alpha = 0.6f),
                        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    ) {
                        LazyColumn(
                            state = listState,
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(battleState.log, key = { it.id }) { log ->
                                val logColor = when (log.type) {
                                    "damage" -> RedDanger
                                    "heal" -> GreenSuccess
                                    "crit" -> YellowWarning
                                    "miss" -> Color.Gray
                                    "card" -> PurpleAccent
                                    else -> GoldAccent
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .clip(CircleShape)
                                            .background(logColor)
                                    )
                                    Text(
                                        text = log.text,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = logColor
                                    )
                                }
                            }
                        }
                    }
                }

                // Played Card Central Animation Overlay
                battleState.playedCardAnimation?.let { playedCard ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = BgDark1,
                            border = BorderStroke(2.dp, GoldAccent),
                            shadowElevation = 8.dp,
                            modifier = Modifier
                                .width(120.dp)
                                .height(160.dp)
                                .scale(1.2f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                CardArtGraphic(
                                    artKey = playedCard.art,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(90.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Text(
                                    text = playedCard.name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = playedCard.abilityName ?: "Заклинание",
                                    fontSize = 10.sp,
                                    color = PurpleAccent,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                // Floating Damage Numbers Animation Overlays
                floatingDamages.forEach { fd ->
                    Box(
                        modifier = Modifier
                            .align(if (fd.isPlayerTarget) Alignment.CenterStart else Alignment.CenterEnd)
                            .padding(horizontal = 28.dp)
                    ) {
                        Text(
                            text = fd.text,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = when (fd.colorHex) {
                                "#6FD49A" -> GreenSuccess
                                "#E0A84A" -> YellowWarning
                                "#8B6FD4" -> PurpleAccent
                                else -> RedDanger
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Battle Control Buttons Layout
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onAction("attack") },
                    enabled = battleState.isPlayerTurn && !battleState.isFinished,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("⚔ Атака", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onAction("heavy") },
                    enabled = battleState.isPlayerTurn && !battleState.isFinished,
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("⚡ Тяжелый удар", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onAction("defend") },
                    enabled = battleState.isPlayerTurn && !battleState.isFinished,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, GoldAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🛡 Защита", fontSize = 12.sp, color = GoldLight, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onAction("potion") },
                    enabled = battleState.isPlayerTurn && !battleState.isFinished && potionCount > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenSuccess),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("✚ Зелье ($potionCount)", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            // Prominent "End Turn" (Завершить ход) Button
            Button(
                onClick = onEndTurn,
                enabled = battleState.isPlayerTurn && !battleState.isFinished,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E2638),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.2f)
                ),
                border = BorderStroke(
                    1.5.dp,
                    if (battleState.isPlayerTurn) GoldAccent else Color.Gray.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "⌛ ЗАВЕРШИТЬ ХОД",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (battleState.isPlayerTurn) GoldLight else Color.Gray,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CombatantBox(
    name: String,
    artKey: String,
    hp: Int,
    maxHp: Int,
    mp: Int,
    maxMp: Int,
    isPlayer: Boolean,
    isDefending: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            CardArtGraphic(
                artKey = artKey,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(
                        2.5.dp,
                        if (isDefending) GoldAccent else if (isPlayer) GreenSuccess else RedDanger,
                        CircleShape
                    )
            )

            if (isDefending) {
                Surface(
                    shape = CircleShape,
                    color = GoldAccent,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🛡", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        // HP Bar
        val hpPct = if (maxHp > 0) hp.toFloat() / maxHp else 0f
        LinearProgressIndicator(
            progress = { hpPct.coerceIn(0f, 1f) },
            color = if (isPlayer) GreenSuccess else RedDanger,
            trackColor = RedDanger.copy(alpha = 0.2f),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = "$hp / $maxHp HP",
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        if (isPlayer && maxMp > 0) {
            Spacer(modifier = Modifier.height(2.dp))
            val mpPct = mp.toFloat() / maxMp
            LinearProgressIndicator(
                progress = { mpPct.coerceIn(0f, 1f) },
                color = PurpleAccent,
                trackColor = PurpleAccent.copy(alpha = 0.2f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = "$mp / $maxMp MP",
                fontSize = 8.sp,
                fontFamily = FontFamily.Monospace,
                color = PurpleAccent
            )
        }
    }
}

@Composable
fun BattleResultDialog(
    isWon: Boolean,
    enemyName: String,
    goldReward: Int,
    xpReward: Int,
    onRestart: () -> Unit,
    onHome: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = BgDark1,
            border = BorderStroke(2.dp, if (isWon) GoldAccent else RedDanger),
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth(0.92f)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isWon) "🏆 ПОБЕДА!" else "💀 ПОРАЖЕНИЕ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isWon) GoldLight else RedDanger,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isWon)
                        "Вы одолели $enemyName и отстояли честь Арены!"
                    else
                        "Здоровье героя достигло 0. $enemyName одержал победу.",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isWon) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Black.copy(alpha = 0.4f),
                        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "НАГРАДЫ ЗА БОЙ",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent,
                                letterSpacing = 1.sp
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "◉ +$goldReward Золота",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldAccent
                                )
                                Text(
                                    text = "⚡ +$xpReward Опыта",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PurpleAccent
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "💡 Совет: Соберите более сильную колоду в Построителе Колоды и используйте зелья!",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onHome,
                        border = BorderStroke(1.dp, GoldAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("🏠 Главное меню", fontSize = 11.sp, color = GoldLight)
                    }

                    Button(
                        onClick = onRestart,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isWon) GoldAccent else RedDanger
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = if (isWon) "↺ Новый бой" else "↺ Повторить",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isWon) Color.Black else Color.White
                        )
                    }
                }
            }
        }
    }
}
