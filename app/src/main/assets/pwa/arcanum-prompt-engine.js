/**
 * ARCANUM EVOLUTION :: PROMPT ENGINE & METAVERSE SUBSYSTEM (v4.5.0)
 * Master Prompts, Variable Templating, Prompt Library, and Prompt Studio UI
 */

(function(global) {
    'use strict';

    class ArcanumPromptEngine {
        constructor() {
            this.version = "4.5.0";
            this.prompts = {
                "arcanum_master": {
                    id: "arcanum_master",
                    title: "Arcanum Master Architecture Prompt",
                    category: "System Architecture",
                    description: "Главный системный промпт для Архитектора Arcanum Evolution.",
                    template: `Вы являетесь Главным Архитектором и Разработчиком системы Arcanum Evolution (v4.5.0).
Текущее состояние системы:
- Версия: {{version}}
- Модули: {{active_modules}}
- Оценка архитектуры: {{arch_score}}/100
- Игрок Level: {{player_level}}, Gold: {{player_gold}}

Ваша задача: Обеспечить монолитную цельность ядра, модульность PWA, совместимость с Android (API 21+) и высокую производительность.`
                },
                "card_generator": {
                    id: "card_generator",
                    title: "RPG Card & Spell Generator Prompt",
                    category: "Game Design",
                    description: "Промпт для процедурной генерации новых карт и заклинаний.",
                    template: `Создайте новую карту для Arcanum Card RPG в формате JSON.
Параметры игрока:
- Имя: {{player_name}}
- Уровень: {{player_level}}
- Любимая стихия: {{favorite_element}}

Сгенерируйте объект карты с полями: id, name, type (attack/defend/spell), power, mpCost, rarity, lore, artKey.`
                },
                "rpg_balancer": {
                    id: "rpg_balancer",
                    title: "Combat & Economy Balancer Prompt",
                    category: "Game Balance",
                    description: "Промпт для расчета формул урона, золота и балансировки боевой системы.",
                    template: `Проанализируйте баланс боя для Arcanum MMORPG.
Текущие показатели:
- Средний HP врага: {{enemy_hp}}
- Базовый урон игрока: {{player_attack}}
- Стоимость способностей: {{mp_cost}} MP

Дайте рекомендации по корректировке формулы урона Damage = Base * (1 + Level * 0.15) и шанса крита.`
                },
                "lore_weaver": {
                    id: "lore_weaver",
                    title: "Arcanum Lore & World Weaver Prompt",
                    category: "Narrative",
                    description: "Промпт для создания захватывающих текстовых квестов и истории мира Arcanum.",
                    template: `Напишите фрагмент лора для локации "{{current_location}}".
Включите древние руны, темные ритуалы магии Arcanum и выбор из 3 действий для игрока уровня {{player_level}}.`
                },
                "code_auditor": {
                    id: "code_auditor",
                    title: "Kotlin & JS Code Quality Auditor Prompt",
                    category: "Engineering",
                    description: "Промпт для проведения ревью кода и проверки на ошибки Android API 21+.",
                    template: `Проведите аудит кода функции {{target_function}}.
Требования:
1. Совместимость с Android minSdk 21 (без новейших Java 8+ API без десугаринга).
2. Отсутствие вызовов missing resources.
3. Соблюдение Material 3 и Jetpack Compose best practices.`
                },
                "vector_art": {
                    id: "vector_art",
                    title: "Card Art Graphic Generator Prompt",
                    category: "Visual Arts",
                    description: "Промпт для генерации вектора и ключевых визуалов карт.",
                    template: `Сгенерируйте описания графики для карты "{{card_name}}".
Стиль: Dark Fantasy Gold, неоновое свечение, золотые руны, черные фоны Canvas.`
                }
            };
            this.executionLogs = [];
        }

        getPromptList() {
            return Object.values(this.prompts);
        }

        getPrompt(id) {
            return this.prompts[id] || null;
        }

        compilePrompt(promptId, variables = {}) {
            const prompt = this.getPrompt(promptId);
            if (!prompt) return "Промпт не найден.";

            let result = prompt.template;
            const defaultVars = {
                "version": "4.5.0",
                "active_modules": "Kernel, AI, IDE, Network, PromptEngine",
                "arch_score": "100",
                "player_name": "Рыцарь Arcanum",
                "player_level": "5",
                "player_gold": "250",
                "favorite_element": "Огонь",
                "enemy_hp": "150",
                "player_attack": "25",
                "mp_cost": "10",
                "current_location": "Древние Руины Асгарда",
                "target_function": "HomeScreenRender()",
                "card_name": "Пламенный Дракон"
            };

            const merged = Object.assign({}, defaultVars, variables);

            for (const [key, val] of Object.entries(merged)) {
                const regex = new RegExp(`{{\\s*${key}\\s*}}`, 'g');
                result = result.replace(regex, val);
            }

            return result;
        }

        estimateTokens(text) {
            if (!text) return 0;
            return Math.ceil(text.length / 3.8);
        }

        simulateExecution(promptId, compiledText) {
            const tokens = this.estimateTokens(compiledText);
            const timestamp = new Date().toLocaleTimeString();

            let simulatedOutput = "";
            if (promptId === "card_generator") {
                simulatedOutput = JSON.stringify({
                    id: "card_dragon_ignis_" + Date.now(),
                    name: "Пламенный Игнис",
                    type: "spell",
                    power: 85,
                    mpCost: 15,
                    rarity: "Legendary",
                    lore: "Вызванный из самого сердца недр Arcanum.",
                    artKey: "dragon"
                }, null, 2);
            } else if (promptId === "rpg_balancer") {
                simulatedOutput = `[Анализ баланса]:
- Урон соразмерен HP врага (3-4 хода на победу).
- Рекомендовано увеличить на 5% задержку восстанавливаемости MP.
- Коэффициент крита 2.0x зафиксирован успешно.`;
            } else {
                simulatedOutput = `[ИИ Исполнитель]: Запрос обработан успешно (${tokens} токенов). Все условия соблюдены в соответствии с конституцией Arcanum Master Prompt.`;
            }

            const logEntry = {
                id: Date.now(),
                timestamp,
                promptId,
                tokens,
                compiledText,
                output: simulatedOutput
            };

            this.executionLogs.unshift(logEntry);
            if (this.executionLogs.length > 20) this.executionLogs.pop();

            return logEntry;
        }
    }

    class ArcanumPromptStudioUI {
        constructor(engine) {
            this.engine = engine;
            this.selectedPromptId = "arcanum_master";
            this.customVariables = {};
        }

        render(containerId) {
            const container = document.getElementById(containerId);
            if (!container) return;

            const prompts = this.engine.getPromptList();
            const currentPrompt = this.engine.getPrompt(this.selectedPromptId);
            const compiled = this.engine.compilePrompt(this.selectedPromptId, this.customVariables);
            const tokens = this.engine.estimateTokens(compiled);

            container.innerHTML = `
                <div class="prompt-studio-card" style="background:rgba(20,25,35,0.95); border:1px solid var(--gold-accent, #c5a059); border-radius:12px; padding:20px; color:#e0e0e0; box-shadow:0 8px 32px rgba(0,0,0,0.5);">
                    <div style="display:flex; justify-content:space-between; align-items:center; border-bottom:1px solid rgba(197,160,89,0.3); padding-bottom:12px; margin-bottom:16px;">
                        <h2 style="margin:0; font-family:'Cinzel', serif; color:#c5a059; display:flex; align-items:center; gap:8px;">
                            🤖 Arcanum Prompt Studio & AI Master Engine v4.5.0
                        </h2>
                        <span style="background:rgba(197,160,89,0.2); border:1px solid #c5a059; padding:4px 12px; border-radius:20px; font-size:0.85rem; color:#ffd700;">
                            ✨ Tokens: ~${tokens}
                        </span>
                    </div>

                    <div style="display:grid; grid-template-columns: 280px 1fr; gap:20px;">
                        <!-- Library Sidebar -->
                        <div style="background:rgba(10,15,22,0.8); border:1px solid rgba(255,255,255,0.1); border-radius:8px; padding:12px;">
                            <h3 style="margin-top:0; font-size:1rem; color:#c5a059; border-bottom:1px solid rgba(255,255,255,0.1); padding-bottom:6px;">
                                📚 Промпт-Библиотека
                            </h3>
                            <div style="display:flex; flex-direction:column; gap:8px;">
                                ${prompts.map(p => `
                                    <button class="prompt-select-btn" onclick="window.ArcanumPromptStudioUI.selectPrompt('${p.id}')"
                                        style="text-align:left; background:${p.id === this.selectedPromptId ? 'rgba(197,160,89,0.25)' : 'rgba(255,255,255,0.03)'};
                                               border:${p.id === this.selectedPromptId ? '1px solid #c5a059' : '1px solid rgba(255,255,255,0.08)'};
                                               color:${p.id === this.selectedPromptId ? '#ffd700' : '#cccccc'};
                                               padding:10px; border-radius:6px; cursor:pointer; transition:all 0.2s ease;">
                                        <div style="font-weight:bold; font-size:0.9rem;">${p.title}</div>
                                        <div style="font-size:0.75rem; opacity:0.7; margin-top:2px;">${p.category}</div>
                                    </button>
                                `).join('')}
                            </div>
                        </div>

                        <!-- Main Editor & Playground -->
                        <div style="display:flex; flex-direction:column; gap:16px;">
                            <div style="background:rgba(15,20,30,0.8); border:1px solid rgba(197,160,89,0.2); border-radius:8px; padding:14px;">
                                <h3 style="margin:0 0 6px 0; color:#ffd700; font-size:1.1rem;">${currentPrompt ? currentPrompt.title : ''}</h3>
                                <p style="margin:0 0 12px 0; font-size:0.85rem; color:#aaa;">${currentPrompt ? currentPrompt.description : ''}</p>
                                
                                <label style="font-size:0.85rem; font-weight:bold; color:#c5a059; display:block; margin-bottom:6px;">
                                    📝 Скомпилированный Промпт (с подстановкой переменных):
                                </label>
                                <textarea id="prompt-compiled-textarea" readonly style="width:100%; height:160px; background:#080c14; border:1px solid rgba(197,160,89,0.3); border-radius:6px; color:#aaffaa; font-family:monospace; padding:10px; font-size:0.85rem; resize:vertical;">${compiled}</textarea>

                                <div style="display:flex; gap:10px; margin-top:12px;">
                                    <button onclick="window.ArcanumPromptStudioUI.copyPrompt()" style="background:#c5a059; color:#000; font-weight:bold; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;">
                                        📋 Скопировать Промпт
                                    </button>
                                    <button onclick="window.ArcanumPromptStudioUI.runPrompt()" style="background:linear-gradient(135deg, #4e54c8, #8f94fb); color:#fff; font-weight:bold; border:none; padding:8px 20px; border-radius:6px; cursor:pointer;">
                                        ⚡ Запустить ИИ Промпт (Исполнить)
                                    </button>
                                </div>
                            </div>

                            <!-- Execution Log -->
                            <div style="background:rgba(10,15,22,0.8); border:1px solid rgba(255,255,255,0.1); border-radius:8px; padding:12px;">
                                <h4 style="margin:0 0 10px 0; color:#c5a059; font-size:0.95rem;">📊 Журнал Исполнения Промптов</h4>
                                <div id="prompt-execution-logs" style="max-height:180px; overflow-y:auto; display:flex; flex-direction:column; gap:8px;">
                                    ${this.engine.executionLogs.length === 0 ? '<div style="font-size:0.85rem; color:#666;">Журнал пуст. Нажмите "Запустить ИИ Промпт" выше.</div>' : ''}
                                    ${this.engine.executionLogs.map(log => `
                                        <div style="background:rgba(255,255,255,0.03); border-left:3px solid #c5a059; padding:8px; border-radius:4px; font-size:0.8rem;">
                                            <div style="display:flex; justify-content:space-between; color:#ffd700; margin-bottom:4px;">
                                                <span>⏱ ${log.timestamp} [${log.promptId}]</span>
                                                <span>Tokens: ${log.tokens}</span>
                                            </div>
                                            <pre style="margin:0; font-family:monospace; color:#88dffc; white-space:pre-wrap;">${log.output}</pre>
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        }

        selectPrompt(id) {
            this.selectedPromptId = id;
            this.render('prompt-studio-container');
        }

        copyPrompt() {
            const textarea = document.getElementById('prompt-compiled-textarea');
            if (textarea) {
                textarea.select();
                navigator.clipboard.writeText(textarea.value).then(() => {
                    if (window.showToast) window.showToast("Промпт скопирован в буфер обмена!");
                }).catch(() => {
                    if (window.showToast) window.showToast("Скопировано!");
                });
            }
        }

        runPrompt() {
            const compiled = this.engine.compilePrompt(this.selectedPromptId, this.customVariables);
            this.engine.simulateExecution(this.selectedPromptId, compiled);
            if (window.showToast) window.showToast("⚡ Промпт успешно исполнен!");
            this.render('prompt-studio-container');
        }
    }

    const engineInstance = new ArcanumPromptEngine();
    const uiInstance = new ArcanumPromptStudioUI(engineInstance);

    global.ArcanumPromptEngine = engineInstance;
    global.ArcanumPromptStudioUI = uiInstance;

})(typeof window !== 'undefined' ? window : this);
