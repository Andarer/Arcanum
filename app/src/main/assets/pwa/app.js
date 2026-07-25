// Arcanum Card MMORPG - Full PWA Game Engine
const DEFAULT_PLAYER = {
  name: "Рыцарь Арканума",
  level: 1,
  xp: 0,
  xpMax: 100,
  gold: 250,
  hp: 100,
  hpMax: 100,
  mp: 50,
  mpMax: 50,
  str: 18,
  def: 12,
  wins: 0,
  losses: 0,
  completedQuests: 0
};

const INITIAL_CARDS = [
  { id: "c1", name: "Пламенный Рыцарь", type: "hero", rarity: "epic", art: "⚔️", level: 1, hp: 85, str: 22, def: 10, abilityCost: 10, desc: "Мощный рыцарь с огненным клинком." },
  { id: "c2", name: "Лесной Дракон", type: "creature", rarity: "legendary", art: "🐉", level: 1, hp: 130, str: 30, def: 14, abilityCost: 15, desc: "Древний дракон, защищающий святилище." },
  { id: "c3", name: "Щит Порядка", type: "item", rarity: "rare", art: "🛡️", level: 1, hp: 35, str: 0, def: 20, abilityCost: 5, desc: "Благословенный щит Ордена." },
  { id: "c4", name: "Теневой Волк", type: "creature", rarity: "common", art: "🐺", level: 1, hp: 50, str: 16, def: 5, abilityCost: 0, desc: "Быстрый ночной хищник." },
  { id: "c5", name: "Кристалл Магии", type: "item", rarity: "epic", art: "💎", level: 1, hp: 10, str: 5, def: 5, abilityCost: 20, desc: "Восстанавливает MP в бою." },
  { id: "c6", name: "Маг Молний", type: "hero", rarity: "rare", art: "⚡", level: 1, hp: 70, str: 25, def: 6, abilityCost: 12, desc: "Управляет грозами." },
  { id: "c7", name: "Феникс Возрождения", type: "creature", rarity: "mythic", art: "🔥", level: 1, hp: 150, str: 35, def: 15, abilityCost: 25, desc: "Мифическая птица огня." }
];

const LOCATIONS = [
  { id: "loc1", name: "Шепчущий Лес", art: "🌲", levelReq: 1, desc: "Густые чащи, кишащие волками и гоблинами.", enemy: { name: "Гоблин-Разбойник", art: "👹", hp: 60, str: 12, def: 3, gold: 30, xp: 25 } },
  { id: "loc2", name: "Подземелье Теней", art: "🏰", levelReq: 2, desc: "Заброшенные руины древнего ордена.", enemy: { name: "Скелет-Рыцарь", art: "💀", hp: 95, str: 18, def: 7, gold: 60, xp: 50 } },
  { id: "loc3", name: "Вулкан Арканума", art: "🌋", levelReq: 3, desc: "Огненная цитадель Древнего Дракона.", enemy: { name: "Огненный Демон", art: "👿", hp: 140, str: 26, def: 12, gold: 120, xp: 90 } }
];

const CRAFT_RECIPES = [
  { id: "rec1", name: "Меч Дракона", cost: 100, art: "🗡️", desc: "Комбинация Руды и Кристалла", resultCard: { id: "c_crafted1", name: "Меч Дракона", type: "item", rarity: "epic", art: "🗡️", level: 1, hp: 20, str: 28, def: 10, abilityCost: 10, desc: "Выкован в огне подземелий." } },
  { id: "rec2", name: "Амулет Легенд", cost: 200, art: "📿", desc: "Слияние 2 редких карт", resultCard: { id: "c_crafted2", name: "Амулет Легенд", type: "item", rarity: "legendary", art: "📿", level: 1, hp: 50, str: 15, def: 25, abilityCost: 5, desc: "Дарует защитный барьер." } }
];

const QUESTS = [
  { id: "q1", title: "Первая Арена", desc: "Одержите 1 победу на Арене", rewardGold: 50, rewardXp: 30, targetWins: 1, claimed: false },
  { id: "q2", title: "Мастер Ковки", desc: "Скрафтьте 1 предмет в Кузнице", rewardGold: 100, rewardXp: 60, targetWins: 3, claimed: false },
  { id: "q3", title: "Завоеватель", desc: "Победите 5 врагов", rewardGold: 200, rewardXp: 120, targetWins: 5, claimed: false }
];

const CHESTS = [
  { id: "ch1", name: "Деревянный Сундук", art: "📦", cost: 50, rarity: "common" },
  { id: "ch2", name: "Золотой Сундук", art: "🪙", cost: 150, rarity: "rare" },
  { id: "ch3", name: "Эпический Сундук Арканума", art: "🎁", cost: 300, rarity: "epic" }
];

