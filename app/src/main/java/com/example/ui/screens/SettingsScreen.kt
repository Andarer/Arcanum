package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    isSoundEnabled: Boolean,
    onToggleTheme: () -> Unit,
    onToggleSound: () -> Unit,
    onResetAllData: () -> Unit,
    onExportSave: () -> String = { "{}" },
    onImportSave: (String) -> Boolean = { false },
    onOpenPwa: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showExportDialog by remember { mutableStateOf(false) }
    var showImportDialog by remember { mutableStateOf(false) }
    var exportText by remember { mutableStateOf("") }
    var importText by remember { mutableStateOf("") }

    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "НАСТРОЙКИ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = GoldLight,
            letterSpacing = 2.sp
        )
        Text(
            text = "Параметры приложения и синхронизация прогресса",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Theme Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Тёмная тема", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                        Text("Переключить оформление", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { onToggleTheme() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GoldAccent,
                            checkedTrackColor = GoldAccent.copy(alpha = 0.3f)
                        )
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Sound Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Звуковые эффекты", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                        Text("Включить звуки сражений и интерфейса", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                    Switch(
                        checked = isSoundEnabled,
                        onCheckedChange = { onToggleSound() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GoldAccent,
                            checkedTrackColor = GoldAccent.copy(alpha = 0.3f)
                        )
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Save Cloud Sync Export / Import
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Синхронизация прогресса (JSON)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                    Text("Экспортируйте или импортируйте сохранения для переключения между PWA и Android", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                exportText = onExportSave()
                                showExportDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("📤 Экспорт JSON", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                importText = ""
                                showImportDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("📥 Импорт JSON", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Reset Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Сбросить прогресс", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RedDanger)
                        Text("Удалить все сохранения и начать заново", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                    Button(
                        onClick = onResetAllData,
                        colors = ButtonDefaults.buttonColors(containerColor = RedDanger)
                    ) {
                        Text("Сбросить", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // PWA Browser Engine
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("PWA Веб-Версия", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                        Text("Запустить веб-версию игры прямо в браузере WebView", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                    Button(
                        onClick = onOpenPwa,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                    ) {
                        Text("Запустить PWA", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Version Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Версия", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                    Text("Arcanum v0.5.0 (Android Native & PWA Engine)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
            }
        }
    }

    // Export Dialog
    if (showExportDialog) {
        Dialog(onDismissRequest = { showExportDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, GoldAccent),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📤 Экспорт Сохранения JSON", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = exportText,
                        onValueChange = {},
                        readOnly = true,
                        maxLines = 8,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { showExportDialog = false }) {
                            Text("Закрыть", color = Color.Gray)
                        }
                        Button(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(exportText))
                                showExportDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)
                        ) {
                            Text("📋 Скопировать", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Import Dialog
    if (showImportDialog) {
        Dialog(onDismissRequest = { showImportDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, PurpleAccent),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📥 Импорт Сохранения JSON", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldLight)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = importText,
                        onValueChange = { importText = it },
                        placeholder = { Text("Вставьте JSON сохранения...") },
                        maxLines = 8,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { showImportDialog = false }) {
                            Text("Отмена", color = Color.Gray)
                        }
                        Button(
                            onClick = {
                                if (importText.isNotBlank()) {
                                    onImportSave(importText)
                                    showImportDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent)
                        ) {
                            Text("Загрузить", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
