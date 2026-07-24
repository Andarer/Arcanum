package com.example

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.ArcanumViewModel
import com.example.ui.components.CardArtGraphic
import com.example.ui.components.LoadingScreen
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val arcanumViewModel: ArcanumViewModel = viewModel()
            val isDarkTheme by arcanumViewModel.isDarkTheme.collectAsStateWithLifecycle()

            ArcanumTheme(darkTheme = isDarkTheme) {
                ArcanumApp(viewModel = arcanumViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcanumApp(viewModel: ArcanumViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    val playerStats by viewModel.playerStats.collectAsStateWithLifecycle()
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    val inventory by viewModel.inventory.collectAsStateWithLifecycle()
    val deck by viewModel.deck.collectAsStateWithLifecycle()
    val quests by viewModel.quests.collectAsStateWithLifecycle()
    val achievements by viewModel.achievements.collectAsStateWithLifecycle()
    val diaryEntries by viewModel.diaryEntries.collectAsStateWithLifecycle()
    val battleState by viewModel.battleState.collectAsStateWithLifecycle()
    val floatingDamages by viewModel.floatingDamages.collectAsStateWithLifecycle()
    val craftSelection by viewModel.craftSelection.collectAsStateWithLifecycle()
    val shopStock by viewModel.shopStockMap.collectAsStateWithLifecycle()
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    val isSoundEnabled by viewModel.isSoundEnabled.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    if (isLoading) {
        LoadingScreen(message = "Инициализация Arcanum...")
        return
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMessage) {
        toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearToast()
        }
    }

    val context = LocalContext.current
    val isOnline = remember { isNetworkAvailable(context) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = GoldLight,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .border(1.dp, GoldAccent, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = data.visuals.message,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = BgDark1,
                            border = BorderStroke(1.dp, GoldAccent),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                CardArtGraphic(artKey = "crystal", modifier = Modifier.size(24.dp))
                            }
                        }
                        Text(
                            text = "ARCANUM",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldLight,
                            letterSpacing = 2.sp
                        )
                    }
                },
                navigationIcon = {
                    if (currentRoute != "home") {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = GoldAccent
                            )
                        }
                    }
                },
                actions = {
                    // Player HUD Compact Bar
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "⚔ ${playerStats.level}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                            Text(
                                text = "◉ ${playerStats.gold}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                        }
                    }

                    // Sound Toggle Icon
                    IconButton(onClick = { viewModel.toggleSound() }) {
                        Icon(
                            imageVector = if (isSoundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                            contentDescription = "Sound",
                            tint = if (isSoundEnabled) GoldAccent else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Theme Toggle Icon
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Theme",
                            tint = GoldAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Status Pill Indicator
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isOnline) GreenSuccess else RedDanger)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    playerStats = playerStats,
                    quests = quests,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            composable("collection") {
                CollectionScreen(
                    cards = cards,
                    onUpgradeCard = { card -> viewModel.upgradeCard(card) },
                    onAddToDeck = { card -> viewModel.addToDeck(card) }
                )
            }

            composable("deck") {
                DeckScreen(
                    deck = deck,
                    allCards = cards,
                    onAddToDeck = { card -> viewModel.addToDeck(card) },
                    onRemoveFromDeck = { idx -> viewModel.removeFromDeck(idx) },
                    onAutoDeck = { viewModel.autoDeck() },
                    onClearDeck = { viewModel.clearDeck() }
                )
            }

            composable("battle") {
                BattleScreen(
                    battleState = battleState,
                    deck = deck,
                    inventory = inventory,
                    floatingDamages = floatingDamages,
                    onAction = { action -> viewModel.battleAction(action) },
                    onUseDeckCard = { idx -> viewModel.useDeckCardInBattle(idx) },
                    onEndTurn = { viewModel.endTurn() },
                    onResetBattle = { viewModel.resetBattle() },
                    onNavigateToHome = { navController.navigate("home") }
                )
            }

            composable("shop") {
                ShopScreen(
                    gold = playerStats.gold,
                    shopStock = shopStock,
                    onBuyItem = { item -> viewModel.buyShopItem(item) }
                )
            }

            composable("quests") {
                QuestsScreen(
                    quests = quests,
                    playerStats = playerStats,
                    totalCardsCount = cards.size,
                    deckSizeCount = deck.size,
                    onClaimQuest = { quest -> viewModel.claimQuestReward(quest) }
                )
            }

            composable("craft") {
                CraftScreen(
                    cards = cards,
                    selectedCards = craftSelection,
                    onToggleSelectCard = { card -> viewModel.toggleCraftSelection(card) },
                    onCraft = { viewModel.performCraft() }
                )
            }

            composable("chest") {
                ChestScreen(
                    gold = playerStats.gold,
                    onOpenChest = { chestType, price -> viewModel.openChest(chestType, price) }
                )
            }

            composable("world") {
                WorldScreen(
                    visitedLocationsStr = playerStats.visitedLocations,
                    onVisitLocation = { locKey -> viewModel.visitLocation(locKey) },
                    onStartBattle = { locKey ->
                        viewModel.visitLocation(locKey)
                        viewModel.resetBattle()
                        navController.navigate("battle")
                    }
                )
            }

            composable("inventory") {
                InventoryScreen(
                    inventory = inventory,
                    onUseItem = { item -> viewModel.useInventoryItem(item) }
                )
            }

            composable("editor") {
                EditorScreen(
                    onCreateCard = { name, type, rarity, hp, mp, str, def, desc, art ->
                        viewModel.createCard(name, type, rarity, hp, mp, str, def, desc, art)
                    }
                )
            }

            composable("achievements") {
                AchievementsScreen(achievements = achievements)
            }

            composable("diary") {
                DiaryScreen(
                    entries = diaryEntries,
                    onClearDiary = { viewModel.clearDiary() }
                )
            }

            composable("print") {
                PrintScreen(cards = cards)
            }

            composable("qr") {
                QrScreen()
            }

            composable("settings") {
                SettingsScreen(
                    isDarkTheme = isDarkTheme,
                    isSoundEnabled = isSoundEnabled,
                    onToggleTheme = { viewModel.toggleTheme() },
                    onToggleSound = { viewModel.toggleSound() },
                    onResetAllData = { viewModel.resetAllData() },
                    onOpenPwa = { navController.navigate("pwa") }
                )
            }

            composable("pwa") {
                PwaWebScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

private fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return true
    val nw = cm.activeNetwork ?: return false
    val actNw = cm.getNetworkCapabilities(nw) ?: return false
    return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
