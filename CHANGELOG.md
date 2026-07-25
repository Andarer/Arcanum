# Arcanum Platform Changelog

## [3.2.0] - 2026-07-25 (GITHUB EVOLUTION PLATFORM ERA)
### Added
- **Multi-Channel PWA Version Manager & Switcher (`arcanum-git.js`)**: Real-time channel switcher (`Stable`, `Beta`, `Nightly`, `Experimental`) with branch & build metadata and instant channel switching simulation.
- **Interactive GitHub Digital Factory Dashboard**: Displays GitHub Actions pipeline statuses (`Self-Validation`, `Multi-Channel Deploy`, `GitHub Pages`, `Build APK`), Release APK downloads, SHA256 checksums, and Build Info.
- **Modular GitHub Actions Workflows (`.github/workflows/`)**:
  - `arcanum-self-validation.yml`: Pre-deployment structure, PWA manifest, service worker, and Android build verification.
  - `arcanum-multi-channel-deploy.yml`: Multi-channel build & GitHub Pages publishing with `build-info.json` & SHA256 generation.
- **Service Worker v3.2 (`sw.js`)**: Added `./arcanum-git.js` to offline asset cache.
- **GitHub Evolution Platform Specifications**: Saved `/docs/ai/constitution/github_evolution_constitution.md` and `/docs/ai/prompts/github_evolution_v3.2.md`.
- **Synchronized Specifications**: Updated `ARCHITECTURE.md`, `ROADMAP.md`, `CHANGELOG.md`, `PROJECT_STATE.md`, `TODO.md`.

## [3.1.0] - 2026-07-25 (META CONSTITUTION ERA)
### Added
- **Arcanum Package Format (.apkg v3.1)**: Package manager engine (`arcanum-package.js`) for installing and isolating modules, themes, worlds, audio, and AI configs.
- **Interactive Living Universe Map**: Hierarchical visual explorer spanning Universe -> Platform -> Client -> Module -> Component -> Class -> Event -> Line of Docs.
- **Explainability Engine (`ArExplainable`)**: Self-inspection engine detailing element purpose, owner, dependencies, and impact if removed.
- **Arcanum Academy & Laboratory**: Embedded academy with interactive tutorials and beta feature experiment toggles.

## [3.0.0] - 2026-07-25 (SINGULARITY ERA)
### Added
- **Arcanum Microkernel & Maturity Engine (`arcanum-kernel.js`)**: Module lifecycle manager tracking evolution levels from L0 (Idea) to L10 (Platform Benchmark).
- **Arcanum Digital Twin Dashboard**: Real-time telemetry displaying Architecture Score (99/100), AI Council Advisory Board metrics, Project Memory Ledger, and Version Time Machine matrix.
