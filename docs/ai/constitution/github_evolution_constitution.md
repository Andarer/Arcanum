# Arcanum GitHub Evolution Platform Constitution v3.2

## 1. Living Repository & Digital Factory
GitHub is not merely a source control repository; it is an active architectural organ of Arcanum Evolution. Every push or pull request triggers an automated full-cycle verification loop across Code, Specs, PWA, Android, and GitHub Pages.

## 2. Multi-Channel Environment Strategy
The platform maintains isolated publishing channels:
- **Stable**: Verified benchmark platform builds
- **Beta**: Public testing for new modules and Render Profiles
- **Nightly**: Daily automated laboratory builds
- **Experimental**: Research and WebLLM / WebRTC prototypes

Users can switch between published channels dynamically via the PWA Version Switcher (`arcanum-git.js`) without reinstalling.

## 3. GitHub Actions Workflows
CI/CD workflows must remain modular:
- `arcanum-self-validation.yml`: Pre-deployment architecture audit and linting
- `arcanum-multi-channel-deploy.yml`: Multi-channel build & GitHub Pages publishing
- `deploy-pwa-gh-pages.yml`: Automated PWA deployment
- `build-release-apk.yml`: Release APK packaging with SHA256 checksums and build info JSON
