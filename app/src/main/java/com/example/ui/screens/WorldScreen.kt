package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CardArtGraphic
import com.example.ui.theme.*

data class LocationModel(
    val key: String,
    val name: String,
    val desc: String,
    val tags: List<String>,
    val color: Color,
    val artKey: String,
    val xRatio: Float,
    val yRatio: Float
)

@Composable
fun WorldScreen(
    visitedLocationsStr: String,
    onVisitLocation: (String) -> Unit,
    onStartBattle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLocationKey by remember { mutableStateOf("city") }

    val locations = listOf(
        LocationModel("city", "Столица Арканум", "Величественный город-государство, центр торговли и магии.", listOf("Безопасно", "Торговля", "Квесты"), GoldAccent, "warrior", 0.3f, 0.35f),
        LocationModel("forest", "Древний Лес", "Тёмные вековые деревья хранят тайны эльфов.", listOf("Опасно", "Ресурсы", "Существа"), GreenSuccess, "pet", 0.7f, 0.3f),
        LocationModel("desert", "Пылающая Пустыня", "Бескрайние пески, где солнце плавит камень.", listOf("Экстремально", "Сокровища", "Ловушки"), YellowWarning, "sword", 0.4f, 0.75f),
        LocationModel("castle", "Замок Теней", "Заброшенная крепость на скале.", listOf("Очень опасно", "Боссы", "Лут"), PurpleAccent, "necro", 0.8f, 0.5f),
        LocationModel("dungeon", "Подземелье", "Сеть пещер и катакомб.", listOf("Опасно", "Монстры", "Опыт"), RedDanger, "goblin", 0.2f, 0.6f),
        LocationModel("sea", "Мёртвое Море", "Туманные воды, где покоятся корабли-призраки.", listOf("Загадочно", "События", "Редкие карты"), CyanAccent, "dragon", 0.55f, 0.8f)
    )

    val selectedLoc = locations.find { it.key == selectedLocationKey } ?: locations[0]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "МИР АРКАНУМ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Исследуй локации, открывай тайны мира",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Interactive Canvas Map
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Background ocean & land mass
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF1A3A5A), Color(0xFF05060D))
                        )
                    )

                    // Draw location connector routes
                    val locCoords = locations.map { Offset(it.xRatio * w, it.yRatio * h) }
                    for (i in 0 until locCoords.size - 1) {
                        drawLine(
                            color = GoldAccent.copy(alpha = 0.3f),
                            start = locCoords[i],
                            end = locCoords[i + 1],
                            strokeWidth = 3f
                        )
                    }
                }

                // Interactive Location Nodes
                locations.forEach { loc ->
                    val isSelected = selectedLocationKey == loc.key
                    val isVisited = visitedLocationsStr.split(",").contains(loc.key)

                    Box(
                        modifier = Modifier
                            .offset(
                                x = (loc.xRatio * 320).dp,
                                y = (loc.yRatio * 200).dp
                            )
                            .size(24.dp)
                            .clickable {
                                selectedLocationKey = loc.key
                                onVisitLocation(loc.key)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) GoldLight else loc.color,
                            border = BorderStroke(2.dp, Color.White),
                            modifier = Modifier.size(if (isSelected) 22.dp else 16.dp)
                        ) {}
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Location Info Card
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, selectedLoc.color),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CardArtGraphic(
                        artKey = selectedLoc.artKey,
                        modifier = Modifier.size(60.dp)
                    )

                    Column {
                        Text(
                            text = selectedLoc.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = selectedLoc.color
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            selectedLoc.tags.forEach { tag ->
                                Surface(
                                    color = selectedLoc.color.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(0.5.dp, selectedLoc.color)
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 9.sp,
                                        color = selectedLoc.color,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = selectedLoc.desc,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onStartBattle(selectedLoc.key) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("⚔ Искать врагов в этой локации", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