let gameState = {
  player: { ...DEFAULT_PLAYER },
  cards: [...INITIAL_CARDS],
  deck: ["c1", "c2", "c3"],
  quests: [...QUESTS],
  currentEnemy: null,
  enemyHp: 0
};

// LocalStorage Persistence
function loadGame() {
  try {
    const saved = localStorage.getItem('arcanum_pwa_full_save');
    if (saved) {
      const parsed = JSON.parse(saved);
      gameState = { ...gameState, ...parsed };
    }
  } catch (e) {
    console.error("Failed to load state", e);
  }
  renderHUD();
}

function saveGame() {
  try {
    localStorage.setItem('arcanum_pwa_full_save', JSON.stringify(gameState));
  } catch (e) {
    console.error("Failed to save state", e);
  }
}

function resetGame() {
  if (confirm("Вы уверены, что хотите сбросить весь прогресс?")) {
    localStorage.removeItem('arcanum_pwa_full_save');
    location.reload();
  }
}

// UI Render Helpers
function renderHUD() {
  document.getElementById('hud-gold').innerText = gameState.player.gold;
  document.getElementById('hud-level').innerText = gameState.player.level;
  document.getElementById('hud-hp').innerText = `${gameState.player.hp}/${gameState.player.hpMax}`;
}

function showToast(msg) {
  const toast = document.getElementById('toast');
  toast.innerText = msg;
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 2500);
}

function switchTab(tabName) {
  document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
  document.querySelectorAll('.screen-view').forEach(view => view.classList.remove('active'));

  const activeBtn = document.querySelector(`.tab-btn[data-tab="${tabName}"]`);
  const activeView = document.getElementById(`view-${tabName}`);

  if (activeBtn) activeBtn.classList.add('active');
  if (activeView) activeView.classList.add('active');

  if (tabName === 'collection') renderCollection();
  if (tabName === 'battle') prepareBattle();
  if (tabName === 'pvp') renderPvp();
  if (tabName === 'clicker') renderClicker();
  if (tabName === 'shooter') initShooter();
  if (tabName === 'world') renderWorld();
  if (tabName === 'craft') renderCraft();
  if (tabName === 'deck') renderDeck();
  if (tabName === 'quests') renderQuests();
  if (tabName === 'chests') renderChests();
  if (tabName === 'docs') {
    if (window.ArcanumDocsEngine) {
      window.ArcanumDocsEngine.renderDocList();
      window.ArcanumDocsEngine.renderDocContent(window.ArcanumDocsEngine.activeDocKey);
      window.ArcanumDocsEngine.renderArchitectureGraph();
    }
  }
  if (tabName === 'kernel') {
    if (window.ArcanumKernelEngine) {
      window.ArcanumKernelEngine.renderDigitalTwinDashboard();
    }
  }
  if (tabName === 'package') {
    if (window.ArcanumPackageEngine) {
      window.ArcanumPackageEngine.renderPackageManagerDashboard();
    }
  }
}

// PVP Arena Engine
let pvpState = {
  rating: 1200,
  inMatch: false,
  enemyHp: 120,
  enemyHpMax: 120,
  enemyName: "Игрок",
  turnTimer: null
};

function renderPvp() {
  const ratingEl = document.getElementById('pvp-rating');
  if (ratingEl) ratingEl.innerText = pvpState.rating;
}

function startPvpMatchmaking() {
  const statusText = document.getElementById('pvp-status-text');
  statusText.innerText = "Поиск соперника на сервере...";

  setTimeout(() => {
    const randomOpponents = ["ShadowBlade99", "ArcaneMaster", "DragonSlayer_RU", "Knight_Vadim"];
    pvpState.enemyName = randomOpponents[Math.floor(Math.random() * randomOpponents.length)];
    pvpState.enemyHp = 120 + Math.floor(Math.random() * 40);
    pvpState.enemyHpMax = pvpState.enemyHp;
    pvpState.inMatch = true;

    document.getElementById('pvp-status-box').style.display = 'none';
    document.getElementById('pvp-battle-box').style.display = 'flex';
    document.getElementById('pvp-enemy-name').innerText = `${pvpState.enemyName} (Рейтинг: ${pvpState.rating + 15})`;
    updatePvpBattleUI();
    showToast(`Соперник найден: ${pvpState.enemyName}!`);
  }, 1200);
}

function updatePvpBattleUI() {
  document.getElementById('pvp-enemy-hp-text').innerText = `${pvpState.enemyHp} / ${pvpState.enemyHpMax}`;
}

