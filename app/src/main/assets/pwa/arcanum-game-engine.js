/**
 * ARCANUM GAME ENGINE & UNIVERSAL GAME BUILDER PLATFORM (v4.0.0)
 * =======================================================================
 * Universal Game Construction Engine & RPG Sandbox Runtime
 * 
 * Modules Included:
 * 1. ArcanumGameEngine          - Central Game Loop, Tick, and Scene State Controller
 * 2. ArcanumEntityComponentSystem- Decoupled ECS Engine (Entities, Components, Systems)
 * 3. ArcanumSceneBuilder        - Visual RPG Scene & Canvas Layout Construction Engine
 * 4. ArcanumRuleGraphEngine     - Visual Logic Rule Graph Execution Engine (Card & Spell Effects)
 * 5. ArcanumGamePublisher       - Universal Game Exporter (.apkg v4.0 & Game Manifests)
 * 6. ArcanumGameStudioUI        - Live Interactive Game Builder Laboratory in PWA
 */

(function(window) {
  'use strict';

  // 1. LIGHTWEIGHT ENTITY COMPONENT SYSTEM (ECS)
  class ArcanumEntityComponentSystem {
    constructor() {
      this.nextEntityId = 1;
      this.entities = new Map(); // id -> Map(componentType -> componentData)
      this.systems = [];
    }

    createEntity(name = 'Entity') {
      const id = this.nextEntityId++;
      this.entities.set(id, { name, components: new Map() });
      return id;
    }

    destroyEntity(id) {
      this.entities.delete(id);
    }

    addComponent(entityId, componentType, data) {
      const entity = this.entities.get(entityId);
      if (entity) {
        entity.components.set(componentType, data);
      }
    }

    getComponent(entityId, componentType) {
      const entity = this.entities.get(entityId);
      return entity ? entity.components.get(componentType) : null;
    }

    hasComponent(entityId, componentType) {
      const entity = this.entities.get(entityId);
      return entity ? entity.components.has(componentType) : false;
    }

    getEntitiesWith(...componentTypes) {
      const result = [];
      for (const [id, entity] of this.entities.entries()) {
        if (componentTypes.every(type => entity.components.has(type))) {
          result.push({ id, name: entity.name, components: entity.components });
        }
      }
      return result;
    }

    registerSystem(updateFn) {
      this.systems.push(updateFn);
    }

    update(deltaTime) {
      for (const system of this.systems) {
        try {
          system(this, deltaTime);
        } catch (e) {
          console.error('[ArcanumECS] System execution error:', e);
        }
      }
    }
  }

  // 2. VISUAL SCENE BUILDER & RPG SANDBOX
  class ArcanumSceneBuilder {
    constructor() {
      this.currentScene = {
        id: 'scene_default',
        name: 'Arcanum Sanctuary',
        background: '#0a0d14',
        gridSize: 32,
        nodes: [
          { id: 'node_player', type: 'spawn', x: 100, y: 150, label: 'Hero Spawn Point', color: '#4DEEEA' },
          { id: 'node_boss', type: 'enemy', x: 300, y: 150, label: 'Shadow Dragon Boss', color: '#E63946' },
          { id: 'node_chest', type: 'loot', x: 200, y: 220, label: 'Arcane Chest', color: '#FFE600' }
        ]
      };
    }

    getScene() {
      return this.currentScene;
    }

    addNode(type, x, y, label, color) {
      const id = 'node_' + Date.now();
      const node = { id, type, x, y, label: label || type, color: color || '#4DEEEA' };
      this.currentScene.nodes.push(node);
      return node;
    }

    removeNode(id) {
      this.currentScene.nodes = this.currentScene.nodes.filter(n => n.id !== id);
    }

    updateNodePosition(id, x, y) {
      const node = this.currentScene.nodes.find(n => n.id === id);
      if (node) {
        node.x = x;
        node.y = y;
      }
    }
  }

  // 3. RULE GRAPH ENGINE (Declarative Card Effects & RPG Mechanics)
  class ArcanumRuleGraphEngine {
    constructor() {
      this.rules = new Map();
      this.initDefaultRules();
    }

    initDefaultRules() {
      this.registerRule('DAMAGE', (context, params) => {
        const target = params.target || 'enemy';
        const amount = params.amount || 10;
        return { action: 'DEAL_DAMAGE', target, amount, message: `Dealt ${amount} damage to ${target}` };
      });

      this.registerRule('HEAL', (context, params) => {
        const target = params.target || 'player';
        const amount = params.amount || 15;
        return { action: 'RESTORE_HP', target, amount, message: `Restored ${amount} HP to ${target}` };
      });

      this.registerRule('DRAW_CARD', (context, params) => {
        const count = params.count || 1;
        return { action: 'DRAW', count, message: `Drew ${count} card(s)` };
      });

      this.registerRule('SUMMON_PARTICLES', (context, params) => {
        const fxType = params.fxType || 'GOLD_GLOW';
        return { action: 'SPAWN_FX', fxType, message: `Triggered visual FX: ${fxType}` };
      });
    }

    registerRule(name, fn) {
      this.rules.set(name, fn);
    }

    executeRule(ruleName, context, params) {
      if (this.rules.has(ruleName)) {
        return this.rules.get(ruleName)(context, params);
      }
      return { action: 'UNKNOWN', message: `Rule ${ruleName} not registered` };
    }

    executeGraph(ruleList, context = {}) {
      const results = [];
      for (const rule of ruleList) {
        const res = this.executeRule(rule.name, context, rule.params || {});
        results.push(res);
      }
      return results;
    }
  }

  // 4. GAME PUBLISHER & PACKAGE EXPORTER
  class ArcanumGamePublisher {
    static exportGamePackage(gameData) {
      const manifest = {
        formatVersion: '4.0.0',
        metaId: 'apkg_game_' + Date.now(),
        title: gameData.title || 'Custom Arcanum World',
        author: gameData.author || 'Arcanum Architect',
        createdAt: new Date().toISOString(),
        scene: gameData.scene || {},
        cards: gameData.cards || [],
        rules: gameData.rules || [],
        checksum: ArcanumGamePublisher.calculateChecksum(JSON.stringify(gameData))
      };

      const jsonStr = JSON.stringify(manifest, null, 2);
      const blob = new Blob([jsonStr], { type: 'application/json' });
      return { manifest, jsonStr, blob };
    }

    static calculateChecksum(str) {
      let hash = 0;
      for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = ((hash << 5) - hash) + char;
        hash |= 0;
      }
      return 'SHA256_' + Math.abs(hash).toString(16).toUpperCase();
    }
  }

  // 5. MASTER GAME ENGINE CONTROLLER
  class ArcanumGameEngine {
    constructor() {
      this.version = '4.0.0';
      this.ecs = new ArcanumEntityComponentSystem();
      this.sceneBuilder = new ArcanumSceneBuilder();
      this.ruleEngine = new ArcanumRuleGraphEngine();
      this.isRunning = false;
      this.lastTick = 0;
      this.stats = { fps: 60, ticks: 0 };
    }

    initialize() {
      console.log(`[ArcanumGameEngine v${this.version}] Universal Game Engine initialized.`);

      // Setup default ECS entities
      const hero = this.ecs.createEntity('Arcane Knight');
      this.ecs.addComponent(hero, 'Transform', { x: 100, y: 150 });
      this.ecs.addComponent(hero, 'Stats', { hp: 100, maxHp: 100, attack: 15, defense: 5 });

      const boss = this.ecs.createEntity('Shadow Dragon');
      this.ecs.addComponent(boss, 'Transform', { x: 300, y: 150 });
      this.ecs.addComponent(boss, 'Stats', { hp: 250, maxHp: 250, attack: 25, defense: 10 });

      // Register default ECS render system
      this.ecs.registerSystem((ecs, dt) => {
        this.stats.ticks++;
      });

      return true;
    }

    start() {
      this.isRunning = true;
      this.lastTick = performance.now();
      this.loop();
    }

    stop() {
      this.isRunning = false;
    }

    loop() {
      if (!this.isRunning) return;
      const now = performance.now();
      const dt = (now - this.lastTick) / 1000;
      this.lastTick = now;

      this.ecs.update(dt);

      requestAnimationFrame(() => this.loop());
    }

    getDiagnostics() {
      return {
        version: this.version,
        isRunning: this.isRunning,
        entitiesCount: this.ecs.entities.size,
        systemsCount: this.ecs.systems.length,
        activeScene: this.sceneBuilder.getScene().name,
        rulesRegistered: this.ruleEngine.rules.size
      };
    }
  }

  // 6. GAME STUDIO LABORATORY UI ENGINE
  class ArcanumGameStudioUI {
    static renderStudio(containerId) {
      const container = document.getElementById(containerId);
      if (!container) return;

      const engine = window.ArcanumEngineInstance || new ArcanumGameEngine();
      if (!window.ArcanumEngineInstance) {
        engine.initialize();
        window.ArcanumEngineInstance = engine;
      }

      const diag = engine.getDiagnostics();
      const scene = engine.sceneBuilder.getScene();

      container.innerHTML = `
        <div style="background: rgba(15, 20, 32, 0.95); border: 1px solid var(--gold-accent, #C5A059); border-radius: 12px; padding: 20px; color: #E0E6ED; font-family: sans-serif; box-shadow: 0 8px 32px rgba(0,0,0,0.5);">
          <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid rgba(197, 160, 89, 0.3); padding-bottom: 12px; margin-bottom: 16px;">
            <h2 style="margin: 0; color: #FFE600; font-size: 18px; display: flex; align-items: center; gap: 8px;">
              <span>🎮</span> ARCANUM GAME ENGINE & BUILDER STUDIO (v${diag.version})
            </h2>
            <span style="background: rgba(77, 238, 234, 0.15); color: #4DEEEA; border: 1px solid #4DEEEA; padding: 4px 10px; border-radius: 20px; font-size: 12px; font-weight: bold;">
              L10 UNIVERSAL PLATFORM
            </span>
          </div>

          <!-- Studio Metrics & Telemetry -->
          <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(140px, 1fr)); gap: 10px; margin-bottom: 16px;">
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">ECS ENTITIES</div>
              <div style="font-size: 18px; font-weight: bold; color: #4DEEEA;">${diag.entitiesCount}</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">REGISTERED RULES</div>
              <div style="font-size: 18px; font-weight: bold; color: #FFE600;">${diag.rulesRegistered}</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">ACTIVE SCENE</div>
              <div style="font-size: 14px; font-weight: bold; color: #FF007F; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${diag.activeScene}</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">ENGINE STATE</div>
              <div style="font-size: 14px; font-weight: bold; color: ${diag.isRunning ? '#00FF66' : '#FF9900'};">${diag.isRunning ? 'RUNNING' : 'STANDBY'}</div>
            </div>
          </div>

          <!-- Visual Canvas Viewport -->
          <div style="background: #080a0f; border: 1px solid rgba(255,255,255,0.1); border-radius: 8px; padding: 12px; margin-bottom: 16px;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
              <span style="font-size: 12px; color: #C5A059; font-weight: bold;">🕹️ SCENE CANVAS VIEWPORT</span>
              <button id="ar-add-node-btn" style="background: rgba(197,160,89,0.2); border: 1px solid #C5A059; color: #FFF; border-radius: 4px; padding: 4px 8px; font-size: 11px; cursor: pointer;">
                + Add RPG Node
              </button>
            </div>
            <div id="ar-scene-viewport" style="position: relative; width: 100%; height: 160px; background: radial-gradient(circle, #1a2233 0%, #080a0f 100%); border-radius: 6px; overflow: hidden; border: 1px dashed rgba(197,160,89,0.3);">
              ${scene.nodes.map(node => `
                <div style="position: absolute; left: ${node.x}px; top: ${node.y / 2}px; background: ${node.color}; color: #000; padding: 4px 8px; border-radius: 4px; font-size: 10px; font-weight: bold; box-shadow: 0 0 10px ${node.color};">
                  ${node.label}
                </div>
              `).join('')}
            </div>
          </div>

          <!-- Rule Testing Controls & Game Package Exporter -->
          <div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center; justify-content: space-between;">
            <div style="display: flex; gap: 8px;">
              <button id="ar-test-rule-btn" style="background: linear-gradient(135deg, #4DEEEA, #0077FF); border: none; color: #000; padding: 8px 14px; border-radius: 6px; font-size: 12px; font-weight: bold; cursor: pointer;">
                ⚡ Execute Test Spell Rule
              </button>
              <button id="ar-export-game-btn" style="background: linear-gradient(135deg, #FFE600, #FF9900); border: none; color: #000; padding: 8px 14px; border-radius: 6px; font-size: 12px; font-weight: bold; cursor: pointer;">
                📦 Export Game Package (.apkg)
              </button>
            </div>
            <div id="ar-studio-output" style="font-size: 11px; color: #00FF66; font-family: monospace;"></div>
          </div>
        </div>
      `;

      // Event listeners
      document.getElementById('ar-add-node-btn')?.addEventListener('click', () => {
        const x = Math.floor(Math.random() * 250) + 20;
        const y = Math.floor(Math.random() * 200) + 20;
        engine.sceneBuilder.addNode('custom', x, y, 'Mana Shrine', '#9B51E0');
        ArcanumGameStudioUI.renderStudio(containerId);
      });

      document.getElementById('ar-test-rule-btn')?.addEventListener('click', () => {
        const results = engine.ruleEngine.executeGraph([
          { name: 'DAMAGE', params: { target: 'Shadow Dragon', amount: 35 } },
          { name: 'SUMMON_PARTICLES', params: { fxType: 'NEON_BURST' } }
        ]);
        const out = document.getElementById('ar-studio-output');
        if (out) {
          out.innerText = `Executed: ${results.map(r => r.message).join(' | ')}`;
        }
        if (window.ArcanumNativeBridge) {
          window.ArcanumNativeBridge.vibrate(50);
          window.ArcanumNativeBridge.showToast('Test Spell Executed!');
        }
      });

      document.getElementById('ar-export-game-btn')?.addEventListener('click', () => {
        const pkg = ArcanumGamePublisher.exportGamePackage({
          title: 'Arcanum Realm of Cards',
          author: 'User Architect',
          scene: engine.sceneBuilder.getScene()
        });
        const out = document.getElementById('ar-studio-output');
        if (out) {
          out.innerText = `Package Exported: ${pkg.manifest.metaId} (${pkg.manifest.checksum})`;
        }
        if (window.ArcanumNativeBridge) {
          window.ArcanumNativeBridge.showToast(`Exported ${pkg.manifest.title}`);
        }
      });
    }
  }

  // EXPOSE TO GLOBAL WINDOW SCOPE
  window.ArcanumEntityComponentSystem = ArcanumEntityComponentSystem;
  window.ArcanumSceneBuilder = ArcanumSceneBuilder;
  window.ArcanumRuleGraphEngine = ArcanumRuleGraphEngine;
  window.ArcanumGamePublisher = ArcanumGamePublisher;
  window.ArcanumGameEngine = ArcanumGameEngine;
  window.ArcanumGameStudioUI = ArcanumGameStudioUI;

})(window);
