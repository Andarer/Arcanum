package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
    var name by remember { mutableStateOf("Странник") }
    var type by remember { mutableStateOf("item") }
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
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "РЕДАКТОР КАРТ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Создай собственную карту для коллекции",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Live Preview Card
        Text(
            text = "ПРЕДПРОСМОТР",
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
                .width(180.dp)
                .height(260.dp)
                .align(Alignment.CenterHorizontally)
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
                        text = type.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    RarityBadge(rarity = rarity)
                }

                Spacer(modifier = Modifier.height(6.dp))

                CardArtGraphic(
                    artKey = art,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = name.ifBlank { "Без имени" },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight,
                    maxLines = 1
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (hp > 0) StatChip("❤ $hp")
                    if (mp > 0) StatChip("✦ $mp")
                    if (str > 0) StatChip("⚔ $str")
                    if (def > 0) StatChip("🛡 $def")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Editor Form
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Art Selector Dropdown / Row
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

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        // Type Selector Row
        Text("Тип", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        TabRow(
            selectedTabIndex = typeOptions.indexOf(type).coerceAtLeast(0),
            modifier = Modifier.fillMaxWidth()
        ) {
            typeOptions.forEach { t ->
                Tab(
                    selected = type == t,
                    onClick = { type = t },
                    text = { Text(t.uppercase(), fontSize = 10.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onCreateCard(name, type, rarity, hp, mp, str, def, desc, art)
            },
            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("✦ Создать карту", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