function pvpPlayerAttack() {
  if (!pvpState.inMatch) return;
  const dmg = 15 + Math.floor(Math.random() * 12);
  pvpState.enemyHp = Math.max(0, pvpState.enemyHp - dmg);
  updatePvpBattleUI();
  showToast(`Вы нанесли ${dmg} урона по ${pvpState.enemyName}!`);

  if (pvpState.enemyHp <= 0) {
    pvpState.rating += 25;
    gameState.player.gold += 80;
    saveGame();
    renderHUD();
    showToast("ПОБЕДА В PVP! +25 Рейтинга, +80 ◉");
    resetPvpMatch();
  } else {
    setTimeout(pvpEnemyTurn, 800);
  }
}

function pvpPlayerMagic() {
  if (!pvpState.inMatch) return;
  const dmg = 28;
  pvpState.enemyHp = Math.max(0, pvpState.enemyHp - dmg);
  updatePvpBattleUI();
  showToast(`✨ Магический урон! -${dmg} HP!`);

  if (pvpState.enemyHp <= 0) {
    pvpState.rating += 25;
    gameState.player.gold += 80;
    saveGame();
    renderHUD();
    showToast("ПОБЕДА В PVP! +25 Рейтинга, +80 ◉");
    resetPvpMatch();
  } else {
    setTimeout(pvpEnemyTurn, 800);
  }
}

function pvpEnemyTurn() {
  if (!pvpState.inMatch) return;
  const dmg = 12 + Math.floor(Math.random() * 10);
  gameState.player.hp = Math.max(0, gameState.player.hp - dmg);
  renderHUD();
  showToast(`${pvpState.enemyName} ответил атакой на ${dmg} урона!`);

  if (gameState.player.hp <= 0) {
    gameState.player.hp = gameState.player.hpMax;
    pvpState.rating = Math.max(1000, pvpState.rating - 15);
    showToast("Поражение в PVP! -15 Рейтинга.");
    resetPvpMatch();
  }
}

function resetPvpMatch() {
  pvpState.inMatch = false;
  document.getElementById('pvp-status-box').style.display = 'block';
  document.getElementById('pvp-battle-box').style.display = 'none';
  renderPvp();
}

// Tap Clicker Engine
let clickerState = {
  clickPower: 1,
  autoMiners: 0
};

function renderClicker() {
  document.getElementById('click-power').innerText = clickerState.clickPower;
  document.getElementById('auto-miners-count').innerText = clickerState.autoMiners;
}

function clickCrystal(event) {
  playSfx('click');
  gameState.player.gold += clickerState.clickPower;
  renderHUD();

  // Floating text animation
  const floatText = document.createElement('div');
  floatText.className = 'floating-click-text';
  floatText.innerText = `+${clickerState.clickPower} ◉`;
  floatText.style.left = `${event.clientX - 20}px`;
  floatText.style.top = `${event.clientY - 20}px`;
  document.body.appendChild(floatText);
  setTimeout(() => floatText.remove(), 800);
}

function buyClickUpgrade() {
  if (gameState.player.gold >= 50) {
    gameState.player.gold -= 50;
    clickerState.clickPower += 1;
    saveGame();
    renderHUD();
    renderClicker();
    showToast("Клик укрепился! +1 к силе добычи.");
  } else {
    showToast("Недостаточно золота!");
  }
}

function buyAutoMiner() {
  if (gameState.player.gold >= 150) {
    gameState.player.gold -= 150;
    clickerState.autoMiners += 1;
    saveGame();
    renderHUD();
    renderClicker();
    showToast("Авто-майнер нанят!");
  } else {
    showToast("Недостаточно золота!");
  }
}

// Auto-miner ticker loop
setInterval(() => {
  if (clickerState.autoMiners > 0) {
    gameState.player.gold += clickerState.autoMiners;
    renderHUD();
  }
}, 1000);

// Arcade Shooter Engine
let shooterState = {
  score: 0,
  targets: [],
  animFrame: null,
  active: false
};

function initShooter() {
  const canvas = document.getElementById('shooter-canvas');
  if (!canvas) return;

  canvas.onclick = (e) => {
    if (!shooterState.active) return;
    const rect = canvas.getBoundingClientRect();
    const clickX = e.clientX - rect.left;
    const clickY = e.clientY - rect.top;

    shooterState.targets.forEach((target, idx) => {
      const dist = Math.hypot(target.x - clickX, target.y - clickY);
      if (dist < target.radius) {
        shooterState.score += 10;
        document.getElementById('shooter-score').innerText = shooterState.score;
        shooterState.targets.splice(idx, 1);
        gameState.player.gold += 5;
        renderHUD();
      }
    });
  };
}

