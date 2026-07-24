// Arcanum Card MMORPG - PWA Game Engine
const DEFAULT_PLAYER = {
  name: "Рыцарь Арканума",
  level: 1,
  xp: 0,
  xpMax: 100,
  gold: 150,
  hp: 100,
  hpMax: 100,
  mp: 50,
  mpMax: 50,
  str: 15,
  def: 10,
  wins: 0,
  losses: 0
};

const INITIAL_CARDS = [
  { id: "c1", name: "Пламенный Рыцарь", type: "hero", rarity: "epic", art: "⚔️", level: 1, hp: 80, str: 20, def: 8, abilityCost: 10, desc: "Мощный рыцарь с огненным клинком." },
  { id: "c2", name: "Лесной Дракон", type: "creature", rarity: "legendary", art: "🐉", level: 1, hp: 120, str: 28, def: 12, abilityCost: 15, desc: "Древний дракон, защищающий святилище." },
  { id: "c3", name: "Щит Порядка", type: "item", rarity: "rare", art: "🛡️", level: 1, hp: 30, str: 0, def: 18, abilityCost: 5, desc: "Благословенный щит Ордена." },
  { id: "c4", name: "Теневой Волк", type: "creature", rarity: "common", art: "🐺", level: 1, hp: 45, str: 14, def: 4, abilityCost: 0, desc: "Быстрый ночной хищник." },
  { id: "c5", name: "Кристалл Магии", type: "item", rarity: "epic", art: "💎", level: 1, hp: 10, str: 5, def: 5, abilityCost: 20, desc: "Восстанавливает 30 MP во время боя." }
];

const ENEMIES = [
  { name: "Гоблин-Берсерк", art: "👹", hp: 60, hpMax: 60, str: 10, def: 2, xpReward: 35, goldReward: 25 },
  { name: "Скелет-Воин", art: "💀", hp: 80, hpMax: 80, str: 14, def: 5, xpReward: 50, goldReward: 40 },
  { name: "Некромант", art: "🧙‍♂️", hp: 110, hpMax: 110, str: 18, def: 8, xpReward: 85, goldReward: 75 }
];

let gameState = {
  player: { ...DEFAULT_PLAYER },
  cards: [...INITIAL_CARDS],
  deck: ["c1", "c2", "c3"],
  inventory: [
    { id: "potion_hp", name: "Зелье Здоровья", count: 3, icon: "🧪", desc: "Восстанавливает 40 HP" }
  ],
  currentEnemy: null,
  enemyHp: 0
};

