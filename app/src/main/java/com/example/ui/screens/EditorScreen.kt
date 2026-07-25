package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CardArtGraphic
import com.example.ui.components.RarityBadge
import com.example.ui.components.getRarityColor
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    activeRenderProfile: RenderProfile,
    onSetRenderProfile: (RenderProfile) -> Unit,
    onCreateCard: (
        name: String,
        type: String,
        rarity: String,
        hp: Int,
        mp: Int,
        str: Int,
        def: Int,
        desc: String,
        art: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("🎨 Рендерер", "⚔️ Кузница", "⚗️ Лаборатория", "📚 Архив")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Studio Header
        Text(
            text = "🏛️ БАШНЯ АРХИТЕКТОРА",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Arcanum Studio — Конструктор Вселенных & Визуальный Движок",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Studio Realm Navigation Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = GoldAccent
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> RenderProfileTab(
                    activeProfile = activeRenderProfile,
                    onSelectProfile = onSetRenderProfile
                )
                1 -> ForgeCardTab(onCreateCard = onCreateCard)
                2 -> LaboratoryTab()
                3 -> ArchiveDocsTab()
            }
        }
    }
}

@Composable
fun RenderProfileTab(
    activeProfile: RenderProfile,
    onSelectProfile: (RenderProfile) -> Unit
) {
    val scrollState = rememberScrollState()
    val profiles = RenderProfile.values()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "ВИЗУАЛЬНЫЕ RENDER-ПРОФИЛИ",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent
        )
        Text(
            text = "Переключай визуальный движок прямо в режиме реального времени. Логика ядра сохраняется, скин и шейдеры меняются.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        profiles.forEach { profile ->
            val isSelected = profile == activeProfile
            val spec = getRenderStyle(profile)

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = spec.surfaceColor,
                border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) spec.primaryColor else Color.Gray.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSelectProfile(profile) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(spec.primaryColor.copy(alpha = 0.2f))
                    ) {
                        Text(text = profile.icon, fontSize = 20.sp)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = spec.primaryColor
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    color = spec.primaryColor,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "АКТИВЕН",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = profile.description,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ForgeCardTab(
    onCreateCard: (
        name: String,
        type: String,
        rarity: String,
        hp: Int,
        mp: Int,
        str: Int,
        def: Int,
        desc: String,
        art: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("Странник") }
    var type by remember { mutableStateOf("hero") }
    var rarity by remember { mutableStateOf("rare") }
    var hpText by remember { mutableStateOf("80") }
    var mpText by remember { mutableStateOf("40") }
    var strText by remember { mutableStateOf("12") }
    var defText by remember { mutableStateOf("8") }
    var desc by remember { mutableStateOf("Загадочный путник, скитающийся между мирами.") }
    var art by remember { mutableStateOf("sword") }

    val hp = hpText.toIntOrNull() ?: 0
    val mp = mpText.toIntOrNull() ?: 0
    val str = strText.toIntOrNull() ?: 0
    val def = defText.toIntOrNull() ?: 0

    val scrollState = rememberScrollState()

    val artOptions = listOf("warrior", "archer", "mage", "necro", "dragon", "goblin", "sword", "shield", "potion", "ring", "crystal", "pet")
    val rarityOptions = listOf("common", "uncommon", "rare", "epic", "legendary", "mythic")
    val typeOptions = listOf("hero", "creature", "item")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Live Preview Card
        Text(
            text = "РАБОЧИЙ СТОЛ КУЗНИЦЫ (ПРЕДПРОСМОТР)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent
        )
        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.5.dp, getRarityColor(rarity)),
            modifier = Modifier
                .width(170.dp)
                .height(240.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = type.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    RarityBadge(rarity = rarity)
                }

                Spacer(modifier = Modifier.height(4.dp))

                CardArtGraphic(
                    artKey = art,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = name.ifBlank { "Без имени" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight,
                    maxLines = 1
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    if (hp > 0) StatChip("❤ $hp")
                    if (mp > 0) StatChip("✦ $mp")
                    if (str > 0) StatChip("⚔ $str")
                    if (def > 0) StatChip("🛡 $def")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Editor Form
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = hpText,
                onValueChange = { hpText = it },
                label = { Text("HP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = mpText,
                onValueChange = { mpText = it },
                label = { Text("MP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = strText,
                onValueChange = { strText = it },
                label = { Text("Сила") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = defText,
                onValueChange = { defText = it },
                label = { Text("Защита") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Art Selector Row
        Text("Арт карты", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        ScrollableTabRow(
            selectedTabIndex = artOptions.indexOf(art).coerceAtLeast(0),
            edgePadding = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            artOptions.forEach { artKey ->
                Tab(
                    selected = art == artKey,
                    onClick = { art = artKey },
                    text = { Text(artKey.uppercase(), fontSize = 10.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Rarity Selector Row
        Text("Редкость", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        ScrollableTabRow(
            selectedTabIndex = rarityOptions.indexOf(rarity).coerceAtLeast(0),
            edgePadding = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            rarityOptions.forEach { r ->
                Tab(
                    selected = rarity == r,
                    onClick = { rarity = r },
                    text = { Text(r.uppercase(), fontSize = 10.sp, color = getRarityColor(r)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onCreateCard(name, type, rarity, hp, mp, str, def, desc, art)
            },
            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("⚔ Ковать карту", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LaboratoryTab() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "АЛХИМИЧЕСКАЯ ЛАБОРАТОРИЯ МЕХАНИК",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent
        )
        Text(
            text = "Синтез новых правил игры, генерация Lore и искусственный интеллект Gemini.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabModuleCard(
            title = "⚗️ Синтезатор Способностей",
            status = "АКТИВЕН",
            description = "Комбинирует магические стихии для создания уникальных умений персонажей."
        )

        LabModuleCard(
            title = "📜 Генератор Легенд & Квестов (Gemini AI)",
            status = "ГОТОВ",
            description = "Использует нейросеть для генерации процедурных историй, предметов и NPC."
        )

        LabModuleCard(
            title = "🧪 Модуль Физики & Столкновений",
            status = "2D РЕЖИМ",
            description = "Рассчитывает траектории снарядов для аркадных и стрелковых локаций."
        )
    }
}

@Composable
fun LabModuleCard(title: String, status: String, description: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight
                )
                Surface(
                    color = PurpleAccent.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = status,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun ArchiveDocsTab() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "АРХИВ ЗНАНИЙ ARCANUM OS",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent
        )
        Text(
            text = "Спецификации ядра, конституция UI и архитектурные манифесты.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DocItemCard("📜 UI Constitution v1.0", "ARCANUM IS A LIVING GAMING OPERATING SYSTEM. AAA Visual standard & Game-first UX.")
        DocItemCard("⚙️ Core Architecture v0.7.0", "Pure Kotlin ECS, System Pipeline, EventBus, ModuleRegistry, boot.json.")
        DocItemCard("🧩 Micro-Module Catalog", "IBattleModule, ICardsModule, IInventoryModule, IQuestModule, ISaveSyncModule.")
        DocItemCard("🌐 PWA Web Core Mirror", "arcanum-core.js running offline in Service Worker environment.")
    }
}

@Composable
fun DocItemCard(title: String, subtitle: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldLight)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
}
