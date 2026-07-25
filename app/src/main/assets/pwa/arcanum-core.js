/**
 * Arcanum Universal Game Engine - JavaScript Core Engine Kernel
 * Vision: ONE CORE. INFINITE WORLDS.
 * Fractal Micro-Module Architecture for Web / PWA Clients
 */

class EventBusJS {
  constructor() {
    this.listeners = new Map();
  }

  subscribe(eventType, listener) {
    if (!this.listeners.has(eventType)) {
      this.listeners.set(eventType, []);
    }
    this.listeners.get(eventType).push(listener);
  }

  unsubscribe(eventType, listener) {
    if (this.listeners.has(eventType)) {
      const list = this.listeners.get(eventType);
      const index = list.indexOf(listener);
      if (index !== -1) list.splice(index, 1);
    }
  }

  publish(eventType, payload = {}, sourceModuleId = "system") {
    const event = {
      type: eventType,
      sourceModuleId,
      payload,
      timestamp: Date.now()
    };
    if (this.listeners.has(eventType)) {
      this.listeners.get(eventType).forEach(listener => {
        try {
          listener(event);
        } catch (err) {
          console.error(`[ArcanumEngineJS] Event error in ${eventType}:`, err);
        }
      });
    }
  }
}

class ModuleManifestJS {
  constructor({ id, name, version = "1.0.0", description = "", author = "Arcanum Core", dependencies = [], eventsPublished = [], eventsSubscribed = [] }) {
    this.id = id;
    this.name = name;
    this.version = version;
    this.description = description;
    this.author = author;
    this.dependencies = dependencies;
    this.eventsPublished = eventsPublished;
    this.eventsSubscribed = eventsSubscribed;
  }
}

class BaseArcanumModuleJS {
  constructor(manifest) {
    this.manifest = new ModuleManifestJS(manifest);
    this.state = "UNINITIALIZED";
    this.context = null;
  }

  onRegister(context) {
    this.context = context;
    this.state = "REGISTERED";
  }

  onInit() {
    this.state = "INITIALIZED";
  }

  onEnable() {
    this.state = "ENABLED";
  }

  onDisable() {
    this.state = "DISABLED";
  }

  onDestroy() {
    this.state = "DESTROYED";
  }
}

class ModuleRegistryJS {
  constructor() {
    this.modules = new Map();
  }

  register(module, context) {
    if (this.modules.has(module.manifest.id)) return false;
    this.modules.set(module.manifest.id, module);
    module.onRegister(context);
    return true;
  }

  unregister(id) {
    const module = this.modules.get(id);
    if (!module) return false;
    if (module.state === "ENABLED") module.onDisable();
    module.onDestroy();
    this.modules.delete(id);
    return true;
  }

  getModule(id) {
    return this.modules.get(id);
  }

  getAllModules() {
    return Array.from(this.modules.values());
  }

  initializeAll() {
    this.modules.forEach(module => {
      if (module.state === "REGISTERED") {
        module.onInit();
        module.onEnable();
      }
    });
  }
}

class EngineContextJS {
  constructor() {
    this.eventBus = new EventBusJS();
    this.registry = new ModuleRegistryJS();
    this.globalStorage = new Map();
  }

  setGlobal(key, val) {
    this.globalStorage.set(key, val);
  }

  getGlobal(key) {
    return this.globalStorage.get(key);
  }
}

// Built-in Core Micro-Modules for JS Engine
class BattleModuleJS extends BaseArcanumModuleJS {
  constructor() {
    super({
      id: "battle",
      name: "Battle Core Module",
      version: "1.0.0",
      description: "Handles arena, turn calculations, and PvP combat."
    });
  }
}

class CardsModuleJS extends BaseArcanumModuleJS {
  constructor() {
    super({
      id: "cards",
      name: "Cards & Evolution Module",
      version: "1.0.0",
      description: "Manages cards collection, leveling, and transcending."
    });
  }
}

class InventoryModuleJS extends BaseArcanumModuleJS {
  constructor() {
    super({
      id: "inventory",
      name: "Inventory & Crafting Module",
      version: "1.0.0",
      description: "Manages item recipes, crafting forge, and loot chests."
    });
  }
}

class QuestModuleJS extends BaseArcanumModuleJS {
  constructor() {
    super({
      id: "quest",
      name: "Quest & Achievements Module",
      version: "1.0.0",
      description: "Tracks diary quests, achievements, and rewards."
    });
  }
}

class SaveSyncModuleJS extends BaseArcanumModuleJS {
  constructor() {
    super({
      id: "save_sync",
      name: "Save & Cross-Platform Sync Module",
      version: "1.0.0",
      description: "JSON state import/export and LocalStorage persistence."
    });
  }
}

class ArcanumEngineJS {
  constructor() {
    this.context = new EngineContextJS();
  }

  async boot(bootConfigUrl = "./boot.json") {
    console.log("⚡ Booting Arcanum Universal JS Engine Core...");
    
    // Default fallback boot config
    let config = {
      activeModules: ["battle", "cards", "inventory", "quest", "save_sync"]
    };

    try {
      const resp = await fetch(bootConfigUrl);
      if (resp.ok) {
        config = await resp.json();
      }
    } catch (e) {
      console.warn("Using default boot configuration:", e);
    }

    // Register active micro-modules
    if (config.activeModules.includes("battle")) this.context.registry.register(new BattleModuleJS(), this.context);
    if (config.activeModules.includes("cards")) this.context.registry.register(new CardsModuleJS(), this.context);
    if (config.activeModules.includes("inventory")) this.context.registry.register(new InventoryModuleJS(), this.context);
    if (config.activeModules.includes("quest")) this.context.registry.register(new QuestModuleJS(), this.context);
    if (config.activeModules.includes("save_sync")) this.context.registry.register(new SaveSyncModuleJS(), this.context);

    this.context.registry.initializeAll();
    this.context.eventBus.publish("engine_initialized", { version: config.version || "0.5.0" }, "core");
    console.log(`✅ Arcanum Engine JS active with ${this.context.registry.getAllModules().length} micro-modules!`);
  }
}

// Global Singleton Instance
window.ArcanumEngine = new ArcanumEngineJS();