// LocalStorage Persistence
function loadGame() {
  try {
    const saved = localStorage.getItem('arcanum_pwa_save');
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
    localStorage.setItem('arcanum_pwa_save', JSON.stringify(gameState));
  } catch (e) {
    console.error("Failed to save state", e);
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
  if (tabName === 'shop') renderShop();
}

function renderCollection() {
  const container = document.getElementById('collection-grid');
  container.innerHTML = '';

  gameState.cards.forEach(card => {
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
        <span class="stat-pill">🛡 ${card.def}</span>
      </div>
    `;
    cardEl.onclick = () => openCardDetail(card);
    container.appendChild(cardEl);
  });
}

function openCardDetail(card) {
  const modal = document.getElementById('modal');
  modal.querySelector('.modal-card').innerHTML = `
    <h2 style="color: var(--gold-light); text-align: center;">${card.name}</h2>
    <div style="font-size: 48px; text-align: center; margin: 10px 0;">${card.art}</div>
    <p style="font-size: 12px; color: var(--text-muted); text-align: center;">${card.desc}</p>
    <div style="margin: 15px 0; font-size: 13px; display: flex; justify-content: space-around;">
      <span>❤ HP: ${card.hp}</span>
      <span>⚔ STR: ${card.str}</span>
      <span>🛡 DEF: ${card.def}</span>
    </div>
    <div style="display: flex; gap: 10px; margin-top: 15px;">
      <button class="btn-action" style="flex:1;" onclick="closeModal()">Закрыть</button>
      <button class="btn-action" style="flex:1; background: var(--purple-accent); color: white;" onclick="upgradeCard('${card.id}')">Улучшить (50 ◉)</button>
    </div>
  `;
  modal.classList.add('active');
}

function upgradeCard(cardId) {
  const card = gameState.cards.find(c => c.id === cardId);
  if (card && gameState.player.gold >= 50) {
    gameState.player.gold -= 50;
    card.level += 1;
    card.hp += 15;
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
function prepareBattle() {
  if (!gameState.currentEnemy || gameState.enemyHp <= 0) {
    const enemy = ENEMIES[Math.floor(Math.random() * ENEMIES.length)];
    gameState.currentEnemy = enemy;
    gameState.enemyHp = enemy.hpMax;
  }
  renderBattle();
}

function renderBattle() {
  const enemy = gameState.currentEnemy;
  document.getElementById('enemy-name').innerText = enemy.name;
  document.getElementById('enemy-art').innerText = enemy.art;
  document.getElementById('enemy-hp-text').innerText = `${gameState.enemyHp} / ${enemy.hpMax}`;
  document.getElementById('enemy-hp-fill').style.width = `${(gameState.enemyHp / enemy.hpMax) * 100}%`;

  document.getElementById('player-hp-text').innerText = `${gameState.player.hp} / ${gameState.player.hpMax}`;
  document.getElementById('player-hp-fill').style.width = `${(gameState.player.hp / gameState.player.hpMax) * 100}%`;
}

function playerAttack() {
  if (!gameState.currentEnemy || gameState.enemyHp <= 0) return;

  const dmg = Math.max(5, gameState.player.str - gameState.currentEnemy.def + Math.floor(Math.random() * 6));
  gameState.enemyHp = Math.max(0, gameState.enemyHp - dmg);
  showToast(`Вы нанесли ${dmg} урона!`);

  if (gameState.enemyHp <= 0) {
    const gold = gameState.currentEnemy.goldReward;
    const xp = gameState.currentEnemy.xpReward;
    gameState.player.gold += gold;
    gameState.player.xp += xp;
    gameState.player.wins += 1;

    showToast(`Победа! +${gold} ◉ Золота, +${xp} XP`);
    saveGame();
    renderHUD();
    setTimeout(prepareBattle, 1500);
  } else {
    setTimeout(enemyTurn, 800);
  }
  renderBattle();
}

function enemyTurn() {
  if (gameState.enemyHp <= 0) return;
  const enemy = gameState.currentEnemy;
  const dmg = Math.max(3, enemy.str - gameState.player.def + Math.floor(Math.random() * 4));
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

// Shop Engine
function renderShop() {
  const container = document.getElementById('shop-items');
  container.innerHTML = `
    <div class="game-card" style="min-height: 140px;">
      <div style="font-size: 32px; text-align: center;">🎴</div>
      <div class="card-title" style="text-align: center;">Случайный Бустерпак</div>
      <p style="font-size: 11px; color: var(--text-muted); text-align: center;">Содержит редкую или легендарную карту</p>
      <button class="btn-action" style="margin-top: 8px;" onclick="buyBooster()">Купить (100 ◉)</button>
    </div>
    <div class="game-card" style="min-height: 140px;">
      <div style="font-size: 32px; text-align: center;">🧪</div>
      <div class="card-title" style="text-align: center;">Зелье Лечения</div>
      <p style="font-size: 11px; color: var(--text-muted); text-align: center;">Восстанавливает 50 HP</p>
      <button class="btn-action" style="margin-top: 8px;" onclick="buyPotion()">Купить (30 ◉)</button>
    </div>
  `;
}

function buyBooster() {
  if (gameState.player.gold >= 100) {
    gameState.player.gold -= 100;
    const newCard = {
      id: "c_" + Date.now(),
      name: "Древний Маг",
      type: "hero",
      rarity: "legendary",
      art: "🧙‍♀️",
      level: 1,
      hp: 90,
      str: 26,
      def: 10,
      abilityCost: 15,
      desc: "Получен из легендарного бустерпака."
    };
    gameState.cards.push(newCard);
    saveGame();
    renderHUD();
    showToast("Вы получили Легендарную карту: Древний Маг!");
  } else {
    showToast("Недостаточно золота!");
  }
}

function buyPotion() {
  if (gameState.player.gold >= 30) {
    gameState.player.gold -= 30;
    gameState.player.hp = Math.min(gameState.player.hpMax, gameState.player.hp + 50);
    saveGame();
    renderHUD();
    showToast("Вы выпили зелье! +50 HP");
  } else {
    showToast("Недостаточно золота!");
  }
}

// Service Worker Registration
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('./sw.js')
      .then(reg => console.log('SW Registered', reg))
      .catch(err => console.error('SW Registration Failed', err));
  });
}

// Init
window.addEventListener('DOMContentLoaded', () => {
  loadGame();
  switchTab('home');
});
