package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class RenderProfile(
    val id: String,
    val title: String,
    val icon: String,
    val description: String
) {
    Fantasy("fantasy", "Fantasy OS", "✨", "Золотые руны, анимированный пергамент, магия"),
    Dark("dark", "Gothic Dark OS", "🔥", "Темный обсидиан, багровые пламена, готика"),
    SciFi("scifi", "Sci-Fi HUD OS", "🌐", "Голографический интерфейс, неоновая сетка, телеметрия"),
    Cyberpunk("cyberpunk", "Cyberpunk Neon", "⚡", "Кибер-неон, угловатые рамки, напряжение"),
    Pixel("pixel", "Retro Pixel Arcade", "🕹️", "Ретро пиксель-арт, классические 8-бит аркады"),
    Console("console", "Console & Steam OS", "🎮", "Консольный стиль, плавные карты, свечение фокуса"),
    Minimal("minimal", "Architect Minimal", "🏛️", "Ультра-лаконичный стиль Архитектора")
}

data class RenderStyleSpec(
    val primaryColor: Color,
    val secondaryColor: Color,
    val accentColor: Color,
    val bgGradientStart: Color,
    val bgGradientEnd: Color,
    val surfaceColor: Color,
    val cardBorderColor: Color,
    val borderWidth: Dp,
    val cornerRadius: Dp,
    val glowColor: Color
)

val LocalRenderProfile = compositionLocalOf { RenderProfile.Fantasy }

@Composable
fun getRenderStyle(profile: RenderProfile): RenderStyleSpec {
    return when (profile) {
        RenderProfile.Fantasy -> RenderStyleSpec(
            primaryColor = GoldAccent,
            secondaryColor = PurpleAccent,
            accentColor = GoldLight,
            bgGradientStart = Color(0xFF0D0B18),
            bgGradientEnd = Color(0xFF1E1735),
            surfaceColor = Color(0xFF161228),
            cardBorderColor = GoldAccent.copy(alpha = 0.6f),
            borderWidth = 1.5.dp,
            cornerRadius = 16.dp,
            glowColor = GoldAccent.copy(alpha = 0.3f)
        )
        RenderProfile.Dark -> RenderStyleSpec(
            primaryColor = Color(0xFFE53935),
            secondaryColor = Color(0xFF8E24AA),
            accentColor = Color(0xFFFF5252),
            bgGradientStart = Color(0xFF0A0508),
            bgGradientEnd = Color(0xFF1C0A12),
            surfaceColor = Color(0xFF150A10),
            cardBorderColor = Color(0xFFE53935).copy(alpha = 0.7f),
            borderWidth = 2.dp,
            cornerRadius = 12.dp,
            glowColor = Color(0xFFE53935).copy(alpha = 0.4f)
        )
        RenderProfile.SciFi -> RenderStyleSpec(
            primaryColor = Color(0xFF00E5FF),
            secondaryColor = Color(0xFF2979FF),
            accentColor = Color(0xFF18FFFF),
            bgGradientStart = Color(0xFF020B14),
            bgGradientEnd = Color(0xFF0A1E30),
            surfaceColor = Color(0xFF071728),
            cardBorderColor = Color(0xFF00E5FF).copy(alpha = 0.8f),
            borderWidth = 1.dp,
            cornerRadius = 8.dp,
            glowColor = Color(0xFF00E5FF).copy(alpha = 0.35f)
        )
        RenderProfile.Cyberpunk -> RenderStyleSpec(
            primaryColor = Color(0xFFFF007F),
            secondaryColor = Color(0xFF00E5FF),
            accentColor = Color(0xFFFFEA00),
            bgGradientStart = Color(0xFF12001A),
            bgGradientEnd = Color(0xFF2B0038),
            surfaceColor = Color(0xFF1D0029),
            cardBorderColor = Color(0xFFFF007F),
            borderWidth = 2.dp,
            cornerRadius = 4.dp,
            glowColor = Color(0xFFFF007F).copy(alpha = 0.5f)
        )
        RenderProfile.Pixel -> RenderStyleSpec(
            primaryColor = Color(0xFF76FF03),
            secondaryColor = Color(0xFFFFD600),
            accentColor = Color(0xFFC6FF00),
            bgGradientStart = Color(0xFF1B2A1C),
            bgGradientEnd = Color(0xFF0F1A10),
            surfaceColor = Color(0xFF142416),
            cardBorderColor = Color(0xFF76FF03),
            borderWidth = 3.dp,
            cornerRadius = 0.dp,
            glowColor = Color(0xFF76FF03).copy(alpha = 0.3f)
        )
        RenderProfile.Console -> RenderStyleSpec(
            primaryColor = Color(0xFF3A86FF),
            secondaryColor = Color(0xFF8338EC),
            accentColor = Color(0xFF00F5D4),
            bgGradientStart = Color(0xFF0B132B),
            bgGradientEnd = Color(0xFF1C2541),
            surfaceColor = Color(0xFF151E3D),
            cardBorderColor = Color(0xFF3A86FF).copy(alpha = 0.5f),
            borderWidth = 1.5.dp,
            cornerRadius = 20.dp,
            glowColor = Color(0xFF3A86FF).copy(alpha = 0.3f)
        )
        RenderProfile.Minimal -> RenderStyleSpec(
            primaryColor = Color(0xFFE0E0E0),
            secondaryColor = Color(0xFF9E9E9E),
            accentColor = Color(0xFFFFFFFF),
            bgGradientStart = Color(0xFF121212),
            bgGradientEnd = Color(0xFF1E1E1E),
            surfaceColor = Color(0xFF1A1A1A),
            cardBorderColor = Color(0xFF424242),
            borderWidth = 1.dp,
            cornerRadius = 12.dp,
            glowColor = Color.White.copy(alpha = 0.1f)
        )
    }
}
