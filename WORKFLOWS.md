# Arcanum GitHub Actions Workflows Guide v3.3

## Overview
Arcanum Evolution utilizes a modular set of GitHub Actions workflows, led by the master orchestrator `pipeline.yml`.

## Active Workflow Definitions

### 1. `pipeline.yml` (OMEGA PIPELINE v3.3)
- **File**: `.github/workflows/pipeline.yml`
- **Role**: Master Orchestrator Pipeline running 17 stages (`01_validate` through `17_finalize`).
- **Triggers**: Push to `main`, `master`, `beta`, `nightly`, `feature/*`, PRs, or `workflow_dispatch`.

### 2. `arcanum-self-validation.yml`
- **File**: `.github/workflows/arcanum-self-validation.yml`
- **Role**: Quick pre-deployment architecture audit and linting.
- **Triggers**: Push and PR.

### 3. `arcanum-multi-channel-deploy.yml`
- **File**: `.github/workflows/arcanum-multi-channel-deploy.yml`
- **Role**: Multi-channel build & GitHub Pages publishing (`stable`, `beta`, `nightly`, `experimental`).
- **Triggers**: Push to target channels or manual dispatch.

### 4. `deploy-pwa-gh-pages.yml`
- **File**: `.github/workflows/deploy-pwa-gh-pages.yml`
- **Role**: Standalone PWA GitHub Pages deployment.

### 5. `build-release-apk.yml`
- **File**: `.github/workflows/build-release-apk.yml`
- **Role**: Release APK packaging, SHA256 checksum generation, and release tag publishing.
