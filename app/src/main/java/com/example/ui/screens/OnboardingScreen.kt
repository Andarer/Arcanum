package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector,
    val badge: String,
    val gradientColors: List<Color>
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = remember {
        listOf(
            OnboardingPage(
                title = "ДОБРО ПОЖАЛОВАТЬ В ARCANUM",
                subtitle = "Карточная Мистическая RPG",
                description = "Собирайте легендарные карты, сражайтесь в дуэлях, исследуйте мир и открывайте мистические артефакты.",
                icon = Icons.Default.AutoAwesome,
                badge = "ЭПИЧЕСКОЕ ПРИКЛЮЧЕНИЕ",
                gradientColors = listOf(GoldAccent, Color(0xFF8B6B23))
            ),
            OnboardingPage(
                title = "ONLINE PVP И БОЕВАЯ АРЕНА",
                subtitle = "Сражайтесь с игроками по всему миру",
                description = "Испытайте силу вашей колоды в реальном времени. Повышайте MMR рейтинг и побеждайте в рейтинговых лигах!",
                icon = Icons.Default.Shield,
                badge = "РЕЙТИНГОВЫЕ БОИ",
                gradientColors = listOf(RedDanger, Color(0xFF8B0000))
            ),
            OnboardingPage(
                title = "КЛИКЕР И АРКАДНЫЙ ТИР",
                subtitle = "Добывайте ресурсы быстрым касанием",
                description = "Кликайте по Кристаллу Арканума, закупайте авто-майнеры и уничтожайте падающих монстров в мини-играх!",
                icon = Icons.Default.FlashOn,
                badge = "МИНИ-ИГРЫ & МАЙНИНГ",
                gradientColors = listOf(PurpleAccent, Color(0xFF4A148C))
            ),
            OnboardingPage(
                title = "КРАФТ И КОЛЛЕКЦИОНИРОВАНИЕ",
                subtitle = "Создавайте уникальные карты",
                description = "Объединяйте ресурсы, создавайте эпическое снаряжение и пополняйте вашу сундуковую библиотеку!",
                icon = Icons.Default.Style,
                badge = "МАСТЕР КРАФТА",
                gradientColors = listOf(GoldLight, GoldAccent)
            )
        )
    }

    var currentPageIndex by remember { mutableIntStateOf(0) }
    var dragAmount by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BgDark2, Color.Black)
                )
            )
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ARCANUM",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = GoldLight,
                letterSpacing = 3.sp
            )

            TextButton(onClick = onFinishOnboarding) {
                Text(
                    text = "ПРОПУСТИТЬ",
                    color = GoldAccent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Carousel Card with Swipe Gesture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (dragAmount < -50f && currentPageIndex < pages.size - 1) {
                                currentPageIndex++
                            } else if (dragAmount > 50f && currentPageIndex > 0) {
                                currentPageIndex--
                            }
                            dragAmount = 0f
                        },
                        onHorizontalDrag = { _, dragDistance ->
                            dragAmount += dragDistance
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            val page = pages[currentPageIndex]

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BgDark1),
                border = BorderStroke(2.dp, page.gradientColors.first()),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.88f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = page.gradientColors.first().copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, page.gradientColors.first())
                    ) {
                        Text(
                            text = page.badge,
                            color = page.gradientColors.first(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    // Main Icon Graphic
                    Surface(
                        shape = CircleShape,
                        color = BgDark2,
                        border = BorderStroke(3.dp, page.gradientColors.first()),
                        shadowElevation = 16.dp,
                        modifier = Modifier.size(110.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = page.icon,
                                contentDescription = null,
                                tint = page.gradientColors.first(),
                                modifier = Modifier.size(56.dp)
                            )
                        }
                    }

                    // Texts
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = page.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = GoldLight,
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = page.subtitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = page.gradientColors.first(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = page.description,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }

                    // Swipe Hint Text
                    Text(
                        text = "👈 Свайпните влево / вправо 👉",
                        fontSize = 11.sp,
                        color = GoldAccent.copy(alpha = 0.6f)
                    )
                }
            }
        }

        // Bottom Controls: Page Indicator Dots & Next / Finish Button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            // Page Indicator Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                pages.forEachIndexed { index, _ ->
                    val isSelected = index == currentPageIndex
                    Box(
                        modifier = Modifier
                            .width(if (isSelected) 24.dp else 8.dp)
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) GoldAccent else Color.Gray.copy(alpha = 0.4f)
                            )
                    )
                }
            }

            // Action Button
            Button(
                onClick = {
                    if (currentPageIndex < pages.size - 1) {
                        currentPageIndex++
                    } else {
                        onFinishOnboarding()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = if (currentPageIndex == pages.size - 1) "НАЧАТЬ ИГРУ!" else "ДАЛЕЕ",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (currentPageIndex == pages.size - 1) Icons.Default.Star else Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}