function startShooterGame() {
  shooterState.score = 0;
  shooterState.targets = [];
  shooterState.active = true;
  document.getElementById('shooter-score').innerText = 0;

  if (shooterState.animFrame) cancelAnimationFrame(shooterState.animFrame);
  runShooterLoop();
  showToast("Шутер запущен! Кликайте по летящим шарам.");
}

function runShooterLoop() {
  const canvas = document.getElementById('shooter-canvas');
  if (!canvas || !shooterState.active) return;
  const ctx = canvas.getContext('2d');

  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // Spawn targets
  if (Math.random() < 0.05 && shooterState.targets.length < 5) {
    shooterState.targets.push({
      x: Math.random() * (canvas.width - 40) + 20,
      y: 0,
      speed: 1 + Math.random() * 2,
      radius: 15,
      color: '#c5a059'
    });
  }

  // Update and draw targets
  shooterState.targets.forEach((target, idx) => {
    target.y += target.speed;

    ctx.beginPath();
    ctx.arc(target.x, target.y, target.radius, 0, Math.PI * 2);
    ctx.fillStyle = target.color;
    ctx.fill();
    ctx.strokeStyle = '#f3e5ab';
    ctx.stroke();

    if (target.y > canvas.height) {
      shooterState.targets.splice(idx, 1);
    }
  });

  shooterState.animFrame = requestAnimationFrame(runShooterLoop);
}

function renderCollection() {
  const container = document.getElementById('collection-grid');
  const query = (document.getElementById('card-search-input')?.value || "").toLowerCase();
  container.innerHTML = '';

  const filtered = gameState.cards.filter(c => c.name.toLowerCase().includes(query) || c.desc.toLowerCase().includes(query));

  filtered.forEach(card => {
    const isDeck = gameState.deck.includes(card.id);
    const cardEl = document.createElement('div');
    cardEl.className = 'game-card';
    cardEl.innerHTML = `
      <div class="card-header">
        <span class="card-type">${card.type}</span>
        <span class="card-rarity rarity-${card.rarity}">${card.rarity}</span>
      </div>
      <div class="card-art">${card.art}</div>
      <div class="card-title">${card.name} ${isDeck ? '⭐' : ''}</div>
      <div class="card-stats">
        <span class="stat-pill">❤ ${card.hp}</span>
        <span class="stat-pill">⚔ ${card.str}</span>
        <span class="stat-pill">🛡 ${card.def}</span>
      </div>
    `;
    cardEl.onclick = () => openCardDetail(card);
    container.appendChild(cardEl);
  });
}

function openCardDetail(card) {
  const modal = document.getElementById('modal');
  const isDeck = gameState.deck.includes(card.id);

  modal.querySelector('.modal-card').innerHTML = `
    <h2 style="color: var(--gold-light); text-align: center;">${card.name} (Ур. ${card.level})</h2>
    <div style="font-size: 42px; text-align: center; margin: 8px 0;">${card.art}</div>
    <p style="font-size: 11px; color: var(--text-muted); text-align: center;">${card.desc}</p>
    <div style="margin: 12px 0; font-size: 12px; display: flex; justify-content: space-around;">
      <span>❤ HP: ${card.hp}</span>
      <span>⚔ STR: ${card.str}</span>
      <span>🛡 DEF: ${card.def}</span>
    </div>
    <div style="display: flex; gap: 8px; flex-wrap: wrap;">
      <button class="btn-action" style="flex:1;" onclick="closeModal()">Закрыть</button>
      <button class="btn-action" style="flex:1; background: var(--purple-accent); color: white;" onclick="toggleDeckCard('${card.id}')">
        ${isDeck ? 'Убрать из колоды' : 'В колоду'}
      </button>
      <button class="btn-action" style="flex:1; background: #27ae60; color: white;" onclick="upgradeCard('${card.id}')">Улучшить (50 ◉)</button>
    </div>
  `;
  modal.classList.add('active');
}

function toggleDeckCard(cardId) {
  const index = gameState.deck.indexOf(cardId);
  if (index >= 0) {
    if (gameState.deck.length <= 1) {
      showToast("В колоде должна оставаться хотя бы 1 карта!");
      return;
    }
    gameState.deck.splice(index, 1);
    showToast("Карта убрана из колоды");
  } else {
    if (gameState.deck.length >= 5) {
      showToast("В колоде может быть максимум 5 карт!");
      return;
    }
    gameState.deck.push(cardId);
    showToast("Карта добавлена в колоду");
  }
  saveGame();
  closeModal();
  renderCollection();
}

function upgradeCard(cardId) {
  const card = gameState.cards.find(c => c.id === cardId);
  if (card && gameState.player.gold >= 50) {
    gameState.player.gold -= 50;
    card.level += 1;
    card.hp += 12;
    card.str += 5;
    card.def += 3;
    saveGame();
    renderHUD();
    closeModal();
    showToast(`Карта ${card.name} улучшена до Ur.${card.level}!`);
    renderCollection();
  } else {
    showToast("Недостаточно золота!");
  }
}

