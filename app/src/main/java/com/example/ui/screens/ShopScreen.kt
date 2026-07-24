package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.InventoryEntity
import com.example.ui.ShopItem
import com.example.ui.components.CardArtGraphic
import com.example.ui.theme.*

@Composable
fun ShopScreen(
    gold: Int,
    shopStock: Map<String, Int>,
    onBuyItem: (ShopItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val shopItems = listOf(
        ShopItem("shop-potion", "Зелье здоровья", "Восстанавливает 30 HP", "potion", 25, 10,
            InventoryEntity("inv-potion", "Зелье здоровья", 1, "potion", "heal", 30)),
        ShopItem("shop-mana", "Зелье маны", "Восстанавливает 40 MP", "potion", 30, 8,
            InventoryEntity("inv-mana", "Зелье маны", 1, "potion", "mana", 40)),
        ShopItem("shop-food", "Провизия", "Восстанавливает 15 HP", "potion", 10, 20,
            InventoryEntity("inv-food", "Провизия", 1, "potion", "heal", 15)),
        ShopItem("shop-scroll", "Свиток огня", "Наносит 40 урона в бою", "crystal", 60, 5,
            InventoryEntity("inv-scroll", "Свиток огня", 1, "crystal", "damage", 40)),
        ShopItem("shop-bomb", "Бомба", "Наносит 60 урона в бою", "crystal", 100, 3,
            InventoryEntity("inv-bomb", "Бомба", 1, "crystal", "damage", 60)),
        ShopItem("shop-gem", "Самоцвет", "Редкий коллекционный предмет", "crystal", 150, 2,
            InventoryEntity("inv-gem", "Самоцвет", 1, "crystal", null, 0)),
        ShopItem("shop-key", "Старый ключ", "Открывает тайные двери", "ring", 200, 1,
            InventoryEntity("inv-key", "Старый ключ", 1, "ring", null, 0)),
        ShopItem("shop-elixir", "Эликсир силы", "Постоянно +2 к силе героя", "potion", 300, 1,
            InventoryEntity("inv-elixir", "Эликсир силы", 1, "potion", "buff_str", 2))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ЛАВКА ТОРГОВЦА",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Твой баланс: ◉ $gold",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(shopItems) { item ->
                val stock = shopStock[item.id] ?: item.stock
                val canBuy = stock > 0 && gold >= item.price

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CardArtGraphic(
                            artKey = item.art,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = item.desc,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.height(30.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "◉ ${item.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )

                        Text(
                            text = if (stock > 0) "В наличии: $stock" else "Распродано",
                            fontSize = 10.sp,
                            color = if (stock > 0) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else RedDanger
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { onBuyItem(item) },
                            enabled = canBuy,
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Купить", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
