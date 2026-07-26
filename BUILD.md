# Arcanum Evolution Build System Specification v3.3 (OMEGA PIPELINE ERA)

## 🛠️ Overview & Architecture
The Arcanum Evolution build system is driven by **OMEGA PIPELINE (`.github/workflows/pipeline.yml`)**, orchestrating 17 independent, reproducible stages from self-validation and architectural graph checks to Android Gradle compilation and GitHub Pages deployment.

---

## 🚀 17 Stage Omega Pipeline Breakdown

| Stage ID | Stage Name | Description |
|---|---|---|
| `01_validate` | **Self Validation** | File existence, JSON manifests, and path integrity audit |
| `02_architecture` | **Architecture Audit** | Decoupled microkernel contracts and score verification |
| `03_dependencies` | **Dependencies Check** | Gradle dependency tree & Version Catalog resolution |
| `04_lint` | **Code Quality Lint** | Static code analysis and code style check |
| `05_tests` | **Unit Testing** | Core business logic and model unit tests |
| `06_build_core` | **Core Build** | Pure Kotlin and JavaScript mirror core build |
| `07_build_pwa` | **PWA Bundle Build** | Offline Service Worker v3.3 asset cache assembly |
| `08_build_android` | **Android APK Build** | Gradle `assembleDebug` compilation |
| `09_generate_docs` | **Docs Sync** | Markdown specification suite validation |
| `10_generate_assets` | **Asset Generation** | WebAudio synth & particle canvas assets check |
| `11_generate_icons` | **Icon Generation** | Custom adaptive icons & splash screen verification |
| `12_generate_cards` | **Cards Database** | 30 Elemental Cards RPG balance check |
| `13_generate_qr` | **ALP QR Protocol** | Cross-device ALP v2.0 payload generator |
| `14_deploy_pages` | **GitHub Pages Deploy** | Multi-channel web portal deployment (`stable`, `beta`, `nightly`, `experimental`) |
| `15_publish_release` | **Release Packaging** | Release APK, SHA256 checksums, and `build-info.json` |
| `16_generate_reports` | **Reports Telemetry** | Architecture Score (100/100) & Digital Twin update |
| `17_finalize` | **Finalize Status** | Pipeline execution summary and status reporting |

---

## 💻 Local Build Commands

### Android Native Container
```bash
gradle assembleDebug
```

### PWA Client Local Preview
Open `/app/src/main/assets/pwa/index.html` or serve via any static HTTP server.