function closeModal() {
  document.getElementById('modal').classList.remove('active');
}

// Battle Engine
function prepareBattle(enemyOverride = null) {
  const enemy = enemyOverride || { name: "Гоблин-Берсерк", art: "👹", hp: 70, hpMax: 70, str: 14, def: 3, goldReward: 35, xpReward: 30 };
  gameState.currentEnemy = enemy;
  gameState.enemyHp = enemy.hp || enemy.hpMax;
  renderBattle();
}

function renderBattle() {
  const enemy = gameState.currentEnemy;
  document.getElementById('enemy-name').innerText = enemy.name;
  document.getElementById('enemy-art').innerText = enemy.art;
  document.getElementById('enemy-hp-text').innerText = `${gameState.enemyHp} / ${enemy.hpMax || enemy.hp}`;
  document.getElementById('enemy-hp-fill').style.width = `${(gameState.enemyHp / (enemy.hpMax || enemy.hp)) * 100}%`;

  document.getElementById('player-hp-text').innerText = `${gameState.player.hp} / ${gameState.player.hpMax}`;
  document.getElementById('player-hp-fill').style.width = `${(gameState.player.hp / gameState.player.hpMax) * 100}%`;
}

function playerAttack() {
  if (!gameState.currentEnemy || gameState.enemyHp <= 0) return;
  playSfx('attack');

  const dmg = Math.max(6, gameState.player.str - (gameState.currentEnemy.def || 0) + Math.floor(Math.random() * 8));
  gameState.enemyHp = Math.max(0, gameState.enemyHp - dmg);
  showToast(`Вы нанесли ${dmg} урона!`);

  if (gameState.enemyHp <= 0) {
    playSfx('victory');
    const gold = gameState.currentEnemy.goldReward || 40;
    const xp = gameState.currentEnemy.xpReward || 30;
    gameState.player.gold += gold;
    gameState.player.xp += xp;
    gameState.player.wins += 1;

    showToast(`Победа! +${gold} ◉, +${xp} XP`);
    saveGame();
    renderHUD();
    setTimeout(() => prepareBattle(), 1500);
  } else {
    setTimeout(enemyTurn, 700);
  }
  renderBattle();
}

function useSpecialAbility() {
  if (gameState.player.mp >= 10) {
    playSfx('magic');
    gameState.player.mp -= 10;
    const dmg = gameState.player.str * 2;
    gameState.enemyHp = Math.max(0, gameState.enemyHp - dmg);
    showToast(`✨ Магический Удар! Нанесено ${dmg} урона!`);
    if (gameState.enemyHp <= 0) {
      playSfx('victory');
      gameState.player.wins += 1;
      gameState.player.gold += 50;
      saveGame();
      renderHUD();
      setTimeout(() => prepareBattle(), 1200);
    } else {
      setTimeout(enemyTurn, 700);
    }
    renderBattle();
  } else {
    showToast("Недостаточно маны!");
  }
}

function usePotion() {
  gameState.player.hp = Math.min(gameState.player.hpMax, gameState.player.hp + 40);
  showToast("🧪 Восстановлено +40 HP");
  renderBattle();
  renderHUD();
}

function enemyTurn() {
  if (gameState.enemyHp <= 0) return;
  const enemy = gameState.currentEnemy;
  const dmg = Math.max(4, enemy.str - gameState.player.def + Math.floor(Math.random() * 4));
  gameState.player.hp = Math.max(0, gameState.player.hp - dmg);
  showToast(`${enemy.name} нанес ${dmg} урона!`);

  if (gameState.player.hp <= 0) {
    gameState.player.hp = gameState.player.hpMax;
    gameState.player.losses += 1;
    showToast("Вы пали в бою! Здоровье восстановлено.");
    saveGame();
    renderHUD();
  }
  renderBattle();
}

// World Map
function renderWorld() {
  const container = document.getElementById('world-locations-grid');
  container.innerHTML = '';

  LOCATIONS.forEach(loc => {
    const locEl = document.createElement('div');
    locEl.className = 'game-card';
    locEl.innerHTML = `
      <div style="font-size: 36px; text-align: center;">${loc.art}</div>
      <div class="card-title" style="text-align: center; margin-top: 4px;">${loc.name}</div>
      <p style="font-size: 10px; color: var(--text-muted); text-align: center; margin-top: 4px;">${loc.desc}</p>
      <button class="btn-action" style="margin-top: 8px;" onclick="attackLocation('${loc.id}')">⚔ В бой</button>
    `;
    container.appendChild(locEl);
  });
}

