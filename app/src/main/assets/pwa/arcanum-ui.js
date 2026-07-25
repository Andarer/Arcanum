/**
 * Arcanum UI v2.0 - Gaming Web Components & Render Profiles Engine
 * Custom Web Components design system for Arcanum Evolution PWA.
 * Supports dynamic Render Profiles: Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal, Steam, PlayStation, Nintendo, Material, Glass, Neon.
 */

(function () {
  'use strict';

  // Available Render Profiles & Attributes
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
      window.dispatchEvent(new CustomEvent('arcanum:render-profile-changed', { detail: { profile } }));
      if (window.ArcanumEngine) {
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

  // Web Component 1: <ar-button>
  class ArButton extends HTMLElement {
    connectedCallback() {
      if (!this.getAttribute('role')) this.setAttribute('role', 'button');
      if (!this.getAttribute('tabindex')) this.setAttribute('tabindex', '0');
      this.classList.add('ar-ui-button');
      this.addEventListener('click', () => {
        if (window.ArcanumNative && window.ArcanumNative.vibrate) {
          window.ArcanumNative.vibrate(20);
        }
      });
    }
  }

  // Web Component 2: <ar-panel>
  class ArPanel extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-panel');
    }
  }

  // Web Component 3: <ar-card>
  class ArCard extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-card');
    }
  }

  // Web Component 4: <ar-inventory>
  class ArInventory extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-inventory');
    }
  }

  // Web Component 5: <ar-dialog>
  class ArDialog extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-dialog');
    }
  }

  // Web Component 6: <ar-quest>
  class ArQuest extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-quest');
    }
  }

  // Web Component 7: <ar-window>
  class ArWindow extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-window');
    }
  }

  // Web Component 8: <ar-map>
  class ArMap extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-map');
    }
  }

  // Web Component 9: <ar-character>
  class ArCharacter extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-character');
    }
  }

  // Web Component 10: <ar-world>
  class ArWorld extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-world');
    }
  }

  // Web Component 11: <ar-menu>
  class ArMenu extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-menu');
    }
  }

  // Web Component 12: <ar-hud>
  class ArHud extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-hud');
    }
  }

  // Web Component 13: <ar-notification>
  class ArNotification extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-notification');
    }
  }

  // Web Component 14: <ar-modal>
  class ArModal extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-modal');
    }
  }

  // Web Component 15: <ar-context-menu>
  class ArContextMenu extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-context-menu');
    }
  }

  // Web Component 16: <ar-tooltip>
  class ArTooltip extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-tooltip');
    }
  }

  // Web Component 17: <ar-craft>
  class ArCraft extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-craft');
    }
  }

  // Web Component 18: <ar-battle>
  class ArBattle extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-battle');
    }
  }

  // Web Component 19: <ar-editor>
  class ArEditor extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-editor');
    }
  }

  // Web Component 20: <ar-dock>
  class ArDock extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-dock');
    }
  }

  // Web Component 21: <ar-sidebar>
  class ArSidebar extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-sidebar');
    }
  }

  // Web Component 22: <ar-toolbar>
  class ArToolbar extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-toolbar');
    }
  }

  // Web Component 23: <ar-status-bar>
  class ArStatusBar extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-status-bar');
    }
  }

  // Web Component 24: <ar-dialogue>
  class ArDialogue extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-dialogue');
    }
  }

  // Web Component 25: <ar-toast>
  class ArToast extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-toast');
    }
  }

  // Web Component 26: <ar-console>
  class ArConsole extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-console');
    }
  }

  // Web Component 27: <ar-settings>
  class ArSettings extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-settings');
    }
  }

  // Web Component 28: <ar-library>
  class ArLibrary extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-library');
    }
  }

  // Web Component 29: <ar-marketplace>
  class ArMarketplace extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-marketplace');
    }
  }

  // Web Component 30: <ar-git>
  class ArGit extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-git');
    }
  }

  // Web Component 31: <ar-ai>
  class ArAI extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-ai');
    }
  }

  // Web Component 32: <ar-explorer>
  class ArExplorer extends HTMLElement {
    connectedCallback() {
      this.classList.add('ar-ui-explorer');
    }
  }

  // Register Custom Web Components
  const COMPONENTS = {
    'ar-button': ArButton,
    'ar-panel': ArPanel,
    'ar-card': ArCard,
    'ar-inventory': ArInventory,
    'ar-dialog': ArDialog,
    'ar-dialogue': ArDialogue,
    'ar-quest': ArQuest,
    'ar-window': ArWindow,
    'ar-map': ArMap,
    'ar-character': ArCharacter,
    'ar-world': ArWorld,
    'ar-menu': ArMenu,
    'ar-hud': ArHud,
    'ar-notification': ArNotification,
    'ar-modal': ArModal,
    'ar-toast': ArToast,
    'ar-context-menu': ArContextMenu,
    'ar-tooltip': ArTooltip,
    'ar-craft': ArCraft,
    'ar-battle': ArBattle,
    'ar-editor': ArEditor,
    'ar-dock': ArDock,
    'ar-sidebar': ArSidebar,
    'ar-toolbar': ArToolbar,
    'ar-status-bar': ArStatusBar,
    'ar-console': ArConsole,
    'ar-settings': ArSettings,
    'ar-library': ArLibrary,
    'ar-marketplace': ArMarketplace,
    'ar-git': ArGit,
    'ar-ai': ArAI,
    'ar-explorer': ArExplorer
  };

  Object.entries(COMPONENTS).forEach(([tagName, componentClass]) => {
    if (!customElements.get(tagName)) {
      customElements.define(tagName, componentClass);
    }
  });

  // Cross-Platform Device & Input Detection Engine
  class ArcanumDeviceEngine {
    static state = {
      deviceType: 'pwa', // pwa, android, desktop, tablet, tv, console, foldable
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
    }

    static detectCapabilities() {
      this.state.hasTouch = 'ontouchstart' in window || navigator.maxTouchPoints > 0;
      this.state.hasMouse = window.matchMedia('(pointer: fine)').matches;
      
      const width = window.innerWidth;
      const height = window.innerHeight;
      this.state.screenWidth = width;
      this.state.screenHeight = height;
      this.state.orientation = width > height ? 'landscape' : 'portrait';

      // Detect TV / Large display
      const userAgent = navigator.userAgent.toLowerCase();
      this.state.isTV = userAgent.includes('tv') || userAgent.includes('smarttv') || userAgent.includes('googletv') || width >= 1920 && !this.state.hasTouch;

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

  // Initialize Engines
  ArcanumRenderEngineWeb.setProfile('fantasy');
  ArcanumDeviceEngine.init();

  console.log('Arcanum UI v2.0 Web Components, Cross-Platform Device Engine & Render Profiles initialized.');
})();
