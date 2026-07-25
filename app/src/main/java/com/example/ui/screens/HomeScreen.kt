package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.PlayerStatsEntity
import com.example.data.QuestEntity
import com.example.ui.theme.*

data class MenuItemModel(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)

@Composable
fun HomeScreen(
    playerStats: PlayerStatsEntity,
    quests: List<QuestEntity>,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Calculate claimable quests count
    val claimableQuestsCount = quests.count { q ->
        if (q.isCompleted) false
        else {
            val statVal = when (q.statKey) {
                "battlesWon" -> playerStats.battlesWon
                "gold" -> playerStats.gold
                "level" -> playerStats.level
                "chestsOpened" -> playerStats.chestsOpened
                "crafted" -> playerStats.crafted
                else -> 0
            }
            statVal >= q.target
        }
    }

    val menuItems = listOf(
        MenuItemModel("battle", "Арена", Icons.Default.FlashOn),
        MenuItemModel("pvp", "PVP Арена", Icons.Default.Shield),
        MenuItemModel("clicker", "Кликер", Icons.Default.TouchApp),
        MenuItemModel("shooter", "Тир", Icons.Default.AdsClick),
        MenuItemModel("onboarding", "Обучение", Icons.Default.AutoAwesome),
        MenuItemModel("collection", "Коллекция", Icons.Default.Style),
        MenuItemModel("deck", "Колода", Icons.Default.ViewCarousel),
        MenuItemModel("shop", "Магазин", Icons.Default.Storefront),
        MenuItemModel("quests", "Квесты", Icons.Default.Assignment, badgeCount = claimableQuestsCount),
        MenuItemModel("craft", "Алхимия", Icons.Default.AutoFixHigh),
        MenuItemModel("chest", "Сокровищница", Icons.Default.CardGiftcard),
        MenuItemModel("world", "Карта Миров", Icons.Default.Explore),
        MenuItemModel("inventory", "Инвентарь", Icons.Default.Backpack),
        MenuItemModel("editor", "Студия OS", Icons.Default.Edit),
        MenuItemModel("achievements", "Зал Славы", Icons.Default.EmojiEvents),
        MenuItemModel("diary", "Дневник", Icons.Default.MenuBook),
        MenuItemModel("print", "Типография", Icons.Default.Print),
        MenuItemModel("qr", "Портал QR", Icons.Default.QrCode),
        MenuItemModel("settings", "Мастерская", Icons.Default.Settings)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Banner Art
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BgDark2),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val heroPainter = runCatching { painterResource(id = R.drawable.img_arcanum_hero) }.getOrNull()
                if (heroPainter != null) {
                    Image(
                        painter = heroPainter,
                        contentDescription = "Arcanum Hero Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    com.example.ui.components.CardArtGraphic(
                        artKey = "dragon",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = Color(0xCC111218),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.6f)),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "⚡ РЕЙД БОСС: МИФИЧЕСКИЙ ДРАКОН БЕЗДНЫ ⚡",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        text = "ARCANUM",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        letterSpacing = 4.sp
                    )
                    Text(
                        text = "HYBRID CARD MMORPG & PWA ENGINE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = GoldAccent,
                        letterSpacing = 2.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Stats Bar
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Уровень", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    Text("⚔ ${playerStats.level}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                }
                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp),
                    color = GoldAccent.copy(alpha = 0.3f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Опыт", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    Text("✦ ${playerStats.xp}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PurpleAccent)
                }
                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp),
                    color = GoldAccent.copy(alpha = 0.3f)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Золото", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    Text("◉ ${playerStats.gold}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Featured Game Carousel Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ИГРОВЫЕ СОБЫТИЯ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = GoldAccent,
                letterSpacing = 2.sp
            )
            Text(
                text = "Листайте ➔",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Horizontal Interactive Game Carousel
        androidx.compose.foundation.lazy.LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                CarouselCardItem(
                    title = "🔥 БОСС: ВУЛКАН",
                    subtitle = "Победите огненного демона",
                    badge = "СОБЫТИЕ",
                    badgeColor = RedDanger,
                    icon = "🐉",
                    onClick = { onNavigate("battle") }
                )
            }
            item {
                CarouselCardItem(
                    title = "✨ ЛЕГЕНДАРНЫЙ СУНДУК",
                    subtitle = "Шанс 100% на Мифическую карту",
                    badge = "АКЦИЯ",
                    badgeColor = GoldAccent,
                    icon = "🎁",
                    onClick = { onNavigate("chest") }
                )
            }
            item {
                CarouselCardItem(
                    title = "🔨 РЕДКИЙ КРАФТ",
                    subtitle = "Выкуйте 'Меч Дракона'",
                    badge = "КУЗНИЦА",
                    badgeColor = PurpleAccent,
                    icon = "🗡️",
                    onClick = { onNavigate("craft") }
                )
            }
            item {
                CarouselCardItem(
                    title = "🗺️ ПОДЗЕМЕЛЬЕ ТЕНЕЙ",
                    subtitle = "Новая локация на карте",
                    badge = "ИССЛЕДОВАНИЕ",
                    badgeColor = Color(0xFF2980B9),
                    icon = "🏰",
                    onClick = { onNavigate("world") }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ГЛАВНОЕ МЕНЮ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent,
            letterSpacing = 2.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, bottom = 12.dp)
        )

        // Menu Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 800.dp),
            userScrollEnabled = false
        ) {
            items(menuItems) { item ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.25f)),
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable { onNavigate(item.id) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = GoldAccent,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (item.badgeCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(RedDanger),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.badgeCount.toString(),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarouselCardItem(
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = BgDark2,
        border = BorderStroke(1.5.dp, GoldAccent.copy(alpha = 0.5f)),
        shadowElevation = 6.dp,
        modifier = modifier
            .width(220.dp)
            .height(115.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = badgeColor,
                        modifier = Modifier.padding(bottom = 2.dp)
                    ) {
                        Text(
                            text = badge,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = icon,
                        fontSize = 22.sp
                    )
                }

                Column {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = GoldLight,
                        maxLines = 1
                    )
                    Text(
                        text = subtitle,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 1
                    )
                }
            }
        }
    }
}
