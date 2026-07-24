package com.example.data

object DefaultData {
    val initialCards = listOf(
        CardEntity(
            id = "ARC-0001-WARRIOR", name = "Воин", type = "hero", rarity = "rare",
            hp = 120, mp = 20, str = 18, def = 14, level = 5, xp = 340,
            desc = "Закалённый в битвах воин.", art = "warrior",
            abilityName = "Удар щитом", abilityType = "damage", abilityValue = 25, abilityCost = 15, abilityDesc = "Наносит 25 урона"
        ),
        CardEntity(
            id = "ARC-0002-ARCHER", name = "Лучник", type = "hero", rarity = "uncommon",
            hp = 85, mp = 40, str = 14, def = 8, level = 4, xp = 210,
            desc = "Мастер дальнего боя.", art = "archer",
            abilityName = "Двойной выстрел", abilityType = "damage", abilityValue = 20, abilityCost = 12, abilityDesc = "Два удара по 20"
        ),
        CardEntity(
            id = "ARC-0003-MAGE", name = "Маг", type = "hero", rarity = "epic",
            hp = 70, mp = 150, str = 8, def = 6, level = 6, xp = 480,
            desc = "Повелитель стихий.", art = "mage",
            abilityName = "Огненный шар", abilityType = "damage", abilityValue = 45, abilityCost = 25, abilityDesc = "Мощная магия огня"
        ),
        CardEntity(
            id = "ARC-0004-NECRO", name = "Некромант", type = "hero", rarity = "legendary",
            hp = 90, mp = 120, str = 12, def = 10, level = 8, xp = 720,
            desc = "Тёмный жрец.", art = "necro",
            abilityName = "Высос души", abilityType = "drain", abilityValue = 30, abilityCost = 20, abilityDesc = "Урон + лечение"
        ),
        CardEntity(
            id = "ARC-0005-DRAGON", name = "Дракон", type = "creature", rarity = "mythic",
            hp = 250, mp = 80, str = 28, def = 22, level = 12, xp = 1500,
            desc = "Древний крылатый ужас.", art = "dragon",
            abilityName = "Пламя", abilityType = "damage", abilityValue = 70, abilityCost = 40, abilityDesc = "Испепеляющее пламя"
        ),
        CardEntity(
            id = "ARC-0006-GOBLIN", name = "Гоблин", type = "creature", rarity = "common",
            hp = 40, mp = 10, str = 8, def = 4, level = 1, xp = 20,
            desc = "Хитрый обитатель пещер.", art = "goblin",
            abilityName = "Укус", abilityType = "damage", abilityValue = 12, abilityCost = 5, abilityDesc = "Быстрая атака"
        ),
        CardEntity(
            id = "ARC-0007-SWORD", name = "Меч", type = "item", rarity = "rare",
            hp = 0, mp = 0, str = 15, def = 0, level = 3, xp = 0,
            desc = "Заговорённый клинок.", art = "sword",
            abilityName = "Рассечение", abilityType = "damage", abilityValue = 30, abilityCost = 10, abilityDesc = "Широкий удар"
        ),
        CardEntity(
            id = "ARC-0008-SHIELD", name = "Щит", type = "item", rarity = "uncommon",
            hp = 0, mp = 0, str = 0, def = 12, level = 2, xp = 0,
            desc = "Крепкий дубовый щит.", art = "shield",
            abilityName = "Бафф защиты", abilityType = "buff", abilityValue = 8, abilityCost = 10, abilityDesc = "+8 защиты на ход"
        ),
        CardEntity(
            id = "ARC-0009-POTION", name = "Зелье", type = "item", rarity = "common",
            hp = 50, mp = 0, str = 0, def = 0, level = 1, xp = 0,
            desc = "Алый эликсир.", art = "potion",
            abilityName = "Исцеление", abilityType = "heal", abilityValue = 40, abilityCost = 8, abilityDesc = "+40 HP"
        ),
        CardEntity(
            id = "ARC-0010-RING", name = "Кольцо", type = "item", rarity = "epic",
            hp = 0, mp = 30, str = 5, def = 5, level = 4, xp = 0,
            desc = "Древнее кольцо с сапфиром.", art = "ring",
            abilityName = "Маг. вспышка", abilityType = "damage", abilityValue = 35, abilityCost = 18, abilityDesc = "Магический удар"
        ),
        CardEntity(
            id = "ARC-0011-CRYSTAL", name = "Кристалл", type = "item", rarity = "legendary",
            hp = 0, mp = 100, str = 0, def = 0, level = 7, xp = 0,
            desc = "Радужный кристалл.", art = "crystal",
            abilityName = "Прилив маны", abilityType = "mana", abilityValue = 30, abilityCost = 0, abilityDesc = "+30 MP"
        ),
        CardEntity(
            id = "ARC-0012-PET", name = "Питомец", type = "creature", rarity = "rare",
            hp = 60, mp = 20, str = 10, def = 8, level = 3, xp = 120,
            desc = "Верный грифон.", art = "pet",
            abilityName = "Когти", abilityType = "damage", abilityValue = 22, abilityCost = 12, abilityDesc = "Атака когтями"
        )
    )

