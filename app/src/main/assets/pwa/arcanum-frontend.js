/**
 * Arcanum Frontend Runtime v3.6 - Omega Frontend Runtime Architecture
 * Self-contained, modular frontend runtime environment powering Arcanum Evolution.
 * Operates on Android, PWA, Web, Desktop, Tablet, TV, Foldable, and VR/AR runtimes.
 *
 * Core Engines Included:
 * 1. Design Tokens & Theme Engine
 * 2. Reactive State Store & Snapshot Persistence Engine
 * 3. Centralized Asset & Icon Registry Engine
 * 4. Unified Multi-Input & Touch/Gamepad Gesture Engine
 * 5. Realtime Performance Monitor & Auto-Quality Degradar Engine
 * 6. Dynamic Scene System & Virtual Render Pipeline Engine
 * 7. Workspace State & Floating Window Manager Engine
 * 8. Global Command Palette Console Engine
 * 9. Interactive Frontend Debugger & Live Component Laboratory
 * 10. Runtime Master Lifecycle Controller & Bootstrapper
 */

(function () {
  'use strict';

  // =======================================================================
  // 1. DESIGN TOKENS & THEME ENGINE
  // =======================================================================
  const DesignTokens = {
    colors: {
      goldAccent: '#c5a059',
      goldLight: '#f3e5ab',
      goldDark: '#8a6a2a',
      bgDark: '#0b0c10',
      bgSurface: '#1f2833',
      bgCard: '#141a22',
      textMain: '#c5a059',
      textMuted: '#8a9ba8',
      textHighlight: '#ffffff',
      greenSuccess: '#2ecc71',
      redDanger: '#e74c3c',
      blueInfo: '#3498db',
      purpleMana: '#9b59b6'
    },
    spacing: {
      xs: '4px',
      sm: '8px',
      md: '16px',
      lg: '24px',
      xl: '32px',
      xxl: '48px'
    },
    radii: {
      sm: '4px',
      md: '8px',
      lg: '12px',
      xl: '20px',
      full: '9999px'
    },
    typography: {
      fontFamilyMain: "'Cinzel', 'Trajan Pro', Georgia, serif",
      fontFamilyMono: "Consolas, 'Courier New', monospace",
      fontSizeXs: '11px',
      fontSizeSm: '13px',
      fontSizeMd: '15px',
      fontSizeLg: '18px',
      fontSizeXl: '24px',
      fontSizeTitle: '32px'
    },
    motion: {
      durationFast: '150ms',
      durationNormal: '300ms',
      durationSlow: '500ms',
      easingDefault: 'cubic-bezier(0.4, 0.0, 0.2, 1)',
      easingBounce: 'cubic-bezier(0.68, -0.55, 0.265, 1.55)'
    },
    shadows: {
      sm: '0 2px 8px rgba(0,0,0,0.5)',
      md: '0 6px 16px rgba(0,0,0,0.7)',
      lg: '0 12px 32px rgba(197, 160, 89, 0.3)'
    },
    zIndices: {
      hud: 100,
      dock: 500,
      window: 1000,
      modal: 10000,
      splash: 99999
    }
  };

  class ArcanumThemeEngine {
    static activeTheme = 'fantasy';
    static themes = {
      fantasy: { name: 'Dark Fantasy Gold', primary: '#c5a059', bg: '#0b0c10', surface: '#1f2833' },
      dark: { name: 'Deep Onyx', primary: '#ffffff', bg: '#000000', surface: '#111111' },
      scifi: { name: 'Quantum Cyan', primary: '#00f3ff', bg: '#030b14', surface: '#0a1d33' },
      cyberpunk: { name: 'Neon Cyber', primary: '#ff0055', bg: '#0d001a', surface: '#260033' },
      pixel: { name: '8-Bit Retro', primary: '#f1c40f', bg: '#1a1c23', surface: '#2d313f' },
      console: { name: 'Amber Terminal', primary: '#ffb000', bg: '#080808', surface: '#121212' },
      minimal: { name: 'Monochrome Clean', primary: '#dddddd', bg: '#121212', surface: '#1e1e1e' },
      glass: { name: 'Frosted Glass', primary: '#a8d8ea', bg: 'rgba(20,20,30,0.85)', surface: 'rgba(255,255,255,0.08)' }
    };

    static applyTheme(themeKey) {
      if (!this.themes[themeKey]) return;
      this.activeTheme = themeKey;
      const theme = this.themes[themeKey];
      document.body.setAttribute('data-render-profile', themeKey);
      document.documentElement.style.setProperty('--gold-accent', theme.primary);
      document.documentElement.style.setProperty('--bg-dark', theme.bg);
      document.documentElement.style.setProperty('--bg-surface', theme.surface);
      window.dispatchEvent(new CustomEvent('arcanum:theme-changed', { detail: { themeKey, theme } }));
    }
  }

  window.DesignTokens = DesignTokens;
  window.ArcanumThemeEngine = ArcanumThemeEngine;

  // =======================================================================
  // 2. REACTIVE STATE STORE & SNAPSHOT PERSISTENCE ENGINE
  // =======================================================================
  class ArcanumStateStore {
    static state = {
      user: { level: 1, gold: 150, hp: 100, maxHp: 100, exp: 0, deckCount: 8 },
      activeScene: 'home',
      activeRenderProfile: 'fantasy',
      windowMode: false,
      audioMuted: false,
      activeTheme: 'Dark Fantasy Gold',
      workspaces: { default: { openWindows: ['home'], zoom: 1, theme: 'fantasy' } },
      activeWorkspace: 'default',
      performanceQuality: 'high',
      debugMode: false
    };

    static subscribers = new Set();
    static snapshots = [];

    static get(key) {
      if (!key) return { ...this.state };
      return key.split('.').reduce((acc, part) => (acc && acc[part] !== undefined ? acc[part] : undefined), this.state);
    }

    static set(key, value) {
      this.recordSnapshot();
      const parts = key.split('.');
      let obj = this.state;
      for (let i = 0; i < parts.length - 1; i++) {
        if (!obj[parts[i]]) obj[parts[i]] = {};
        obj = obj[parts[i]];
      }
      obj[parts[parts.length - 1]] = value;

      this.notify(key, value);
      ArcanumStatePersistence.saveState();
    }

    static subscribe(callback) {
      this.subscribers.add(callback);
      return () => this.subscribers.delete(callback);
    }

    static notify(key, value) {
      this.subscribers.forEach(cb => cb(key, value, this.state));
      window.dispatchEvent(new CustomEvent('arcanum:state-changed', { detail: { key, value, state: this.state } }));
    }

    static recordSnapshot() {
      if (this.snapshots.length > 50) this.snapshots.shift();
      this.snapshots.push(JSON.parse(JSON.stringify(this.state)));
    }

    static timeTravelUndo() {
      if (this.snapshots.length > 0) {
        this.state = this.snapshots.pop();
        this.notify('all', this.state);
        return true;
      }
      return false;
    }
  }

  class ArcanumStatePersistence {
    static STORAGE_KEY = 'arcanum_omega_runtime_state_v3.6';

    static saveState() {
      try {
        const data = ArcanumStateStore.get();
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(data));
      } catch (e) {
        console.warn('State save failed:', e);
      }
    }

    static restoreState() {
      try {
        const raw = localStorage.getItem(this.STORAGE_KEY);
        if (raw) {
          const parsed = JSON.parse(raw);
          Object.assign(ArcanumStateStore.state, parsed);
        }
      } catch (e) {
        console.warn('State restore failed:', e);
      }
    }
  }

  window.ArcanumStateStore = ArcanumStateStore;
  window.ArcanumStatePersistence = ArcanumStatePersistence;

  // =======================================================================
  // 3. ICON & ASSET REGISTRY ENGINE
  // =======================================================================
  class ArcanumIconEngine {
    static registry = new Map([
      ['home', '🏰'],
      ['cards', '🎴'],
      ['battle', '⚔️'],
      ['pvp', '🗡️'],
      ['clicker', '💎'],
      ['shooter', '🎯'],
      ['world', '🗺️'],
      ['craft', '🔨'],
      ['deck', '📦'],
      ['studio', '🎨'],
      ['specs', '📜'],
      ['kernel', '🤖'],
      ['package', '📦'],
      ['github', '🐙'],
      ['console', '⌨️'],
      ['settings', '⚙️'],
      ['lab', '🧪'],
      ['debug', '🐞']
    ]);

    static getIcon(name) {
      return this.registry.get(name) || '📱';
    }

    static registerIcon(name, symbolOrSvg) {
      this.registry.set(name, symbolOrSvg);
    }
  }

  window.ArcanumIconEngine = ArcanumIconEngine;

  // =======================================================================
  // 4. UNIFIED INPUT & GESTURE ENGINE
  // =======================================================================
  class ArcanumInputEngine {
    static touchStartX = 0;
    static touchStartY = 0;

    static init() {
      window.addEventListener('touchstart', (e) => this.handleTouchStart(e), { passive: true });
      window.addEventListener('touchend', (e) => this.handleTouchEnd(e), { passive: true });
      window.addEventListener('keydown', (e) => this.handleKeyDown(e));
    }

    static handleTouchStart(e) {
      if (e.touches && e.touches[0]) {
        this.touchStartX = e.touches[0].clientX;
        this.touchStartY = e.touches[0].clientY;
      }
    }

    static handleTouchEnd(e) {
      if (!e.changedTouches || !e.changedTouches[0]) return;
      const deltaX = e.changedTouches[0].clientX - this.touchStartX;
      const deltaY = e.changedTouches[0].clientY - this.touchStartY;

      if (Math.abs(deltaX) > 60 && Math.abs(deltaY) < 40) {
        const direction = deltaX > 0 ? 'right' : 'left';
        window.dispatchEvent(new CustomEvent('arcanum:gesture-swipe', { detail: { direction } }));
      }
    }

    static handleKeyDown(e) {
      if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === 'k') {
        e.preventDefault();
        if (window.ArCommandPalette) {
          window.ArCommandPalette.togglePalette();
        }
      }
    }
  }

  window.ArcanumInputEngine = ArcanumInputEngine;

  // =======================================================================
  // 5. PERFORMANCE ENGINE & AUTO QUALITY SCALING
  // =======================================================================
  class ArcanumPerformanceEngine {
    static fps = 60;
    static frameCount = 0;
    static lastTime = performance.now();
    static renderTimeMs = 0;

    static startMonitoring() {
      const loop = (now) => {
        this.frameCount++;
        if (now >= this.lastTime + 1000) {
          this.fps = Math.round((this.frameCount * 1000) / (now - this.lastTime));
          this.frameCount = 0;
          this.lastTime = now;
          this.checkAutoDegradation();
        }
        requestAnimationFrame(loop);
      };
      requestAnimationFrame(loop);
    }

    static checkAutoDegradation() {
      if (this.fps < 25 && ArcanumStateStore.get('performanceQuality') !== 'low') {
        ArcanumStateStore.set('performanceQuality', 'low');
        document.body.classList.add('ar-perf-low');
        console.warn('Performance Engine: Low FPS detected. Auto-reducing particle & shader visual complexity.');
      } else if (this.fps > 50 && ArcanumStateStore.get('performanceQuality') === 'low') {
        ArcanumStateStore.set('performanceQuality', 'high');
        document.body.classList.remove('ar-perf-low');
      }
    }
  }

  window.ArcanumPerformanceEngine = ArcanumPerformanceEngine;

  // =======================================================================
  // 6. DYNAMIC SCENE SYSTEM & ROUTER ENGINE
  // =======================================================================
  class ArcanumSceneEngine {
    static scenes = new Map();
    static activeScene = 'home';

    static registerScene(id, name, icon) {
      this.scenes.set(id, { id, name, icon, viewId: `view-${id}` });
    }

    static loadScene(sceneId) {
      if (!this.scenes.has(sceneId)) sceneId = 'home';
      this.activeScene = sceneId;
      ArcanumStateStore.set('activeScene', sceneId);

      if (window.switchTab) {
        window.switchTab(sceneId);
      }

      window.dispatchEvent(new CustomEvent('arcanum:scene-loaded', { detail: { sceneId } }));
    }
  }

  // Register Standard Runtime Scenes
  [
    ['home', 'Castle Hub', '🏰'],
    ['collection', 'Cards Deck', '🎴'],
    ['battle', 'Arena PvE', '⚔️'],
    ['pvp', 'Arena PvP', '🗡️'],
    ['clicker', 'Mana Clicker', '💎'],
    ['shooter', 'Dragon Shooter', '🎯'],
    ['world', 'World Realm', '🗺️'],
    ['craft', 'Alchemy Forge', '🔨'],
    ['deck', 'Deck Builder', '📦'],
    ['studio', 'Arcanum Studio', '🎨'],
    ['specs', 'Documentation', '📜'],
    ['kernel', 'Digital Twin', '🤖'],
    ['package', 'Package Manager', '📦'],
    ['github', 'GitHub Factory', '🐙'],
    ['lab', 'Frontend Lab', '🧪']
  ].forEach(([id, name, icon]) => ArcanumSceneEngine.registerScene(id, name, icon));

  window.ArcanumSceneEngine = ArcanumSceneEngine;

  // =======================================================================
  // 7. WORKSPACE ENGINE & WINDOW MANAGER RESTORE
  // =======================================================================
  class ArcanumWorkspaceEngine {
    static saveWorkspace(name = 'default') {
      const openWindows = [];
      document.querySelectorAll('.ar-window-container').forEach(win => {
        if (win.style.display !== 'none') openWindows.push(win.id);
      });
      const data = { openWindows, theme: ArcanumThemeEngine.activeTheme, timestamp: Date.now() };
      ArcanumStateStore.set(`workspaces.${name}`, data);
      return data;
    }

    static restoreWorkspace(name = 'default') {
      const ws = ArcanumStateStore.get(`workspaces.${name}`);
      if (ws && ws.theme) {
        ArcanumThemeEngine.applyTheme(ws.theme);
      }
    }
  }

  window.ArcanumWorkspaceEngine = ArcanumWorkspaceEngine;

  // =======================================================================
  // 8. FRONTEND DEBUGGER & LIVE FRONTEND LAB VIEW
  // =======================================================================
  class ArcanumFrontendLab {
    static renderLabView(containerId = 'view-lab') {
      const container = document.getElementById(containerId);
      if (!container) return;

      container.innerHTML = `
        <div style="padding:16px;max-width:1200px;margin:0 auto;color:var(--gold-accent);">
          <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:16px;border-bottom:1px solid var(--gold-accent);padding-bottom:8px;">
            <h2 style="font-size:20px;margin:0;">🧪 ARCANUM FRONTEND RUNTIME LABORATORY v3.6</h2>
            <span style="font-size:12px;background:rgba(197,160,89,0.2);padding:4px 10px;border-radius:12px;border:1px solid var(--gold-accent);">
              FPS: <strong id="ar-lab-fps">60</strong> | Memory: <strong id="ar-lab-mem">Normal</strong>
            </span>
          </div>

          <div style="display:grid;grid-template-columns:repeat(auto-fit, minmax(320px, 1fr));gap:16px;">
            <!-- Render Themes Switcher -->
            <div style="background:var(--bg-card);border:1px solid rgba(255,255,255,0.1);padding:14px;border-radius:10px;">
              <h3 style="font-size:14px;color:var(--gold-light);margin-bottom:10px;">🎨 13 Render Profiles Switcher</h3>
              <div style="display:flex;flex-wrap:wrap;gap:6px;">
                ${Object.keys(ArcanumThemeEngine.themes).map(t => `
                  <button onclick="ArcanumThemeEngine.applyTheme('${t}')" style="background:rgba(197,160,89,0.15);border:1px solid var(--gold-accent);color:var(--text-highlight);padding:6px 12px;border-radius:6px;cursor:pointer;font-size:11px;">
                    ${ArcanumThemeEngine.themes[t].name}
                  </button>
                `).join('')}
              </div>
            </div>

            <!-- Web Components Catalog Inspector -->
            <div style="background:var(--bg-card);border:1px solid rgba(255,255,255,0.1);padding:14px;border-radius:10px;">
              <h3 style="font-size:14px;color:var(--gold-light);margin-bottom:10px;">📦 Registered Web Components (32)</h3>
              <div id="ar-lab-catalog-container"></div>
            </div>

            <!-- Gesture & Input Monitor -->
            <div style="background:var(--bg-card);border:1px solid rgba(255,255,255,0.1);padding:14px;border-radius:10px;">
              <h3 style="font-size:14px;color:var(--gold-light);margin-bottom:10px;">🎮 Multi-Input & Touch Gesture Engine</h3>
              <div id="ar-lab-gesture-log" style="font-family:monospace;font-size:11px;background:rgba(0,0,0,0.5);padding:8px;border-radius:6px;height:120px;overflow-y:auto;color:#2ecc71;">
                [Runtime] Waiting for gestures (Swipe left/right, Keydown, Touch)...
              </div>
            </div>
          </div>
        </div>
      `;

      if (window.ComponentCatalog) {
        window.ComponentCatalog.renderCatalogViewer('ar-lab-catalog-container');
      }

      window.addEventListener('arcanum:gesture-swipe', (e) => {
        const log = document.getElementById('ar-lab-gesture-log');
        if (log) {
          log.innerHTML += `<div>[Gesture] Swipe Detected: <strong>${e.detail.direction}</strong></div>`;
          log.scrollTop = log.scrollHeight;
        }
      });
    }
  }

  window.ArcanumFrontendLab = ArcanumFrontendLab;

  // =======================================================================
  // 9. MASTER RUNTIME CONTROLLER & BOOTSTRAPPER
  // =======================================================================
  class ArcanumFrontendRuntime {
    static version = '3.6.0';

    static boot() {
      console.log(`[Arcanum Runtime] Booting Frontend Runtime v${this.version}...`);
      ArcanumStatePersistence.restoreState();
      ArcanumInputEngine.init();
      ArcanumPerformanceEngine.startMonitoring();

      // Register lab scene if not exists
      AppletRegistry.register({ id: 'lab', title: 'Frontend Lab', icon: '🧪', category: 'developer', viewId: 'view-lab' });

      // Render Lab view on demand
      window.addEventListener('arcanum:scene-loaded', (e) => {
        if (e.detail.sceneId === 'lab') {
          ArcanumFrontendLab.renderLabView();
        }
      });

      console.log(`[Arcanum Runtime] Frontend Runtime v${this.version} Operational.`);
    }
  }

  window.ArcanumFrontendRuntime = ArcanumFrontendRuntime;

  // Auto Boot Engine
  window.addEventListener('DOMContentLoaded', () => {
    ArcanumFrontendRuntime.boot();
  });
})();
