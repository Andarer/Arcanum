# Arcanum Meta Evolution Workflow Specification

## Workflow Engine Protocol
1. **Repository Inspection**: Check `/PROJECT_STATE.md` and `/TODO.md`.
2. **Feature Implementation**: Implement features in PWA Primary Core (`arcanum-core.js`, `arcanum-ui.js`, `arcanum-atmosphere.js`, `arcanum-audio.js`, `app.js`).
3. **Android Shell Bridge**: Maintain native container (`PwaWebScreen.kt`, `ArcanumNativeBridge`).
4. **Verification**: Run `compile_applet`.
5. **Documentation Synchronization**: Auto-update standard documentation files.
