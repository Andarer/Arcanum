package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlayerStatsEntity
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun PvpScreen(
    playerStats: PlayerStatsEntity,
    onAddGold: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var pvpRating by remember { mutableStateOf(1200) }
    var isSearching by remember { mutableStateOf(false) }
    var inBattle by remember { mutableStateOf(false) }
    var opponentName by remember { mutableStateOf("") }
    var opponentHp by remember { mutableStateOf(120) }
    var opponentHpMax by remember { mutableStateOf(120) }
    var battleLog by remember { mutableStateOf("Ожидание поиска поединка...") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ONLINE PVP АРЕНА",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BgDark2),
            border = BorderStroke(1.5.dp, GoldAccent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ваш PVP Рейтинг: $pvpRating MMR",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )
                Text(
                    text = "Лигла: Золотая | Сезон 1",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!inBattle) {
                    Button(
                        onClick = {
                            isSearching = true
                            battleLog = "Поиск соперника на сервере..."
                            coroutineScope.launch {
                                delay(1500)
                                val opponents = listOf("ShadowBlade", "ArcaneMaster", "DragonSlayer", "Vadim_Knight")
                                opponentName = opponents.random()
                                opponentHpMax = 120 + Random.nextInt(40)
                                opponentHp = opponentHpMax
                                isSearching = false
                                inBattle = true
                                battleLog = "Соперник $opponentName найден! Ваш ход!"
                            }
                        },
                        enabled = !isSearching,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isSearching) "Поиск..." else "Найти Поединок",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (inBattle) {
            AnimatedVisibility(visible = inBattle, enter = fadeIn()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BgDark1),
                    border = BorderStroke(1.dp, RedDanger),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = opponentName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = RedDanger
                                )
                                Text(
                                    text = "HP: $opponentHp / $opponentHpMax",
                                    fontSize = 13.sp,
                                    color = GoldLight
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = RedDanger,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        LinearProgressIndicator(
                            progress = { opponentHp.toFloat() / opponentHpMax.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = RedDanger,
                            trackColor = Color.DarkGray
                        )

                        Text(
                            text = battleLog,
                            fontSize = 12.sp,
                            color = GoldAccent
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    val dmg = Random.nextInt(12, 25)
                                    opponentHp = (opponentHp - dmg).coerceAtLeast(0)
                                    if (opponentHp == 0) {
                                        pvpRating += 25
                                        onAddGold(80)
                                        inBattle = false
                                        battleLog = "Победа в PVP! +25 MMR, +80 Золота!"
                                    } else {
                                        val enemyDmg = Random.nextInt(10, 20)
                                        battleLog = "Вы нанесли $dmg урона! $opponentName ответил на $enemyDmg!"
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = RedDanger),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("⚔ Атака", color = Color.White, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    val dmg = 28
                                    opponentHp = (opponentHp - dmg).coerceAtLeast(0)
                                    if (opponentHp == 0) {
                                        pvpRating += 25
                                        onAddGold(80)
                                        inBattle = false
                                        battleLog = "Победа Суперударом! +25 MMR, +80 Золота!"
                                    } else {
                                        val enemyDmg = Random.nextInt(8, 16)
                                        battleLog = "Магический урон $dmg! $opponentName ответил на $enemyDmg!"
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.FlashOn, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Магия", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