function attackLocation(locId) {
  const loc = LOCATIONS.find(l => l.id === locId);
  if (loc) {
    switchTab('battle');
    prepareBattle({
      name: loc.enemy.name,
      art: loc.enemy.art,
      hp: loc.enemy.hp,
      hpMax: loc.enemy.hp,
      str: loc.enemy.str,
      def: loc.enemy.def,
      goldReward: loc.enemy.gold,
      xpReward: loc.enemy.xp
    });
  }
}

// Crafting
function renderCraft() {
  const container = document.getElementById('craft-recipes-list');
  container.innerHTML = '';

  CRAFT_RECIPES.forEach(rec => {
    const el = document.createElement('div');
    el.className = 'game-card';
    el.innerHTML = `
      <div style="font-size: 32px; text-align: center;">${rec.art}</div>
      <div class="card-title" style="text-align: center;">${rec.name}</div>
      <p style="font-size: 10px; color: var(--text-muted); text-align: center;">${rec.desc}</p>
      <button class="btn-action" style="margin-top: 8px; background: #27ae60; color: white;" onclick="craftItem('${rec.id}')">🔨 Скрафтить (${rec.cost} ◉)</button>
    `;
    container.appendChild(el);
  });
}

function craftItem(recId) {
  const rec = CRAFT_RECIPES.find(r => r.id === recId);
  if (rec && gameState.player.gold >= rec.cost) {
    gameState.player.gold -= rec.cost;
    gameState.cards.push({ ...rec.resultCard, id: "crafted_" + Date.now() });
    saveGame();
    renderHUD();
    showToast(`Вы успешно скрафтили: ${rec.name}!`);
  } else {
    showToast("Недостаточно золота!");
  }
}

// Deck View
function renderDeck() {
  const container = document.getElementById('deck-cards-list');
  container.innerHTML = '';

  const activeCards = gameState.cards.filter(c => gameState.deck.includes(c.id));

  activeCards.forEach(card => {
    const cardEl = document.createElement('div');
    cardEl.className = 'game-card';
    cardEl.innerHTML = `
      <div class="card-header">
        <span class="card-type">${card.type}</span>
        <span class="card-rarity rarity-${card.rarity}">${card.rarity}</span>
      </div>
      <div class="card-art">${card.art}</div>
      <div class="card-title">${card.name}</div>
      <div class="card-stats">
        <span class="stat-pill">❤ ${card.hp}</span>
        <span class="stat-pill">⚔ ${card.str}</span>
      </div>
    `;
    container.appendChild(cardEl);
  });
}

// Quests View
function renderQuests() {
  const container = document.getElementById('quests-list');
  container.innerHTML = '';

  gameState.quests.forEach(q => {
    const qEl = document.createElement('div');
    qEl.className = 'quest-card';
    qEl.innerHTML = `
      <div>
        <h3 style="color: var(--gold-light); font-size: 14px;">${q.title}</h3>
        <p style="font-size: 11px; color: var(--text-muted);">${q.desc}</p>
        <span style="font-size: 10px; color: var(--gold-accent);">Награда: +${q.rewardGold} ◉</span>
      </div>
      <button class="btn-action" ${q.claimed ? 'disabled style="opacity:0.5;"' : ''} onclick="claimQuest('${q.id}')">
        ${q.claimed ? 'Получено' : 'Забрать'}
      </button>
    `;
    container.appendChild(qEl);
  });
}

function claimQuest(qId) {
  const q = gameState.quests.find(quest => quest.id === qId);
  if (q && !q.claimed) {
    q.claimed = true;
    gameState.player.gold += q.rewardGold;
    saveGame();
    renderHUD();
    renderQuests();
    showToast(`Награда за квест получена! +${q.rewardGold} ◉`);
  }
}

// Chests View
function renderChests() {
  const container = document.getElementById('chests-grid');
  container.innerHTML = '';

  CHESTS.forEach(ch => {
    const el = document.createElement('div');
    el.className = 'game-card';
    el.innerHTML = `
      <div style="font-size: 40px; text-align: center;">${ch.art}</div>
      <div class="card-title" style="text-align: center;">${ch.name}</div>
      <button class="btn-action" style="margin-top: 8px;" onclick="openChest('${ch.id}')">Открыть (${ch.cost} ◉)</button>
    `;
    container.appendChild(el);
  });
}

