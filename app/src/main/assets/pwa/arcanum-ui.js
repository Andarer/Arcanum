/**
 * Arcanum UI v3.4 - ArDesign System, Window Manager, Splash Engine & Command Palette
 * Universal Web Components design system for Arcanum Immersive Platform.
 * Supports dynamic Render Profiles: Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal, Steam, PlayStation, Nintendo, Material, Glass, Neon.
 * Features Floating Window Manager, Splash Loading Scene, Touch Gestures & Command Palette.
 */

(function () {
  'use strict';

  // 13 Available Render Profiles & Theme Overrides
  const RENDER_PROFILES = [
    'fantasy', 'dark', 'scifi', 'cyberpunk', 'pixel', 'console',
    'minimal', 'steam', 'playstation', 'nintendo', 'material', 'glass', 'neon'
  ];

  class ArcanumRenderEngineWeb {
    static activeProfile = 'fantasy';

    static setProfile(profile) {
      if (!RENDER_PROFILES.includes(profile)) profile = 'fantasy';
      this.activeProfile = profile;
      document.documentElement.setAttribute('data-render-profile', profile);
      localStorage.setItem('arcanum_render_profile', profile);

      window.dispatchEvent(new CustomEvent('arcanum:render-profile-changed', { detail: { profile } }));
      if (window.ArcanumEngine && window.ArcanumEngine.context) {
        window.ArcanumEngine.context.eventBus.publish({
          type: 'render_profile_changed',
          sourceModuleId: 'ui_render',
          payload: { profile }
        });
      }
    }

    static getProfile() {
      return this.activeProfile;
    }
  }

  window.ArcanumRenderEngineWeb = ArcanumRenderEngineWeb;

  // Sound helper wrapper
  function playUiSound(type) {
    if (window.ArcanumAudioSynth && window.ArcanumAudioSynth.play) {
      window.ArcanumAudioSynth.play(type);
    }
  }

  // -----------------------------------------------------------------------
  // ArDesign Component Classes Definitions
  // -----------------------------------------------------------------------

  class ArApp extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-app'); }
  }

  class ArScene extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-scene'); }
  }

  class ArSplash extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-splash'); }
  }

  class ArButton extends HTMLElement {
    connectedCallback() {
      if (!this.getAttribute('role')) this.setAttribute('role', 'button');
      if (!this.getAttribute('tabindex')) this.setAttribute('tabindex', '0');
      this.classList.add('ar-ui-button');
      this.addEventListener('click', () => {
        playUiSound('click');
        if (window.ArcanumNative && window.ArcanumNative.vibrate) {
          window.ArcanumNative.vibrate(15);
        }
      });
    }
  }

  class ArPanel extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-panel'); }
  }

  class ArCard extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-card');
      this.addEventListener('click', () => playUiSound('flip'));
    }
  }

  class ArList extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-list'); }
  }

  class ArGrid extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-grid'); }
  }

  class ArDialog extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-dialog'); }
  }

  class ArToast extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-toast'); }
  }

  class ArNotification extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-notification'); }
  }

  class ArHud extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-hud'); }
  }

  class ArTabs extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-tabs'); }
  }

  class ArDock extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-dock'); }
  }

  class ArToolbar extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-toolbar'); }
  }

  class ArSidebar extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-sidebar'); }
  }

  class ArInventory extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-inventory'); }
  }

  class ArQuest extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-quest'); }
  }

  class ArChat extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-chat'); }
  }

  class ArMap extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-map'); }
  }

  class ArCamera extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-camera'); }
  }

  class ArScanner extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-scanner'); }
  }

  class ArMarketplace extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-marketplace'); }
  }

  class ArLibrary extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-library'); }
  }

  class ArExplorer extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-explorer'); }
  }

  class ArSettings extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-settings'); }
  }

  class ArConsole extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-console'); }
  }

  class ArTerminal extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-terminal'); }
  }

  class ArDeveloper extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-developer'); }
  }

  class ArProfiler extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-profiler'); }
  }

  class ArWindow extends HTMLElement {
    connectedCallback() { this.classList.add('ar-ui-window'); }
  }

  class ArCharacter extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-character'); } }
  class ArWorld extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-world'); } }
  class ArMenu extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-menu'); } }
  class ArModal extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-modal'); } }
  class ArContextMenu extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-context-menu'); } }
  class ArTooltip extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-tooltip'); } }
  class ArCraft extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-craft'); } }
  class ArBattle extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-battle'); } }
  class ArEditor extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-editor'); } }
  class ArStatusBar extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-status-bar'); } }
  class ArDialogue extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-dialogue'); } }
  class ArGit extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-git'); } }
  class ArAI extends HTMLElement { connectedCallback() { this.classList.add('ar-ui-ai'); } }

  // Register Custom Web Components
  const COMPONENTS = {
    'ar-app': ArApp,
    'ar-scene': ArScene,
    'ar-splash': ArSplash,
    'ar-button': ArButton,
    'ar-panel': ArPanel,
    'ar-card': ArCard,
    'ar-list': ArList,
    'ar-grid': ArGrid,
    'ar-dialog': ArDialog,
    'ar-toast': ArToast,
    'ar-notification': ArNotification,
    'ar-hud': ArHud,
    'ar-tabs': ArTabs,
    'ar-dock': ArDock,
    'ar-toolbar': ArToolbar,
    'ar-sidebar': ArSidebar,
    'ar-inventory': ArInventory,
    'ar-quest': ArQuest,
    'ar-chat': ArChat,
    'ar-map': ArMap,
    'ar-camera': ArCamera,
    'ar-scanner': ArScanner,
    'ar-marketplace': ArMarketplace,
    'ar-library': ArLibrary,
    'ar-explorer': ArExplorer,
    'ar-settings': ArSettings,
    'ar-console': ArConsole,
    'ar-terminal': ArTerminal,
    'ar-developer': ArDeveloper,
    'ar-profiler': ArProfiler,
    'ar-window': ArWindow,
    'ar-character': ArCharacter,
    'ar-world': ArWorld,
    'ar-menu': ArMenu,
    'ar-modal': ArModal,
    'ar-context-menu': ArContextMenu,
    'ar-tooltip': ArTooltip,
    'ar-craft': ArCraft,
    'ar-battle': ArBattle,
    'ar-editor': ArEditor,
    'ar-status-bar': ArStatusBar,
    'ar-dialogue': ArDialogue,
    'ar-git': ArGit,
    'ar-ai': ArAI
  };

  Object.entries(COMPONENTS).forEach(([tagName, componentClass]) => {
    if (!customElements.get(tagName)) {
      customElements.define(tagName, componentClass);
    }
  });

  // -----------------------------------------------------------------------
  // Splash Loading Engine (ArSplashEngine)
  // -----------------------------------------------------------------------
  class ArSplashEngine {
    static tips = [
      "Совет: Каждая карта имеет синергию со стихией активного героя.",
      "Совет: В редакторе миров можно экспортировать любой пакет в формате .apkg.",
      "Совет: Нажмите Ctrl+K для открытия Командной Палитры Arcanum Console.",
      "Совет: В режиме Арены победа дает редкие кристаллы и золотые монеты.",
      "Совет: Вы можете переключать профили оформления в настройках или консоли.",
      "Совет: Платформа Arcanum работает на 100% офлайн благодаря Service Worker."
    ];

    static showSplash(onComplete) {
      let splashEl = document.getElementById('ar-splash-screen');
      if (!splashEl) {
        splashEl = document.createElement('ar-splash');
        splashEl.id = 'ar-splash-screen';
        splashEl.className = 'ar-splash-overlay';
        splashEl.innerHTML = `
          <div class="ar-splash-content">
            <div class="ar-splash-logo">💎</div>
            <h1 class="ar-splash-title">ARCANUM EVOLUTION</h1>
            <p class="ar-splash-subtitle">IMMERSIVE PLATFORM v3.4 :: OMEGA ERA</p>
            
            <div class="ar-splash-progress-container">
              <div class="ar-splash-progress-bar" id="ar-splash-bar"></div>
            </div>
            
            <div class="ar-splash-step" id="ar-splash-step">Инициализация Microkernel Engine...</div>
            <div class="ar-splash-tip" id="ar-splash-tip"></div>

            <button class="ar-splash-btn" id="ar-splash-start-btn" style="display:none;">
              🚀 ЗАПУСТИТЬ ВСЕЛЕННУЮ ARCANUM
            </button>
          </div>
        `;
        document.body.appendChild(splashEl);
      }

      const randomTip = this.tips[Math.floor(Math.random() * this.tips.length)];
      const tipEl = document.getElementById('ar-splash-tip');
      if (tipEl) tipEl.textContent = randomTip;

      const steps = [
        { progress: '15%', text: 'Загрузка ядра Arcanum Core & EventBus...' },
        { progress: '35%', text: 'Инициализация 32 Web Components & Render Profiles...' },
        { progress: '60%', text: 'Загрузка Базы Карт (30 сущностей) & Миров...' },
        { progress: '80%', text: 'Проверка пакетов .apkg & Синхронизация Digital Twin...' },
        { progress: '100%', text: 'Готово! Платформа готова к работе.' }
      ];

      let currentStep = 0;
      const stepInterval = setInterval(() => {
        if (currentStep < steps.length) {
          const step = steps[currentStep];
          const bar = document.getElementById('ar-splash-bar');
          const txt = document.getElementById('ar-splash-step');
          if (bar) bar.style.width = step.progress;
          if (txt) txt.textContent = step.text;
          playUiSound('click');
          currentStep++;
        } else {
          clearInterval(stepInterval);
          const startBtn = document.getElementById('ar-splash-start-btn');
          if (startBtn) {
            startBtn.style.display = 'inline-block';
            startBtn.onclick = () => {
              playUiSound('strike');
              splashEl.classList.add('ar-splash-fade-out');
              setTimeout(() => {
                splashEl.remove();
                if (onComplete) onComplete();
              }, 400);
            };
          } else {
            splashEl.classList.add('ar-splash-fade-out');
            setTimeout(() => {
              splashEl.remove();
              if (onComplete) onComplete();
            }, 400);
          }
        }
      }, 250);
    }
  }

  window.ArSplashEngine = ArSplashEngine;

  // -----------------------------------------------------------------------
  // ArWindowManager (Floating Window Applet Engine)
  // -----------------------------------------------------------------------
  class ArWindowManager {
    static activeWindows = {};
    static highestZIndex = 1000;
    static windowMode = false; // false = Tab mode, true = Floating Windows mode

    static toggleMode() {
      this.windowMode = !this.windowMode;
      document.body.classList.toggle('ar-window-workspace-active', this.windowMode);
      localStorage.setItem('arcanum_window_mode', this.windowMode ? 'true' : 'false');
      
      const badge = document.getElementById('ar-device-badge');
      if (badge) {
        badge.textContent = this.windowMode ? '🪟 Layout: Floating Windows' : '📱 Layout: Tabs';
      }

      if (this.windowMode) {
        this.openAppletWindow('home', '🏰 Главная');
      } else {
        // Close all floating windows when switching back to tab mode
        const container = document.getElementById('ar-floating-windows-container');
        if (container) container.innerHTML = '';
        this.activeWindows = {};
      }

      playUiSound('flip');
    }

    static openAppletWindow(tabId, title) {
      if (!this.windowMode) {
        // Fallback to switching tab
        if (window.switchTab) window.switchTab(tabId);
        return;
      }

      let winId = `win-${tabId}`;
      let existingWin = document.getElementById(winId);

      if (existingWin) {
        this.bringToFront(existingWin);
        existingWin.style.display = 'block';
        return;
      }

      let container = document.getElementById('ar-floating-windows-container');
      if (!container) {
        container = document.createElement('div');
        container.id = 'ar-floating-windows-container';
        container.className = 'ar-floating-workspace';
        document.body.appendChild(container);
      }

      // Clone original target screen content
      const sourceScreen = document.getElementById(`view-${tabId}`);
      if (!sourceScreen) return;

      const winEl = document.createElement('ar-window');
      winEl.id = winId;
      winEl.className = 'ar-floating-window';
      this.highestZIndex += 1;
      winEl.style.zIndex = this.highestZIndex;

      // Position offset
      const winCount = Object.keys(this.activeWindows).length;
      const left = Math.min(80 + winCount * 30, window.innerWidth - 350);
      const top = Math.min(80 + winCount * 30, window.innerHeight - 300);

      winEl.style.left = `${left}px`;
      winEl.style.top = `${top}px`;

      winEl.innerHTML = `
        <div class="ar-win-header">
          <span class="ar-win-title">${title}</span>
          <div class="ar-win-controls">
            <button class="ar-win-btn ar-win-min" title="Свернуть">_</button>
            <button class="ar-win-btn ar-win-max" title="Развернуть">▢</button>
            <button class="ar-win-btn ar-win-close" title="Закрыть">✕</button>
          </div>
        </div>
        <div class="ar-win-body">
          ${sourceScreen.innerHTML}
        </div>
      `;

      container.appendChild(winEl);
      this.activeWindows[tabId] = winEl;

      this.makeDraggable(winEl);
      this.bindWindowControls(winEl, tabId);
      this.bringToFront(winEl);

      playUiSound('click');
    }

    static bringToFront(winEl) {
      this.highestZIndex += 1;
      winEl.style.zIndex = this.highestZIndex;
      document.querySelectorAll('.ar-floating-window').forEach(w => w.classList.remove('ar-win-focused'));
      winEl.classList.add('ar-win-focused');
    }

    static makeDraggable(winEl) {
      const header = winEl.querySelector('.ar-win-header');
      let isDragging = false;
      let startX, startY, initialLeft, initialTop;

      const onMouseDown = (e) => {
        if (e.target.closest('.ar-win-controls')) return;
        isDragging = true;
        this.bringToFront(winEl);
        startX = e.clientX || (e.touches && e.touches[0].clientX);
        startY = e.clientY || (e.touches && e.touches[0].clientY);
        initialLeft = winEl.offsetLeft;
        initialTop = winEl.offsetTop;

        document.addEventListener('mousemove', onMouseMove);
        document.addEventListener('mouseup', onMouseUp);
        document.addEventListener('touchmove', onMouseMove);
        document.addEventListener('touchend', onMouseUp);
      };

      const onMouseMove = (e) => {
        if (!isDragging) return;
        const clientX = e.clientX || (e.touches && e.touches[0].clientX);
        const clientY = e.clientY || (e.touches && e.touches[0].clientY);
        const dx = clientX - startX;
        const dy = clientY - startY;

        winEl.style.left = `${Math.max(0, initialLeft + dx)}px`;
        winEl.style.top = `${Math.max(0, initialTop + dy)}px`;
      };

      const onMouseUp = () => {
        isDragging = false;
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
        document.removeEventListener('touchmove', onMouseMove);
        document.removeEventListener('touchend', onMouseUp);
      };

      header.addEventListener('mousedown', onMouseDown);
      header.addEventListener('touchstart', onMouseDown);
    }

    static bindWindowControls(winEl, tabId) {
      winEl.addEventListener('mousedown', () => this.bringToFront(winEl));

      const closeBtn = winEl.querySelector('.ar-win-close');
      if (closeBtn) {
        closeBtn.onclick = () => {
          winEl.remove();
          delete this.activeWindows[tabId];
          playUiSound('click');
        };
      }

      const maxBtn = winEl.querySelector('.ar-win-max');
      if (maxBtn) {
        maxBtn.onclick = () => {
          winEl.classList.toggle('ar-win-maximized');
          playUiSound('flip');
        };
      }

      const minBtn = winEl.querySelector('.ar-win-min');
      if (minBtn) {
        minBtn.onclick = () => {
          winEl.style.display = 'none';
          playUiSound('click');
        };
      }
    }
  }

  window.ArWindowManager = ArWindowManager;

  // -----------------------------------------------------------------------
  // ArCommandPalette & Console Command Engine
  // -----------------------------------------------------------------------
  class ArCommandPalette {
    static init() {
      window.addEventListener('keydown', (e) => {
        if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === 'k') {
          e.preventDefault();
          this.togglePalette();
        }
      });
    }

    static togglePalette() {
      let modal = document.getElementById('ar-command-modal');
      if (!modal) {
        modal = document.createElement('ar-modal');
        modal.id = 'ar-command-modal';
        modal.className = 'ar-cmd-modal-overlay';
        modal.innerHTML = `
          <div class="ar-cmd-box">
            <div class="ar-cmd-header">
              <span>⌨️ Arcanum Command Palette (Console)</span>
              <button onclick="ArCommandPalette.togglePalette()" style="background:none;border:none;color:white;cursor:pointer;">✕</button>
            </div>
            <input type="text" id="ar-cmd-input" placeholder="Введите команду (/theme, /window, /audio, /fullscreen, /help)..." autocomplete="off" />
            <div class="ar-cmd-suggestions" id="ar-cmd-suggestions">
              <div onclick="ArCommandPalette.execCommand('/theme fantasy')">🎨 /theme fantasy</div>
              <div onclick="ArCommandPalette.execCommand('/theme cyberpunk')">🎨 /theme cyberpunk</div>
              <div onclick="ArCommandPalette.execCommand('/theme scifi')">🎨 /theme scifi</div>
              <div onclick="ArCommandPalette.execCommand('/window toggle')">🪟 /window toggle (Floating Windows)</div>
              <div onclick="ArCommandPalette.execCommand('/audio toggle')">🔊 /audio toggle</div>
              <div onclick="ArCommandPalette.execCommand('/fullscreen')">🖥️ /fullscreen</div>
              <div onclick="ArCommandPalette.execCommand('/help')">❓ /help</div>
            </div>
            <div id="ar-cmd-output" class="ar-cmd-output"></div>
          </div>
        `;
        document.body.appendChild(modal);

        const input = document.getElementById('ar-cmd-input');
        input.addEventListener('keydown', (e) => {
          if (e.key === 'Enter') {
            this.execCommand(input.value);
            input.value = '';
          }
        });
      }

      modal.style.display = modal.style.display === 'flex' ? 'none' : 'flex';
      if (modal.style.display === 'flex') {
        const input = document.getElementById('ar-cmd-input');
        if (input) input.focus();
        playUiSound('click');
      }
    }

    static execCommand(cmdText) {
      cmdText = cmdText.trim();
      const outputEl = document.getElementById('ar-cmd-output');
      if (!outputEl) return;

      const parts = cmdText.split(' ');
      const mainCmd = parts[0].toLowerCase();
      const arg = parts[1] ? parts[1].toLowerCase() : '';

      let resultText = '';

      switch (mainCmd) {
        case '/theme':
          if (RENDER_PROFILES.includes(arg)) {
            ArcanumRenderEngineWeb.setProfile(arg);
            resultText = `✔ Профиль оформления успешно изменен на [${arg}]`;
          } else {
            resultText = `⚠ Неизвестный профиль. Доступные: ${RENDER_PROFILES.join(', ')}`;
          }
          break;

        case '/window':
          if (arg === 'toggle' || arg === '') {
            ArWindowManager.toggleMode();
            resultText = `✔ Режим окон переключен: ${ArWindowManager.windowMode ? 'Floating Windows' : 'Tabs'}`;
          }
          break;

        case '/audio':
          if (window.ArcanumAudioSynth) {
            window.ArcanumAudioSynth.toggleMute();
            resultText = `✔ Аудио: ${window.ArcanumAudioSynth.isMuted ? 'Выключено' : 'Включено'}`;
          }
          break;

        case '/fullscreen':
          ArcanumCinematics.toggleFullscreen();
          resultText = `✔ Полноэкранный режим переключен`;
          break;

        case '/help':
          resultText = `
            Доступные команды Arcanum Console:<br/>
            - /theme &lt;fantasy|cyberpunk|scifi|dark|pixel|glass...&gt;<br/>
            - /window toggle<br/>
            - /audio toggle<br/>
            - /fullscreen<br/>
            - /clear<br/>
            - /help
          `;
          break;

        case '/clear':
          outputEl.innerHTML = '';
          return;

        default:
          resultText = `⚠ Неизвестная команда "${cmdText}". Введите /help для справки.`;
          break;
      }

      outputEl.innerHTML = `<div style="color:var(--gold-accent);margin-top:6px;">${resultText}</div>`;
      playUiSound('click');
    }
  }

  window.ArCommandPalette = ArCommandPalette;

  // -----------------------------------------------------------------------
  // Cross-Platform Device & Touch Gesture Engine
  // -----------------------------------------------------------------------
  class ArcanumDeviceEngine {
    static state = {
      deviceType: 'pwa',
      hasTouch: false,
      hasMouse: false,
      hasGamepad: false,
      isTV: false,
      screenWidth: window.innerWidth,
      screenHeight: window.innerHeight,
      orientation: window.innerWidth > window.innerHeight ? 'landscape' : 'portrait'
    };

    static init() {
      this.detectCapabilities();
      this.bindListeners();
      this.applyDeviceClasses();
      this.bindTouchGestures();
      ArCommandPalette.init();
    }

    static detectCapabilities() {
      this.state.hasTouch = 'ontouchstart' in window || navigator.maxTouchPoints > 0;
      this.state.hasMouse = window.matchMedia('(pointer: fine)').matches;

      const width = window.innerWidth;
      const height = window.innerHeight;
      this.state.screenWidth = width;
      this.state.screenHeight = height;
      this.state.orientation = width > height ? 'landscape' : 'portrait';

      const userAgent = navigator.userAgent.toLowerCase();
      this.state.isTV = userAgent.includes('tv') || userAgent.includes('smarttv') || userAgent.includes('googletv') || (width >= 1920 && !this.state.hasTouch);

      if (window.ArcanumNative && window.ArcanumNative.isNativeContainer()) {
        this.state.deviceType = 'android';
      } else if (this.state.isTV) {
        this.state.deviceType = 'tv';
      } else if (width >= 1024 && this.state.hasMouse) {
        this.state.deviceType = 'desktop';
      } else if (width >= 768) {
        this.state.deviceType = 'tablet';
      } else {
        this.state.deviceType = 'pwa';
      }
    }

    static bindListeners() {
      window.addEventListener('resize', () => {
        this.detectCapabilities();
        this.applyDeviceClasses();
      });

      window.addEventListener('gamepadconnected', (e) => {
        this.state.hasGamepad = true;
        this.state.deviceType = 'console';
        this.applyDeviceClasses();
        if (window.ArcanumNative && window.ArcanumNative.showToast) {
          window.ArcanumNative.showToast(`Геймпад подключен: ${e.gamepad.id}`);
        }
      });

      window.addEventListener('gamepaddisconnected', () => {
        this.state.hasGamepad = false;
        this.detectCapabilities();
        this.applyDeviceClasses();
      });
    }

    static applyDeviceClasses() {
      const root = document.documentElement;
      root.setAttribute('data-device-type', this.state.deviceType);
      root.setAttribute('data-orientation', this.state.orientation);
      root.setAttribute('data-input-touch', this.state.hasTouch ? 'true' : 'false');
      root.setAttribute('data-input-gamepad', this.state.hasGamepad ? 'true' : 'false');
    }

    static bindTouchGestures() {
      let touchStartX = 0;
      let touchStartY = 0;

      window.addEventListener('touchstart', (e) => {
        if (e.touches.length === 1) {
          touchStartX = e.touches[0].clientX;
          touchStartY = e.touches[0].clientY;
        }
      }, { passive: true });

      window.addEventListener('touchend', (e) => {
        if (!e.changedTouches.length) return;
        const deltaX = e.changedTouches[0].clientX - touchStartX;
        const deltaY = e.changedTouches[0].clientY - touchStartY;

        // Swipe horizontal trigger for tabs
        if (Math.abs(deltaX) > 120 && Math.abs(deltaY) < 60) {
          if (deltaX < 0) {
            // Swipe Left -> Next Tab
            this.navigateTabOffset(1);
          } else {
            // Swipe Right -> Prev Tab
            this.navigateTabOffset(-1);
          }
        }
      }, { passive: true });
    }

    static navigateTabOffset(offset) {
      const tabs = Array.from(document.querySelectorAll('.nav-tabs .tab-btn'));
      if (!tabs.length) return;
      const activeIdx = tabs.findIndex(t => t.classList.contains('active'));
      if (activeIdx === -1) return;

      const nextIdx = (activeIdx + offset + tabs.length) % tabs.length;
      const nextTab = tabs[nextIdx];
      if (nextTab && nextTab.dataset.tab) {
        if (window.switchTab) window.switchTab(nextTab.dataset.tab);
      }
    }
  }

  // Cinematic & Transition Engine
  class ArcanumCinematics {
    static transitionTo(targetElement, effect = 'zoom') {
      if (!targetElement) return;
      targetElement.classList.add(`fx-transition-${effect}`);
      setTimeout(() => {
        targetElement.classList.remove(`fx-transition-${effect}`);
      }, 500);
    }

    static screenShake(intensity = 10) {
      document.body.classList.add('fx-screen-shake');
      setTimeout(() => {
        document.body.classList.remove('fx-screen-shake');
      }, 300);
    }

    static toggleFullscreen() {
      if (!document.fullscreenElement) {
        document.documentElement.requestFullscreen().catch(() => {});
      } else {
        if (document.exitFullscreen) document.exitFullscreen().catch(() => {});
      }
    }

    static async requestWakeLock() {
      if ('wakeLock' in navigator) {
        try {
          await navigator.wakeLock.request('screen');
        } catch (err) {
          console.warn('Wake Lock failed:', err);
        }
      }
    }
  }

  window.ArcanumDeviceEngine = ArcanumDeviceEngine;
  window.ArcanumCinematics = ArcanumCinematics;

  // Restore saved render profile
  const savedProfile = localStorage.getItem('arcanum_render_profile') || 'fantasy';
  ArcanumRenderEngineWeb.setProfile(savedProfile);
  ArcanumDeviceEngine.init();

  console.log('Arcanum UI v3.4 ArDesign System, Window Manager, Splash Engine & Command Palette initialized.');
})();
