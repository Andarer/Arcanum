# Arcanum Meta AI Workflow & Execution Protocol v2.4

## Step 1: Context Analysis & Skill Verification
- Analyze current repository state, file trees, dependencies, and previous documentation (`PROJECT_STATE.md`, `TODO.md`, `CHANGELOG.md`).
- Perform Skill Check for relevant integrations (Room, Gemini, App Icon, Image Generation, etc.).

## Step 2: Evolutionary Plan & Micro-Task Breakdown
- Formulate a 3-step action plan focusing on high-value functional, visual, or architectural enhancements.
- Respect scope discipline: implement fully functional, production-ready code with no placeholder stubs or broken references.

## Step 3: Dual-Core Implementation (PWA Primary Core + Android Container)
- Implement web/PWA features inside `/app/src/main/assets/pwa/`.
- Ensure native bridge hooks (`ArcanumNativeBridge`) and Compose host screens (`PwaWebScreen.kt`) remain synchronized.

## Step 4: Verification & Compilation
- Execute `compile_applet` tool to ensure Android Gradle build and asset bundling complete cleanly.

## Step 5: Automatic Documentation & Prompt Sync
- Update all 18 standard documentation files (`AI_CONSTITUTION.md`, `AI_WORKFLOW.md`, `ARCHITECTURE.md`, `MODULES.md`, `CORE.md`, `PWA.md`, `ANDROID.md`, `COMPONENTS.md`, `NETWORK.md`, `ALP_PROTOCOL.md`, `DESIGN_SYSTEM.md`, `UX_GUIDE.md`, `UI_GUIDE.md`, `ROADMAP.md`, `PROJECT_STATE.md`, `CHANGELOG.md`, `DECISIONS.md`, `TODO.md`).
- Archive active prompts into `/docs/ai/prompts/`.