function openChest(chId) {
  const ch = CHESTS.find(c => c.id === chId);
  if (ch && gameState.player.gold >= ch.cost) {
    playSfx('chest');
    gameState.player.gold -= ch.cost;
    const newCard = {
      id: "chest_" + Date.now(),
      name: "Древний Маг Арканума",
      type: "hero",
      rarity: ch.rarity,
      art: "🧙‍♂️",
      level: 1,
      hp: 100,
      str: 28,
      def: 12,
      abilityCost: 15,
      desc: "Получен из сундука."
    };
    gameState.cards.push(newCard);
    saveGame();
    renderHUD();
    showToast(`Из сундука выпала карта: ${newCard.name}!`);
  } else {
    showToast("Недостаточно золота!");
  }
}

// Audio Synthesizer (Web Audio API)
let audioCtx = null;
let soundEnabled = true;

function playSfx(type) {
  if (!soundEnabled) return;
  try {
    if (!audioCtx) {
      audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    }
    if (audioCtx.state === 'suspended') {
      audioCtx.resume();
    }

    const osc = audioCtx.createOscillator();
    const gain = audioCtx.createGain();
    osc.connect(gain);
    gain.connect(audioCtx.destination);

    const now = audioCtx.currentTime;

    if (type === 'click') {
      osc.type = 'triangle';
      osc.frequency.setValueAtTime(440, now);
      osc.frequency.exponentialRampToValueAtTime(880, now + 0.08);
      gain.gain.setValueAtTime(0.15, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.08);
      osc.start(now);
      osc.stop(now + 0.08);
    } else if (type === 'attack') {
      osc.type = 'sawtooth';
      osc.frequency.setValueAtTime(320, now);
      osc.frequency.exponentialRampToValueAtTime(80, now + 0.15);
      gain.gain.setValueAtTime(0.25, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.15);
      osc.start(now);
      osc.stop(now + 0.15);
    } else if (type === 'magic') {
      osc.type = 'sine';
      osc.frequency.setValueAtTime(523.25, now); // C5
      osc.frequency.setValueAtTime(659.25, now + 0.06); // E5
      osc.frequency.setValueAtTime(783.99, now + 0.12); // G5
      gain.gain.setValueAtTime(0.2, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.25);
      osc.start(now);
      osc.stop(now + 0.25);
    } else if (type === 'victory') {
      osc.type = 'square';
      osc.frequency.setValueAtTime(523.25, now);
      osc.frequency.setValueAtTime(659.25, now + 0.1);
      osc.frequency.setValueAtTime(783.99, now + 0.2);
      osc.frequency.setValueAtTime(1046.50, now + 0.3);
      gain.gain.setValueAtTime(0.2, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.5);
      osc.start(now);
      osc.stop(now + 0.5);
    } else if (type === 'chest') {
      osc.type = 'sine';
      osc.frequency.setValueAtTime(300, now);
      osc.frequency.exponentialRampToValueAtTime(1200, now + 0.3);
      gain.gain.setValueAtTime(0.3, now);
      gain.gain.exponentialRampToValueAtTime(0.01, now + 0.35);
      osc.start(now);
      osc.stop(now + 0.35);
    }
  } catch (e) {
    console.warn("Audio Context init error", e);
  }
}

function toggleSound() {
  soundEnabled = !soundEnabled;
  showToast(soundEnabled ? "🔊 Звуковые эффекты включены" : "🔇 Звуковые эффекты выключены");
}

// Fullscreen API Toggle
function toggleFullscreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen().catch(err => {
      showToast("Полноэкранный режим активирован!");
    });
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    }
  }
}

// Onboarding Modal Carousel System
const ONBOARDING_PAGES = [
  {
    title: "ДОБРО ПОЖАЛОВАТЬ В ARCANUM",
    sub: "Карточная Мистическая RPG",
    desc: "Собирайте легендарные карты, сражайтесь в дуэлях, исследуйте мир и открывайте мистические артефакты.",
    badge: "ЭПИЧЕСКОЕ ПРИКЛЮЧЕНИЕ",
    icon: "💎"
  },
  {
    title: "ONLINE PVP И БОЕВАЯ АРЕНА",
    sub: "Сражайтесь с игроками по всему миру",
    desc: "Испытайте силу вашей колоды в реальном времени. Повышайте MMR рейтинг и побеждайте в лиге!",
    badge: "РЕЙТИНГОВЫЕ БОИ",
    icon: "⚔️"
  },
  {
    title: "КЛИКЕР И АРКАДНЫЙ ТИР",
    sub: "Добывайте ресурсы быстрым касанием",
    desc: "Кликайте по Кристаллу Арканума, закупайте авто-майнеры и уничтожайте падающих монстров!",
    badge: "МИНИ-ИГРЫ & МАЙНИНГ",
    icon: "🎯"
  },
  {
    title: "КРАФТ И КОЛЛЕКЦИОНИРОВАНИЕ",
    sub: "Создавайте уникальные карты",
    desc: "Объединяйте ресурсы, создавайте эпическое снаряжение и пополняйте библиотеку!",
    badge: "МАСТЕР КРАФТА",
    icon: "🔨"
  }
];

