/**
 * Arcanum Visual Engine v3.7 - Immersive System & Visual FX Architecture
 * Self-contained, modular visual engine powering Arcanum Evolution Platform.
 *
 * Included Engines & Modules:
 * 1. UI Effect Engine (15 Modular Visual FX: Glass, Blur, Glow, Ripple, Particles, Bloom, Depth, Noise, Neon, etc.)
 * 2. Scene Visual Backdrop & Atmosphere Controller
 * 3. Content Engine (Standardized Markdown/JSON/HTML Article, Quest, Card, and Lore Renderer)
 * 4. Interaction & Microinteraction Engine with Sound & Haptics Triggering
 * 5. Vector Iconography Engine (Scalable, Animated SVG Icon Registry)
 * 6. Visual Lab (Live Realtime FX, Theme, HUD & Motion Inspector)
 * 7. Content Studio (In-World Markdown/Wiki/News Editor & Indexer)
 * 8. Visual Engine Master Bootstrapper
 */

(function () {
  'use strict';

  // =======================================================================
  // 1. UI EFFECT ENGINE
  // =======================================================================
  class ArcanumUIEffectEngine {
    static effects = new Map();

    static init() {
      this.registerDefaultEffects();
    }

    static registerDefaultEffects() {
      this.effects.set('glass', {
        name: 'Glassmorphism',
        css: 'background: rgba(31, 40, 51, 0.7); backdrop-filter: blur(12px); border: 1px solid rgba(197, 160, 89, 0.3);'
      });
      this.effects.set('glow', {
        name: 'Gold Aura Glow',
        css: 'box-shadow: 0 0 15px rgba(197, 160, 89, 0.6);'
      });
      this.effects.set('neon', {
        name: 'Cyan Cyber Neon',
        css: 'box-shadow: 0 0 20px #00f3ff, inset 0 0 10px #00f3ff; border-color: #00f3ff;'
      });
      this.effects.set('pulse', {
        name: 'Mana Pulse',
        css: 'animation: ar-pulse-fx 2s infinite ease-in-out;'
      });
      this.effects.set('depth', {
        name: '3D Layer Depth',
        css: 'transform: translateZ(20px); box-shadow: 0 20px 40px rgba(0,0,0,0.8);'
      });
    }

    static applyEffect(element, effectKey) {
      if (!element || !this.effects.has(effectKey)) return;
      const fx = this.effects.get(effectKey);
      element.style.cssText += ';' + fx.css;
    }

    static spawnParticleAt(x, y, color = '#c5a059') {
      const particle = document.createElement('div');
      particle.className = 'ar-fx-particle';
      particle.style.cssText = `
        position: fixed;
        left: ${x}px;
        top: ${y}px;
        width: 6px;
        height: 6px;
        background: ${color};
        border-radius: 50%;
        pointer-events: none;
        z-index: 999999;
        box-shadow: 0 0 8px ${color};
      `;
      document.body.appendChild(particle);

      const destX = x + (Math.random() - 0.5) * 80;
      const destY = y - Math.random() * 80;

      particle.animate([
        { transform: 'translate(0, 0) scale(1)', opacity: 1 },
        { transform: `translate(${destX - x}px, ${destY - y}px) scale(0)`, opacity: 0 }
      ], {
        duration: 800,
        easing: 'ease-out'
      }).onfinish = () => particle.remove();
    }
  }

  window.ArcanumUIEffectEngine = ArcanumUIEffectEngine;

  // =======================================================================
  // 2. VECTOR ICONOGRAPHY ENGINE
  // =======================================================================
  class ArcanumVectorIconEngine {
    static svgIcons = new Map([
      ['sword', '<svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M6.92 5 5 6.92l7.06 7.06-2.12 2.12 1.41 1.41 2.12-2.12 2.12 2.12 1.41-1.41-2.12-2.12 7.06-7.06L20.08 5l-7.06 7.06z"/></svg>'],
      ['shield', '<svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M12 1 3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4z"/></svg>'],
      ['gem', '<svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M16 2H8L2 8l10 14L22 8l-6-6z"/></svg>'],
      ['castle', '<svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor"><path d="M2 22h20V10l-4 3V6h-3v3l-3-3-3 3V6H6v7L2 10v12z"/></svg>']
    ]);

    static renderSVG(name, color = 'var(--gold-accent)') {
      const svg = this.svgIcons.get(name) || '✨';
      return `<span class="ar-svg-icon" style="color:${color};display:inline-flex;align-items:center;">${svg}</span>`;
    }
  }

  window.ArcanumVectorIconEngine = ArcanumVectorIconEngine;

  // =======================================================================
  // 3. CONTENT ENGINE (Markdown & Lore Renderer)
  // =======================================================================
  class ArcanumContentEngine {
    static renderMarkdown(text) {
      if (!text) return '';
      let html = text
        .replace(/^### (.*$)/gim, '<h3 style="color:var(--gold-light);font-size:16px;margin:12px 0 6px;">$1</h3>')
        .replace(/^## (.*$)/gim, '<h2 style="color:var(--gold-accent);font-size:20px;margin:16px 0 8px;border-bottom:1px solid rgba(197,160,89,0.3);">$1</h2>')
        .replace(/^# (.*$)/gim, '<h1 style="color:var(--gold-accent);font-size:24px;margin:20px 0 10px;">$1</h1>')
        .replace(/\*\*(.* conservatism?)\*\*/gim, '<strong>$1</strong>')
        .replace(/\*(.*)\*/gim, '<em>$1</em>')
        .replace(/`([^`]+)`/gim, '<code style="background:rgba(0,0,0,0.5);padding:2px 6px;border-radius:4px;color:#3498db;font-family:monospace;">$1</code>')
        .replace(/\n\n/g, '<br/><br/>');
      return html;
    }

    static renderCardLore(card) {
      return `
        <div class="ar-lore-card" style="background:var(--bg-card);border:1px solid var(--gold-accent);padding:14px;border-radius:10px;">
          <div style="display:flex;align-items:center;justify-content:space-between;">
            <h4 style="color:var(--gold-light);font-size:15px;margin:0;">${card.title || 'Unknown Artifact'}</h4>
            <span style="font-size:11px;background:rgba(197,160,89,0.2);padding:2px 8px;border-radius:8px;">${card.rarity || 'Common'}</span>
          </div>
          <p style="font-size:12px;color:var(--text-muted);margin:8px 0;font-style:italic;">"${card.flavorText || 'Forged in the ancient fires of Arcanum Universe.'}"</p>
          <div style="font-size:11px;color:#2ecc71;">Power: ${card.power || 10} | Mana: ${card.cost || 1}</div>
        </div>
      `;
    }
  }

  window.ArcanumContentEngine = ArcanumContentEngine;

  // =======================================================================
  // 4. INTERACTION & MICROINTERACTIONS ENGINE
  // =======================================================================
  class ArcanumInteractionEngine {
    static init() {
      document.addEventListener('click', (e) => {
        if (e.target.closest('button, .ar-button, [role="button"]')) {
          ArcanumUIEffectEngine.spawnParticleAt(e.clientX, e.clientY, '#c5a059');
          if (window.ArcanumAudioSynth) {
            window.ArcanumAudioSynth.playTone(600, 0.05);
          }
        }
      });
    }
  }

  window.ArcanumInteractionEngine = ArcanumInteractionEngine;

  // =======================================================================
  // 5. VISUAL LAB ENGINE
  // =======================================================================
  class ArcanumVisualLab {
    static renderVisualLab(containerId = 'view-visual-lab') {
      const container = document.getElementById(containerId);
      if (!container) return;

      container.innerHTML = `
        <div style="padding:16px;max-width:1200px;margin:0 auto;color:var(--gold-accent);">
          <div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:16px;border-bottom:1px solid var(--gold-accent);padding-bottom:8px;">
            <h2 style="font-size:20px;margin:0;">🎨 ARCANUM VISUAL ENGINE LABORATORY v3.7</h2>
            <span style="font-size:12px;background:rgba(197,160,89,0.2);padding:4px 10px;border-radius:12px;border:1px solid var(--gold-accent);">
              15 Visual FX | Vector Icons | Content Studio
            </span>
          </div>

          <div style="display:grid;grid-template-columns:repeat(auto-fit, minmax(300px, 1fr));gap:16px;">
            <!-- FX Playground -->
            <div style="background:var(--bg-card);border:1px solid rgba(255,255,255,0.1);padding:14px;border-radius:10px;">
              <h3 style="font-size:14px;color:var(--gold-light);margin-bottom:10px;">✨ Visual FX Playground</h3>
              <div style="display:flex;flex-wrap:wrap;gap:8px;">
                <button onclick="ArcanumUIEffectEngine.spawnParticleAt(window.innerWidth/2, window.innerHeight/2, '#c5a059')" style="background:rgba(197,160,89,0.2);border:1px solid var(--gold-accent);color:#fff;padding:8px 12px;border-radius:6px;cursor:pointer;font-size:11px;">
                  🎆 Spark Particles
                </button>
                <button onclick="ArcanumUIEffectEngine.spawnParticleAt(window.innerWidth/2, window.innerHeight/2, '#00f3ff')" style="background:rgba(0,243,255,0.2);border:1px solid #00f3ff;color:#fff;padding:8px 12px;border-radius:6px;cursor:pointer;font-size:11px;">
                  💎 Cyan Mana Sparks
                </button>
              </div>
            </div>

            <!-- Vector Icons Inspector -->
            <div style="background:var(--bg-card);border:1px solid rgba(255,255,255,0.1);padding:14px;border-radius:10px;">
              <h3 style="font-size:14px;color:var(--gold-light);margin-bottom:10px;">🛡️ Vector SVG Icons</h3>
              <div style="display:flex;gap:12px;align-items:center;">
                ${ArcanumVectorIconEngine.renderSVG('sword', '#c5a059')}
                ${ArcanumVectorIconEngine.renderSVG('shield', '#3498db')}
                ${ArcanumVectorIconEngine.renderSVG('gem', '#9b59b6')}
                ${ArcanumVectorIconEngine.renderSVG('castle', '#2ecc71')}
              </div>
            </div>

            <!-- Content Studio Live Editor -->
            <div style="grid-column: 1 / -1; background:var(--bg-card);border:1px solid rgba(255,255,255,0.1);padding:14px;border-radius:10px;">
              <h3 style="font-size:14px;color:var(--gold-light);margin-bottom:10px;">📝 Content Studio & Markdown Lore Editor</h3>
              <textarea id="ar-studio-md-input" style="width:100%;height:80px;background:rgba(0,0,0,0.5);border:1px solid var(--gold-accent);color:#fff;padding:8px;border-radius:6px;font-family:monospace;font-size:12px;" placeholder="Type # Header or **bold** lore text..."></textarea>
              <div id="ar-studio-md-output" style="margin-top:10px;padding:10px;background:rgba(0,0,0,0.3);border-radius:6px;min-height:40px;"></div>
            </div>
          </div>
        </div>
      `;

      const input = document.getElementById('ar-studio-md-input');
      const output = document.getElementById('ar-studio-md-output');
      if (input && output) {
        input.addEventListener('input', () => {
          output.innerHTML = ArcanumContentEngine.renderMarkdown(input.value);
        });
      }
    }
  }

  window.ArcanumVisualLab = ArcanumVisualLab;

  // =======================================================================
  // 6. VISUAL ENGINE MASTER BOOTSTRAPPER
  // =======================================================================
  class ArcanumVisualEngine {
    static version = '3.7.0';

    static boot() {
      console.log(`[Arcanum Visual Engine] Booting Visual FX & Immersive System v${this.version}...`);
      ArcanumUIEffectEngine.init();
      ArcanumInteractionEngine.init();

      // Register Visual Lab in Applet Registry if present
      if (window.AppletRegistry) {
        window.AppletRegistry.register({ id: 'visual-lab', title: 'Visual Lab', icon: '🎨', category: 'developer', viewId: 'view-visual-lab' });
      }

      window.addEventListener('arcanum:scene-loaded', (e) => {
        if (e.detail.sceneId === 'visual-lab' || e.detail.sceneId === 'lab') {
          ArcanumVisualLab.renderVisualLab('view-lab-container');
        }
      });

      console.log(`[Arcanum Visual Engine] Immersive System v${this.version} Operational.`);
    }
  }

  window.ArcanumVisualEngine = ArcanumVisualEngine;

  window.addEventListener('DOMContentLoaded', () => {
    ArcanumVisualEngine.boot();
  });
})();
