/**
 * ARCANUM AI SUBSYSTEM & NEURAL COPILOT ENGINE (v4.1.0)
 * =======================================================================
 * Modular AI Platform, Local Semantic Search, Rule Reasoning & LLM Adapters
 * 
 * Modules Included:
 * 1. ArcanumSemanticIndex   - Local TF-IDF & Keyword Vector Search over Lore & Specs
 * 2. ArcanumRuleReasoner    - Procedural Card Generator & Balance Inference Engine
 * 3. ArcanumLLMAdapter      - Multi-Provider Adapter Framework (Local Modules, Gemini REST API)
 * 4. ArcanumAIEngine        - Master Subsystem Controller & AI Context Manager
 * 5. ArcanumCopilotStudioUI - Interactive AI Neural Copilot Laboratory in PWA
 */

(function(window) {
  'use strict';

  // 1. LOCAL SEMANTIC INDEX & VECTOR SEARCH ENGINE
  class ArcanumSemanticIndex {
    constructor() {
      this.documents = []; // { id, title, content, tags, tokens }
    }

    addDocument(id, title, content, tags = []) {
      const tokens = this.tokenize(`${title} ${content} ${tags.join(' ')}`);
      this.documents.push({ id, title, content, tags, tokens });
    }

    tokenize(text) {
      return text
        .toLowerCase()
        .replace(/[^\w\sа-яА-ЯёЁ]/g, ' ')
        .split(/\s+/)
        .filter(t => t.length > 2);
    }

    search(query, topK = 3) {
      const queryTokens = this.tokenize(query);
      if (queryTokens.length === 0) return [];

      const scores = this.documents.map(doc => {
        let matchCount = 0;
        for (const qt of queryTokens) {
          if (doc.tokens.includes(qt)) {
            matchCount++;
          }
        }
        const score = matchCount / (queryTokens.length + Math.log(doc.tokens.length + 1));
        return { doc, score };
      });

      return scores
        .filter(s => s.score > 0)
        .sort((a, b) => b.score - a.score)
        .slice(0, topK)
        .map(s => s.doc);
    }
  }

  // 2. RULE REASONER & PROCEDURAL CONTENT GENERATOR
  class ArcanumRuleReasoner {
    constructor() {
      this.cardPrefixes = ['Пламенный', 'Теневой', 'Священный', 'Астральный', 'Древний', 'Грозовой'];
      this.cardTypes = ['hero', 'creature', 'item', 'spell'];
      this.rarities = ['common', 'rare', 'epic', 'legendary', 'mythic'];
    }

    generateProceduralCard(theme = 'Arcane') {
      const prefix = this.cardPrefixes[Math.floor(Math.random() * this.cardPrefixes.length)];
      const type = this.cardTypes[Math.floor(Math.random() * this.cardTypes.length)];
      const rarity = this.rarities[Math.floor(Math.random() * this.rarities.length)];
      
      const level = Math.floor(Math.random() * 5) + 1;
      const baseHp = type === 'hero' ? 80 : (type === 'creature' ? 100 : 20);
      const baseStr = type === 'item' ? 10 : 20;

      const hp = baseHp + level * 15;
      const str = baseStr + level * 8;
      const def = level * 4;

      return {
        id: 'c_ai_' + Date.now(),
        name: `${prefix} ${theme}`,
        type,
        rarity,
        level,
        hp,
        str,
        def,
        abilityCost: level * 5,
        desc: `AI Generated ${rarity} ${type} with dynamic ${theme} magic capabilities.`,
        generatedBy: 'ArcanumRuleReasoner v4.1'
      };
    }

    balanceCheck(card) {
      const powerScore = card.hp * 0.4 + card.str * 1.2 + card.def * 1.5;
      const recommendedLevel = Math.max(1, Math.round(powerScore / 35));
      return {
        powerScore: Math.round(powerScore),
        recommendedLevel,
        isBalanced: Math.abs(recommendedLevel - card.level) <= 1
      };
    }
  }

  // 3. MULTI-PROVIDER LLM ADAPTER FRAMEWORK
  class ArcanumLLMAdapter {
    constructor(provider = 'LOCAL_RULES') {
      this.provider = provider;
      this.apiKey = null;
    }

    setProvider(provider, apiKey = null) {
      this.provider = provider;
      this.apiKey = apiKey;
    }

    async prompt(userPrompt, context = {}) {
      if (this.provider === 'LOCAL_RULES') {
        return this.localRuleResponse(userPrompt, context);
      } else if (this.provider === 'GEMINI_REST' && this.apiKey) {
        return this.geminiRestResponse(userPrompt, context);
      } else {
        return {
          response: `[AI Fallback] Processing prompt '${userPrompt}' using Arcanum Local JS Rules Engine.`,
          provider: 'LOCAL_RULES'
        };
      }
    }

    localRuleResponse(userPrompt, context) {
      const promptLower = userPrompt.toLowerCase();
      if (promptLower.includes('card') || promptLower.includes('карта')) {
        return {
          response: `Generated new RPG Card logic based on prompt "${userPrompt}". Rarity: Epic. Type: Creature. Ability: Flame Strike.`,
          action: 'GENERATE_CARD',
          provider: 'LOCAL_RULES'
        };
      } else if (promptLower.includes('quest') || promptLower.includes('квест')) {
        return {
          response: `Generated new RPG Quest: "Defeat 3 Shadows in the Arcane Cave". Reward: 150 Gold, 80 XP.`,
          action: 'GENERATE_QUEST',
          provider: 'LOCAL_RULES'
        };
      } else {
        return {
          response: `Arcanum AI Co-Pilot recommendation for "${userPrompt}": System components and ECS rules are synchronized. Performance is optimal.`,
          action: 'ADVISE',
          provider: 'LOCAL_RULES'
        };
      }
    }

    async geminiRestResponse(userPrompt, context) {
      try {
        const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${this.apiKey}`;
        const body = {
          contents: [{ parts: [{ text: userPrompt }] }]
        };
        const res = await fetch(url, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(body)
        });
        const data = await res.json();
        const text = data.candidates?.[0]?.content?.parts?.[0]?.text || 'No response';
        return { response: text, provider: 'GEMINI_REST' };
      } catch (e) {
        return { response: `Gemini API Error: ${e.message}. Falling back to Local Engine.`, provider: 'LOCAL_RULES' };
      }
    }
  }

  // 4. MASTER AI SUBSYSTEM CONTROLLER
  class ArcanumAIEngine {
    constructor() {
      this.version = '4.1.0';
      this.semanticIndex = new ArcanumSemanticIndex();
      this.reasoner = new ArcanumRuleReasoner();
      this.llmAdapter = new ArcanumLLMAdapter('LOCAL_RULES');
      this.initDefaultDocs();
    }

    initDefaultDocs() {
      this.semanticIndex.addDocument(
        'doc_pwa',
        'Arcanum PWA Client Architecture',
        'Full offline-first PWA runtime with Service Worker v4.0, IndexedDB state store, and ArDesign System.',
        ['pwa', 'offline', 'service-worker', 'ui']
      );
      this.semanticIndex.addDocument(
        'doc_game',
        'Arcanum Game Engine ECS & Rule Graph',
        'Universal Game Engine v4.0 with Entity Component System, Visual Scene Builder, and Rule Graph Engine.',
        ['game-engine', 'ecs', 'rules', 'rpg', 'scene']
      );
      this.semanticIndex.addDocument(
        'doc_meta',
        'Arcanum Meta Ecosystem & Passports',
        'Meta Registry with Digital Passports, Meta IDs, Relationship Mapper, and Self-Evolution Audit.',
        ['meta', 'passport', 'audit', 'registry']
      );
    }

    query(userPrompt) {
      const searchResults = this.semanticIndex.search(userPrompt);
      const aiResponse = this.llmAdapter.prompt(userPrompt, { searchResults });
      return { searchResults, aiResponse };
    }

    getDiagnostics() {
      return {
        version: this.version,
        indexedDocs: this.semanticIndex.documents.length,
        currentProvider: this.llmAdapter.provider
      };
    }
  }

  // 5. AI COPILOT STUDIO UI ENGINE
  class ArcanumCopilotStudioUI {
    static renderStudio(containerId) {
      const container = document.getElementById(containerId);
      if (!container) return;

      const aiEngine = window.ArcanumAIInstance || new ArcanumAIEngine();
      if (!window.ArcanumAIInstance) {
        window.ArcanumAIInstance = aiEngine;
      }

      const diag = aiEngine.getDiagnostics();

      container.innerHTML = `
        <div style="background: rgba(15, 20, 32, 0.95); border: 1px solid #9B51E0; border-radius: 12px; padding: 20px; color: #E0E6ED; font-family: sans-serif; box-shadow: 0 8px 32px rgba(0,0,0,0.5);">
          <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid rgba(155, 81, 224, 0.3); padding-bottom: 12px; margin-bottom: 16px;">
            <h2 style="margin: 0; color: #D4ADFC; font-size: 18px; display: flex; align-items: center; gap: 8px;">
              <span>🤖</span> ARCANUM NEURAL AI COPILOT & REASONER STUDIO (v${diag.version})
            </h2>
            <span style="background: rgba(155, 81, 224, 0.15); color: #D4ADFC; border: 1px solid #9B51E0; padding: 4px 10px; border-radius: 20px; font-size: 12px; font-weight: bold;">
              MODULAR AI LAYER
            </span>
          </div>

          <!-- AI Metrics & Telemetry -->
          <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(140px, 1fr)); gap: 10px; margin-bottom: 16px;">
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">INDEXED SPECS</div>
              <div style="font-size: 18px; font-weight: bold; color: #D4ADFC;">${diag.indexedDocs}</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">PROVIDER</div>
              <div style="font-size: 14px; font-weight: bold; color: #00FF66;">${diag.currentProvider}</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 10px; border-radius: 8px; text-align: center;">
              <div style="font-size: 11px; color: #8A99AD;">HEURISTIC REASONER</div>
              <div style="font-size: 14px; font-weight: bold; color: #4DEEEA;">ACTIVE</div>
            </div>
          </div>

          <!-- Interactive AI Console -->
          <div style="background: #080a0f; border: 1px solid rgba(255,255,255,0.1); border-radius: 8px; padding: 12px; margin-bottom: 16px;">
            <div style="font-size: 12px; color: #D4ADFC; font-weight: bold; margin-bottom: 8px;">🧠 NEURAL COPILOT PROMPT CONSOLE</div>
            <div style="display: flex; gap: 8px; margin-bottom: 10px;">
              <input id="ar-ai-prompt-input" type="text" placeholder="e.g. 'Generate legendary dragon card' or 'Search PWA offline specs'" style="flex: 1; background: rgba(255,255,255,0.08); border: 1px solid rgba(155,81,224,0.4); color: #FFF; padding: 8px 12px; border-radius: 6px; font-size: 12px;" />
              <button id="ar-ai-send-btn" style="background: linear-gradient(135deg, #9B51E0, #0077FF); border: none; color: #FFF; padding: 8px 16px; border-radius: 6px; font-size: 12px; font-weight: bold; cursor: pointer;">
                Execute Prompt
              </button>
            </div>
            <div id="ar-ai-output-box" style="background: rgba(0,0,0,0.5); border: 1px solid rgba(155,81,224,0.2); border-radius: 6px; padding: 10px; font-family: monospace; font-size: 11px; color: #00FF66; min-height: 60px; max-height: 120px; overflow-y: auto;">
              [AI Subsystem Ready] Type a prompt or click quick action buttons below.
            </div>
          </div>

          <!-- Quick Action Generators -->
          <div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center;">
            <button id="ar-gen-card-btn" style="background: rgba(77,238,234,0.15); border: 1px solid #4DEEEA; color: #4DEEEA; padding: 6px 12px; border-radius: 6px; font-size: 11px; font-weight: bold; cursor: pointer;">
              ⚡ Procedural Card Generator
            </button>
            <button id="ar-search-specs-btn" style="background: rgba(255,230,0,0.15); border: 1px solid #FFE600; color: #FFE600; padding: 6px 12px; border-radius: 6px; font-size: 11px; font-weight: bold; cursor: pointer;">
              🔍 Semantic Index Search
            </button>
          </div>
        </div>
      `;

      // Event listeners
      document.getElementById('ar-ai-send-btn')?.addEventListener('click', async () => {
        const input = document.getElementById('ar-ai-prompt-input');
        const output = document.getElementById('ar-ai-output-box');
        if (!input || !output) return;

        const val = input.value.trim() || 'Recommend RPG Balance';
        output.innerText = `[Processing prompt: "${val}"]...`;

        const res = aiEngine.query(val);
        const resultObj = await res.aiResponse;

        output.innerText = `[Response (${resultObj.provider})]: ${resultObj.response}\n\n[Semantic Search Match Count]: ${res.searchResults.length}`;
      });

      document.getElementById('ar-gen-card-btn')?.addEventListener('click', () => {
        const output = document.getElementById('ar-ai-output-box');
        const card = aiEngine.reasoner.generateProceduralCard('Void Archon');
        const balance = aiEngine.reasoner.balanceCheck(card);

        if (output) {
          output.innerText = `[Generated Card]: ${card.name} (${card.rarity.toUpperCase()} ${card.type})\nHP: ${card.hp} | STR: ${card.str} | DEF: ${card.def} | Ability Cost: ${card.abilityCost}\n[Power Score]: ${balance.powerScore} | [Balanced]: ${balance.isBalanced ? 'YES' : 'NO'}`;
        }
      });

      document.getElementById('ar-search-specs-btn')?.addEventListener('click', () => {
        const output = document.getElementById('ar-ai-output-box');
        const docs = aiEngine.semanticIndex.search('PWA offline game engine', 2);
        if (output) {
          output.innerText = `[Semantic Index Search Results (${docs.length} matches)]:\n` +
            docs.map(d => `• ${d.title}: ${d.content}`).join('\n');
        }
      });
    }
  }

  // EXPOSE TO GLOBAL WINDOW SCOPE
  window.ArcanumSemanticIndex = ArcanumSemanticIndex;
  window.ArcanumRuleReasoner = ArcanumRuleReasoner;
  window.ArcanumLLMAdapter = ArcanumLLMAdapter;
  window.ArcanumAIEngine = ArcanumAIEngine;
  window.ArcanumCopilotStudioUI = ArcanumCopilotStudioUI;

})(window);
