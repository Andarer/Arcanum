/**
 * Arcanum Interactive Documentation Reader & Architecture Visualizer Engine v2.4
 * Renders all 18 platform specification documents into interactive gaming UI screens with
 * full-text search, Table of Contents, Architecture Node Graph, and Version Comparison.
 */

(function () {
  'use strict';

  // Embedded Specification Documents Registry
  const DOCS_DATABASE = {
    'AI_CONSTITUTION.md': {
      title: '📜 AI Constitution v2.4',
      category: 'AI & Governance',
      content: `# Arcanum Evolution Meta AI Constitution v2.4

## Principle 1: Continuous Platform Evolution
The Meta AI Architect operates as a perpetual engine of development, continuously elevating the Arcanum Evolution codebase, architecture, UI/UX, documentation, and prompt workflows.

## Principle 2: Zero Regression & Backward Compatibility
All updates must be additive and evolutionary. Breaking core contracts is forbidden unless explicitly required for a major architectural transition.

## Principle 3: Documentation as First-Class Code
Documentation is synchronized automatically alongside code changes across all specification documents.

## Principle 4: PWA as Canonical Primary Core
The PWA (\`/app/src/main/assets/pwa/\`) remains the primary, universal application client. The Android layer acts as a native wrapper container shell.

## Principle 5: Omnipresent Cross-Platform Experience
Arcanum Evolution presents an identical, immersive gaming OS experience on Mobile, PWA, Desktop, Tablet, Smart TV, Console, and Foldable runtimes.`
    },
    'ARCHITECTURE.md': {
      title: '🏛️ System Architecture v2.4',
      category: 'Architecture',
      content: `# Arcanum Engine Architecture Specification v2.4

## Vision: "PERPETUAL META AI ARCHITECT. PWA CANONICAL CORE. ONE ENGINE. INFINITE WORLDS."

Arcanum Evolution is a universal digital platform designed to assemble Card RPGs, MMORPGs, Shooters, Quests, Sandboxes, Clickers, and Arcanum Studio tools across Android, PWA, Web, Desktop, Tablet, TV, Console, and Foldable runtimes using a shared fractal micro-module architecture, dynamic Render Profiles, procedural sound synthesis, living particle atmosphere, and the Arcanum Link Protocol (ALP).

### Primary System Modules
- **PWA Canonical Core**: HTML5 / ES2025+ Offline First PWA
- **Android Native Container**: WebView + Native Bridge
- **Atmosphere Particle Canvas**: 2D Ambient particle engine
- **Procedural Sound Engine**: WebAudio spatial sound generator
- **Arcanum UI Web Components**: 27 custom gaming elements
- **Arcanum Link Protocol (ALP v2.0)**: Universal QR / Link payload exchanger`
    },
    'PWA.md': {
      title: '🌐 PWA Core Specification v2.4',
      category: 'Platform',
      content: `# Arcanum PWA Primary Client Specification v2.4

The **PWA (Progressive Web App)** is the canonical primary client of Arcanum Evolution.

### Features
- Service Worker v2.4 offline-first caching
- IndexedDB & LocalStorage save persistence
- 13 dynamic Render Profiles
- 27 Arcanum UI Custom Web Components
- Cross-platform input engine (Touch, Mouse, Keyboard, Gamepad)`
    },
    'ANDROID.md': {
      title: '📱 Android Native Shell v2.4',
      category: 'Platform',
      content: `# Arcanum Android Container Specification v2.4

Android native app operates as a high-performance wrapper shell wrapping the PWA inside Jetpack Compose and WebView.

### Native Bridge Methods
- \`vibrate(ms)\`: Device haptic vibration feedback
- \`showToast(msg)\`: Native Android Toast
- \`getDeviceInfo()\`: Android SDK & hardware details`
    },
    'COMPONENTS.md': {
      title: '🧩 Web Components Catalog v2.6',
      category: 'Design System',
      content: `# Arcanum UI Web Components Catalog v2.6

Features 32 custom gaming elements:
\`<ar-button>\`, \`<ar-panel>\`, \`<ar-card>\`, \`<ar-inventory>\`, \`<ar-dialog>\`, \`<ar-dialogue>\`, \`<ar-quest>\`, \`<ar-window>\`, \`<ar-map>\`, \`<ar-character>\`, \`<ar-world>\`, \`<ar-menu>\`, \`<ar-hud>\`, \`<ar-notification>\`, \`<ar-modal>\`, \`<ar-toast>\`, \`<ar-context-menu>\`, \`<ar-tooltip>\`, \`<ar-craft>\`, \`<ar-battle>\`, \`<ar-editor>\`, \`<ar-dock>\`, \`<ar-sidebar>\`, \`<ar-toolbar>\`, \`<ar-status-bar>\`, \`<ar-console>\`, \`<ar-settings>\`, \`<ar-library>\`, \`<ar-marketplace>\`, \`<ar-git>\`, \`<ar-ai>\`, \`<ar-explorer>\`.`
    },
    'MODULES.md': {
      title: '📦 Micro-Modules Catalog v2.4',
      category: 'Architecture',
      content: `# Decoupled Micro-Modules Catalog v2.4

- **BattleModule**: Turn-based Card RPG combat & spell casting
- **CardsModule**: Collectible card decks & elemental affinities
- **InventoryModule**: Slots, gold, gems, and craft materials
- **QuestModule**: Campaign quests & world stage progression
- **SaveSyncModule**: LocalStorage & IndexedDB state persistence`
    },
    'ALP_PROTOCOL.md': {
      title: '🔗 Arcanum Link Protocol v2.0',
      category: 'Protocols',
      content: `# Arcanum Link Protocol (ALP v2.0) Specification

Unified payload format for sharing cards, saves, and world nodes across devices via QR codes or \`arcanum://link\` universal URLs.`
    },
    'ROADMAP.md': {
      title: '🗺️ Platform Evolution Roadmap',
      category: 'Roadmap',
      content: `# Arcanum Platform Evolution Roadmap

- **Phase 1-3**: Kotlin/JS Engine, Arcanum Studio, ALP Protocol (Completed)
- **Phase 4-5**: PWA Primary Core, Gamepad & TV Focus, 23 Web Components (Completed)
- **Phase 6**: Atmosphere Particle Canvas, WebAudio Sound Synthesizer (Completed)
- **Phase 7**: Meta AI Architect Protocol, 18-Doc Architecture Suite (Completed)
- **Phase 8 OMEGA**: Interactive PWA Documentation Reader & Architecture Visualizer (Completed)`
    }
  };

  class ArcanumDocsEngine {
    static activeDocKey = 'AI_CONSTITUTION.md';

    static renderDocList(searchQuery = '') {
      const container = document.getElementById('docs-list-container');
      if (!container) return;

      let html = '<div style="display: flex; flex-direction: column; gap: 6px;">';
      
      Object.keys(DOCS_DATABASE).forEach((key) => {
        const doc = DOCS_DATABASE[key];
        const matchesSearch = !searchQuery || 
          doc.title.toLowerCase().includes(searchQuery.toLowerCase()) || 
          doc.content.toLowerCase().includes(searchQuery.toLowerCase());

        if (!matchesSearch) return;

        const isActive = key === this.activeDocKey;
        const activeBg = isActive ? 'background: var(--gold-accent); color: #000; font-weight: bold;' : 'background: rgba(255,255,255,0.05); color: var(--text-light);';

        html += `
          <button class="btn-action" style="${activeBg} text-align: left; padding: 10px 14px; font-size: 12px; width: 100%; border-radius: 6px;" onclick="ArcanumDocsEngine.openDoc('${key}')">
            ${doc.title}
            <div style="font-size: 10px; opacity: 0.8; margin-top: 2px;">📁 ${doc.category}</div>
          </button>
        `;
      });

      html += '</div>';
      container.innerHTML = html;
    }

    static openDoc(key) {
      if (!DOCS_DATABASE[key]) return;
      this.activeDocKey = key;
      this.renderDocList(document.getElementById('docs-search-input')?.value || '');
      this.renderDocContent(key);
      if (window.ArcanumAudioEngine) window.ArcanumAudioEngine.playCardFlip();
    }

    static renderDocContent(key) {
      const displayArea = document.getElementById('docs-display-area');
      if (!displayArea) return;

      const doc = DOCS_DATABASE[key];
      let htmlContent = this.markdownToHtml(doc.content);

      displayArea.innerHTML = `
        <div style="background: rgba(16, 18, 26, 0.95); border: 1.5px solid var(--gold-accent); border-radius: 12px; padding: 20px; box-shadow: 0 8px 24px rgba(0,0,0,0.6);">
          <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid rgba(212,175,55,0.3); padding-bottom: 10px; margin-bottom: 16px;">
            <h2 style="color: var(--gold-light); font-size: 18px; margin: 0;">${doc.title}</h2>
            <span class="carousel-badge" style="background: var(--purple-accent);">${doc.category}</span>
          </div>
          <div class="markdown-body" style="font-size: 13px; line-height: 1.6; color: var(--text-light);">
            ${htmlContent}
          </div>
        </div>
      `;
    }

    static markdownToHtml(md) {
      let html = md
        .replace(/^### (.*$)/gim, '<h3 style="color: var(--gold-light); margin-top: 14px; margin-bottom: 6px; font-size: 14px;">$1</h3>')
        .replace(/^## (.*$)/gim, '<h2 style="color: var(--gold-accent); border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 4px; margin-top: 18px; margin-bottom: 8px; font-size: 16px;">$1</h2>')
        .replace(/^# (.*$)/gim, '<h1 style="color: var(--gold-light); font-size: 18px; margin-bottom: 12px;">$1</h1>')
        .replace(/\*\*(.* vast?)\*\*/gim, '<strong>$1</strong>')
        .replace(/\*\*(.*?)\*\*/gim, '<strong style="color: var(--gold-light);">$1</strong>')
        .replace(/`([^`]+)`/gim, '<code style="background: rgba(255,255,255,0.1); padding: 2px 6px; border-radius: 4px; color: #00ffcc; font-family: monospace;">$1</code>')
        .replace(/^\- (.*$)/gim, '<li style="margin-left: 18px; list-style-type: square; margin-bottom: 4px;">$1</li>')
        .replace(/\n\n/gim, '<br/><br/>');
      return html;
    }

    static renderArchitectureGraph() {
      const graphCanvas = document.getElementById('arch-graph-canvas');
      if (!graphCanvas) return;
      const ctx = graphCanvas.getContext('2d');
      if (!ctx) return;

      graphCanvas.width = graphCanvas.parentElement.clientWidth || 600;
      graphCanvas.height = 260;

      ctx.clearRect(0, 0, graphCanvas.width, graphCanvas.height);

      // Nodes
      const nodes = [
        { id: 'pwa', label: 'PWA Core', x: 80, y: 130, color: '#f3e5ab' },
        { id: 'ui', label: '27 Web Components', x: 220, y: 60, color: '#9b51e0' },
        { id: 'audio', label: 'WebAudio Synth', x: 220, y: 130, color: '#27ae60' },
        { id: 'atm', label: 'Atmosphere FX', x: 220, y: 200, color: '#e67e22' },
        { id: 'android', label: 'Android Shell', x: 380, y: 80, color: '#2980b9' },
        { id: 'alp', label: 'ALP Protocol', x: 380, y: 180, color: '#e74c3c' },
        { id: 'github', label: 'GitHub CI/CD', x: 500, y: 130, color: '#f1c40f' }
      ];

      // Draw Edges
      const connections = [
        ['pwa', 'ui'], ['pwa', 'audio'], ['pwa', 'atm'],
        ['ui', 'android'], ['audio', 'android'],
        ['pwa', 'alp'], ['alp', 'github'], ['android', 'github']
      ];

      ctx.strokeStyle = 'rgba(212, 175, 55, 0.4)';
      ctx.lineWidth = 1.5;

      connections.forEach(([fromId, toId]) => {
        const fromNode = nodes.find(n => n.id === fromId);
        const toNode = nodes.find(n => n.id === toId);
        if (fromNode && toNode) {
          ctx.beginPath();
          ctx.moveTo(fromNode.x, fromNode.y);
          ctx.lineTo(toNode.x, toNode.y);
          ctx.stroke();
        }
      });

      // Draw Node Circles
      nodes.forEach((node) => {
        ctx.fillStyle = node.color;
        ctx.beginPath();
        ctx.arc(node.x, node.y, 18, 0, Math.PI * 2);
        ctx.fill();

        ctx.strokeStyle = '#ffffff';
        ctx.lineWidth = 1.5;
        ctx.stroke();

        ctx.fillStyle = '#ffffff';
        ctx.font = '10px sans-serif';
        ctx.textAlign = 'center';
        ctx.fillText(node.label, node.x, node.y + 32);
      });
    }
  }

  window.ArcanumDocsEngine = ArcanumDocsEngine;
})();