let currentOnboardingIndex = 0;

function showOnboardingModal() {
  currentOnboardingIndex = 0;
  renderOnboardingSlide();
  const modal = document.getElementById('modal');
  if (modal) modal.classList.add('active');
}

function renderOnboardingSlide() {
  const p = ONBOARDING_PAGES[currentOnboardingIndex];
  const modalCard = document.querySelector('.modal-card');
  if (!modalCard) return;

  const dotsHtml = ONBOARDING_PAGES.map((_, idx) => 
    `<div class="onboarding-dot ${idx === currentOnboardingIndex ? 'active' : ''}"></div>`
  ).join('');

  modalCard.innerHTML = `
    <div class="onboarding-modal-content">
      <span class="onboarding-badge">${p.badge}</span>
      <div class="onboarding-icon-circle">${p.icon}</div>
      <h2 style="color: var(--gold-light); font-size: 18px;">${p.title}</h2>
      <h4 style="color: var(--gold-accent); font-size: 13px;">${p.sub}</h4>
      <p style="font-size: 12px; color: var(--text-muted); line-height: 1.5;">${p.desc}</p>
      
      <div class="onboarding-dots">${dotsHtml}</div>

      <div style="display: flex; gap: 8px; width: 100%; margin-top: 10px;">
        <button class="btn-action" style="flex: 1; background: rgba(255,255,255,0.1); color: var(--text-muted);" onclick="closeModal()">Пропустить</button>
        <button class="btn-action" style="flex: 2; background: var(--gold-accent); color: black; font-weight: 800;" onclick="nextOnboardingSlide()">
          ${currentOnboardingIndex === ONBOARDING_PAGES.length - 1 ? 'Начать игру!' : 'Далее ➔'}
        </button>
      </div>
    </div>
  `;
}

function nextOnboardingSlide() {
  playSfx('click');
  if (currentOnboardingIndex < ONBOARDING_PAGES.length - 1) {
    currentOnboardingIndex++;
    renderOnboardingSlide();
  } else {
    closeModal();
    showToast("Приключение начинается!");
  }
}

function exportPwaSaveJson() {
  const jsonStr = JSON.stringify(gameState, null, 2);
  navigator.clipboard.writeText(jsonStr).then(() => {
    showToast("📋 Сохранение скопировано в буфер обмена!");
  }).catch(() => {
    prompt("Скопируйте JSON вашего сохранения:", jsonStr);
  });
}

function importPwaSaveJson() {
  const jsonStr = prompt("Вставьте JSON сохранения для импорта:");
  if (!jsonStr) return;
  try {
    const parsed = JSON.parse(jsonStr);
    if (parsed.player) {
      gameState = { ...gameState, ...parsed };
      saveGame();
      renderHUD();
      showToast("✓ Сохранение импортировано!");
    } else {
      showToast("Неверный формат JSON");
    }
  } catch (e) {
    showToast("Ошибка чтения JSON файла");
  }
}

function closeModal() {
  const modal = document.getElementById('modal');
  if (modal) modal.classList.remove('active');
}

// Service Worker Registration
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('./sw.js')
      .then(reg => console.log('SW Registered', reg))
      .catch(err => console.error('SW Registration Failed', err));
  });
}

// Init & Spatial Navigation
window.addEventListener('DOMContentLoaded', () => {
  if (window.ArcanumEngine) {
    window.ArcanumEngine.boot();
  }
  loadGame();

  // Update status bar device badge
  const deviceBadge = document.getElementById('ar-device-badge');
  if (deviceBadge && window.ArcanumDeviceEngine) {
    const devType = window.ArcanumDeviceEngine.state.deviceType.toUpperCase();
    deviceBadge.innerText = `⚡ Device: ${devType}`;
  }

  // Keyboard / Gamepad Arrow Keys Spatial Focus Navigation
  window.addEventListener('keydown', (e) => {
    if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight'].includes(e.key)) {
      const focusable = Array.from(document.querySelectorAll('button, a, input, select, [tabindex="0"], ar-button'));
      if (focusable.length === 0) return;
      const index = focusable.indexOf(document.activeElement);
      if (index === -1) {
        focusable[0].focus();
      } else if (e.key === 'ArrowRight' || e.key === 'ArrowDown') {
        const next = focusable[(index + 1) % focusable.length];
        next.focus();
      } else if (e.key === 'ArrowLeft' || e.key === 'ArrowUp') {
        const prev = focusable[(index - 1 + focusable.length) % focusable.length];
        prev.focus();
      }
    }
  });

  switchTab('home');
});
