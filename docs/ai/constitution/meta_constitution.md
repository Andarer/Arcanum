# Arcanum Meta Constitution v3.1 (Decadal Platform Guarantees)

## 1. Eternal Compatibility
Every API, package format (`.apkg`), and state schema must declare explicit versioning. Breaking changes to user worlds, cards, or assets are strictly prohibited without an automated migration transformer.

## 2. Zero Rewrite Principle
Existing functional code modules must never be rewritten from scratch when evolving the system. New features are introduced via decoupled events, contracts, or microkernel packages.

## 3. Explainability Engine Requirement
Every class, Web Component, package, or event must be able to self-explain its purpose, owner, dependencies, and impact if removed via `ArcanumPackageEngine.explainElement()`.

## 4. Digital Twin & Living Universe Map
The platform must maintain a real-time living visual map spanning Universe -> Platform -> Client -> Module -> Component -> Class -> Event -> Line of Docs.