    val initialInventory = listOf(
        InventoryEntity(id = "inv-potion", name = "Зелье здоровья", count = 5, art = "potion", useType = "heal", value = 30),
        InventoryEntity(id = "inv-mana", name = "Зелье маны", count = 3, art = "potion", useType = "mana", value = 40),
        InventoryEntity(id = "inv-gold", name = "Золото", count = 100, art = "crystal", useType = null, value = 0),
        InventoryEntity(id = "inv-key", name = "Старый ключ", count = 1, art = "ring", useType = null, value = 0),
        InventoryEntity(id = "inv-scroll", name = "Свиток огня", count = 2, art = "crystal", useType = "damage", value = 40),
        InventoryEntity(id = "inv-gem", name = "Самоцвет", count = 3, art = "crystal", useType = null, value = 0),
        InventoryEntity(id = "inv-food", name = "Провизия", count = 8, art = "potion", useType = "heal", value = 15),
        InventoryEntity(id = "inv-bomb", name = "Бомба", count = 2, art = "crystal", useType = "damage", value = 60)
    )

    val initialQuests = listOf(
        QuestEntity("q_battle1", "Первая кровь", "Победи в 1 сражении", "questBattle", 1, "battlesWon", 50, 30),
        QuestEntity("q_battle5", "Опытный воин", "Победи в 5 сражениях", "questTrophy", 5, "battlesWon", 200, 100),
        QuestEntity("q_battle15", "Ветеран арены", "Победи в 15 сражениях", "questCrown", 15, "battlesWon", 500, 300),
        QuestEntity("q_card1", "Коллекционер", "Собери 15 карт", "questCards", 15, "totalCards", 100, 80),
        QuestEntity("q_card25", "Библиотекарь", "Собери 25 карт", "questBook", 25, "totalCards", 300, 200),
        QuestEntity("q_craft1", "Алхимик", "Создай 1 карту через крафт", "questPotion", 1, "crafted", 80, 50),
        QuestEntity("q_craft5", "Мастер крафта", "Создай 5 карт через крафт", "questHammer", 5, "crafted", 250, 150),
        QuestEntity("q_gold", "Богач", "Накопи 500 золота", "questGold", 500, "gold", 150, 0),
        QuestEntity("q_level5", "Пятый уровень", "Достигни 5 уровня", "questStar", 5, "level", 0, 200),
        QuestEntity("q_explore", "Исследователь", "Посети 6 локаций", "questMap", 6, "visitedLocations", 200, 150),
        QuestEntity("q_chest", "Охотник за сокровищами", "Открой 3 сундука", "questChest", 3, "chestsOpened", 150, 100),
        QuestEntity("q_deck", "Мастер колоды", "Собери полную колоду из 5 карт", "questDeck", 5, "deckSize", 100, 80)
    )

    val initialAchievements = listOf(
        AchievementEntity("first_card", "Первые шаги", "Создай свою первую карту", "editor"),
        AchievementEntity("collector_15", "Коллекционер", "Собери 15 карт", "collection"),
        AchievementEntity("first_blood", "Первая кровь", "Победи в 1 сражении", "battle"),
        AchievementEntity("warrior_10", "Ветеран", "Победи в 10 сражениях", "achievements"),
        AchievementEntity("rich", "Богач", "Накопи 500 золота", "shop"),
        AchievementEntity("level_5", "Опытный", "Достигни 5 уровня", "quests"),
        AchievementEntity("explorer", "Исследователь", "Посети 6 локаций", "world"),
        AchievementEntity("alchemist", "Алхимик", "Создай 3 карты крафтом", "craft"),
        AchievementEntity("crit_master", "Мастер критов", "Нанеси 5 критов", "battle"),
        AchievementEntity("mythic", "Легенда", "Получи мифическую карту", "chest"),
        AchievementEntity("chest_master", "Охотник за сундуками", "Открой 5 сундуков", "chest"),
        AchievementEntity("deck_master", "Мастер колоды", "Собери 5 карт в колоде", "deck")
    )
}
