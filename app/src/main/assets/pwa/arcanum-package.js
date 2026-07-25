/**
 * Arcanum Package Engine, Explainability & Academy Suite v3.1 (Meta Constitution Era)
 * Implements .apkg Package Format Manager, Living Universe Map, Explainability Engine,
 * Embedded Arcanum Academy, and Experimental Feature Laboratory.
 */

(function () {
  'use strict';

  // Installed Arcanum Packages Registry (.apkg)
  const INSTALLED_PACKAGES = [
    { id: 'pkg.core.combat', name: 'Elemental Combat System', version: '3.1.0', author: 'Arcanum Architect', type: 'Core Module', apkg: 'combat_v3.1.apkg', status: 'Active', desc: 'Turn-based card spell battles and elemental elemental affinities.' },
    { id: 'pkg.ui.themes', name: 'Omni Render Profiles Pack (13)', version: '3.1.0', author: 'Arcanum Studio', type: 'UI Theme Pack', apkg: 'render_profiles.apkg', status: 'Active', desc: '13 theme styling engines including Cyberpunk, Glass, and PlayStation.' },
    { id: 'pkg.audio.procedural', name: 'WebAudio Synthesizer Engine', version: '3.1.0', author: 'Sound Lab', type: 'Audio Pack', apkg: 'webaudio_synth.apkg', status: 'Active', desc: 'Zero-file spatial audio synthesis for clicks, strikes, and chimes.' },
    { id: 'pkg.docs.knowledge', name: 'Interactive Specs Knowledge Base', version: '3.1.0', author: 'AI Council', type: 'Knowledge Pack', apkg: 'specs_knowledge.apkg', status: 'Active', desc: '18 synchronized specification markdown documents.' }
  ];

  // Living Universe Map Hierarchy Data
  const UNIVERSE_MAP = {
    title: '🌌 Arcanum Living Digital Universe Map',
    nodes: [
      { id: 'u_universe', name: 'Arcanum Ecosystem Universe', level: 'Universe', desc: 'Primary digital gaming operating system ecosystem.' },
      { id: 'u_platform', name: 'Arcanum Evolution Kernel Platform', level: 'Platform', desc: 'Microkernel architecture runtime and module lifecycle engine.' },
      { id: 'u_client', name: 'PWA Canonical Primary Client', level: 'Client', desc: '100% Offline-First Web runtime and Service Worker SW-v3.1.' },
      { id: 'u_modules', name: 'Micro-Module Engine Suite (32)', level: 'Modules', desc: '32 Ar* Web Components, Particle Canvas, WebAudio, and ALP v2.0.' },
      { id: 'u_components', name: 'Web Components Catalog', level: 'Component', desc: '<ar-button>, <ar-card>, <ar-library>, <ar-git>, <ar-ai>, etc.' },
      { id: 'u_class', name: 'Kernel & UI Classes', level: 'Class', desc: 'ArcanumKernelEngine, ArcanumDocsEngine, ArcanumPackageEngine.' },
      { id: 'u_events', name: 'EventBus Messaging Layer', level: 'Event', desc: 'Decoupled pub/sub event emitters for cards, combat, and tabs.' },
      { id: 'u_docs', name: '18 Platform Specifications', level: 'Line of Docs', desc: 'Self-documenting specification suite in /docs/ai/ & project root.' }
    ]
  };

  // Academy Courses & Practice Sandboxes
  const ACADEMY_COURSES = [
    { id: 'acc_01', title: '🎓 Course 1: Microkernel Architecture & Module Contracts', level: 'Beginner', duration: '15 min', topic: 'Decoupled EventBus and .apkg package registry.' },
    { id: 'acc_02', title: '🎨 Course 2: Building Ar* Custom Web Components', level: 'Intermediate', duration: '20 min', topic: 'Creating custom gaming UI elements and Render Profiles.' },
    { id: 'acc_03', title: '🎵 Course 3: WebAudio Spatial Sound Synthesis', level: 'Advanced', duration: '25 min', topic: 'Generating UI and combat frequency tones procedurally.' }
  ];

  // Experimental Laboratory Features Toggles
  const LAB_EXPERIMENTS = [
    { id: 'exp_01', name: '⚡ OffscreenCanvas Multithreaded Particles', status: 'Disabled', desc: 'Offload particle physics render loops to Web Worker thread.' },
    { id: 'exp_02', name: '🌐 WebRTC Direct Peer-to-Peer Mesh Sync', status: 'Prototype', desc: 'Direct browser-to-browser card trading without external server.' },
    { id: 'exp_03', name: '🧠 On-Device WebLLM AI Companion', status: 'Experimental', desc: 'Local WebGPU LLM inference for procedural NPC quests.' }
  ];

  class ArcanumPackageEngine {
    static renderPackageManagerDashboard() {
      const container = document.getElementById('package-manager-container');
      if (!container) return;

      let html = `
        <div style="background: rgba(16,18,26,0.95); border: 1.5px solid var(--gold-accent); border-radius: 12px; padding: 18px; margin-bottom: 20px;">
          <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid rgba(212,175,55,0.3); padding-bottom: 10px; margin-bottom: 16px;">
            <div>
              <h2 style="color: var(--gold-light); font-size: 18px; margin: 0;">📦 ARCANUM PACKAGE MANAGER (.apkg v3.1)</h2>
              <div style="font-size: 11px; color: var(--text-muted); margin-top: 2px;">Модульный реестр пакетов с принципом вечной совместимости и изолированности.</div>
            </div>
            <button class="btn-action" style="background: var(--gold-accent); color: #000; font-weight: bold; font-size: 12px; padding: 8px 14px; border-radius: 6px;" onclick="ArcanumPackageEngine.installPackagePrompt()">+ Установить .apkg Пакет</button>
          </div>

          <!-- Package Cards List -->
          <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 14px;">
      `;

      INSTALLED_PACKAGES.forEach(pkg => {
        html += `
          <div style="background: rgba(0,0,0,0.5); border: 1px solid rgba(212,175,55,0.4); border-radius: 10px; padding: 14px; display: flex; flex-direction: column; justify-content: space-between;">
            <div>
              <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px;">
                <strong style="color: var(--gold-light); font-size: 13px;">${pkg.name}</strong>
                <span style="font-size: 9px; background: rgba(0,255,204,0.15); color: #00ffcc; border: 1px solid #00ffcc; padding: 2px 6px; border-radius: 4px;">v${pkg.version}</span>
              </div>
              <div style="font-size: 11px; color: var(--text-muted); margin-bottom: 8px;">ID: <code>${pkg.id}</code> | Author: ${pkg.author}</div>
              <div style="font-size: 11.5px; color: var(--text-light); line-height: 1.4; margin-bottom: 12px;">${pkg.desc}</div>
            </div>
            <div style="display: flex; gap: 8px;">
              <button class="btn-action" style="flex: 1; font-size: 11px; padding: 6px;" onclick="ArcanumPackageEngine.explainElement('${pkg.id}')">🔍 Инспекция</button>
              <button class="btn-action" style="background: rgba(239, 68, 68, 0.2); border: 1px solid #ef4444; color: #f87171; font-size: 11px; padding: 6px;" onclick="alert('Пакет ${pkg.name} изолирован. Для удаления нажмите Подтвердить.')">Удалить</button>
            </div>
          </div>
        `;
      });

      html += `
          </div>
        </div>

        <!-- Living Universe Map Section -->
        <div style="background: rgba(10, 14, 23, 0.95); border: 1px solid var(--purple-accent); border-radius: 12px; padding: 18px; margin-bottom: 20px;">
          <h3 style="color: var(--purple-accent); font-size: 15px; margin-bottom: 12px;">🗺️ ИНТЕРАКТИВНАЯ ЖИВАЯ КАРТА ВСЕЛЕННОЙ ARCANUM</h3>
          <div style="display: flex; flex-direction: column; gap: 8px;">
      `;

      UNIVERSE_MAP.nodes.forEach(node => {
        html += `
          <div style="background: rgba(255,255,255,0.03); border-left: 3px solid var(--purple-accent); border-radius: 6px; padding: 10px 14px; display: flex; justify-content: space-between; align-items: center;">
            <div>
              <div style="font-size: 12px; font-weight: bold; color: var(--gold-light);">${node.name} <span style="font-size: 10px; color: var(--purple-accent); font-weight: normal;">[${node.level}]</span></div>
              <div style="font-size: 11px; color: var(--text-muted); margin-top: 2px;">${node.desc}</div>
            </div>
            <button class="btn-action" style="font-size: 10px; padding: 4px 10px;" onclick="ArcanumPackageEngine.explainElement('${node.id}')">Детали</button>
          </div>
        `;
      });

      html += `
          </div>
        </div>

        <!-- Academy & Laboratory Section -->
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
          
          <!-- Academy -->
          <div style="background: rgba(16, 18, 26, 0.95); border: 1px solid var(--gold-accent); border-radius: 12px; padding: 16px;">
            <h3 style="color: var(--gold-light); font-size: 14px; margin-bottom: 10px;">🎓 ВСТРОЕННАЯ АКАДЕМИЯ ARCANUM</h3>
            <div style="display: flex; flex-direction: column; gap: 8px;">
      `;

      ACADEMY_COURSES.forEach(crs => {
        html += `
          <div style="background: rgba(255,255,255,0.03); border-left: 3px solid var(--gold-accent); padding: 8px 12px; border-radius: 6px; font-size: 11px;">
            <div style="display: flex; justify-content: space-between; margin-bottom: 2px;">
              <strong style="color: var(--gold-light);">${crs.title}</strong>
              <span style="color: #00ffcc;">${crs.duration}</span>
            </div>
            <div style="color: var(--text-muted);">${crs.topic}</div>
            <button class="btn-action" style="margin-top: 6px; font-size: 10px; padding: 3px 8px;" onclick="alert('Запуск курса: ${crs.title}')">▶ Начать Интерактивный Урок</button>
          </div>
        `;
      });

      html += `
            </div>
          </div>

          <!-- Laboratory -->
          <div style="background: rgba(16, 18, 26, 0.95); border: 1px solid var(--gold-accent); border-radius: 12px; padding: 16px;">
            <h3 style="color: var(--gold-light); font-size: 14px; margin-bottom: 10px;">🧪 ЭКСПЕРИМЕНТАЛЬНАЯ ЛАБОРАТОРИЯ</h3>
            <div style="display: flex; flex-direction: column; gap: 8px;">
      `;

      LAB_EXPERIMENTS.forEach(exp => {
        html += `
          <div style="background: rgba(255,255,255,0.03); border-left: 3px solid #00ffcc; padding: 8px 12px; border-radius: 6px; font-size: 11px;">
            <div style="display: flex; justify-content: space-between; margin-bottom: 2px;">
              <strong style="color: #00ffcc;">${exp.name}</strong>
              <span style="color: var(--gold-accent);">${exp.status}</span>
            </div>
            <div style="color: var(--text-muted); margin-bottom: 6px;">${exp.desc}</div>
            <button class="btn-action" style="font-size: 10px; padding: 3px 8px;" onclick="alert('Переключение эксперимента: ${exp.name}')">⚙️ Тумблер Опции</button>
          </div>
        `;
      });

      html += `
            </div>
          </div>

        </div>
      `;

      container.innerHTML = html;
    }

    static explainElement(elementId) {
      alert(`[ARCANUM EXPLAINABILITY ENGINE]\n\nЭлемент: ${elementId}\nНазначение: Модульный компонент единой платформы Arcanum.\nЗависимости: Arcanum Kernel EventBus.\nВлияние при удалении: Безопасно изолирован, ядро продолжит работу (Zero Rewrite).\nСовместимость: 100% Вечная Совместимость.`);
    }

    static installPackagePrompt() {
      alert('Установка .apkg пакета: Выберите .apkg файл или введите URL репозитория модулей Arcanum.');
    }
  }

  window.ArcanumPackageEngine = ArcanumPackageEngine;
})();
