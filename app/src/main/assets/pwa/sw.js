const CACHE_NAME = 'arcanum-pwa-v4.4';
const ASSETS_TO_CACHE = [
  './',
  './index.html',
  './styles.css',
  './arcanum-core.js',
  './arcanum-atmosphere.js',
  './arcanum-audio.js',
  './arcanum-ui.js',
  './arcanum-frontend.js',
  './arcanum-visual.js',
  './arcanum-meta.js',
  './arcanum-docs.js',
  './arcanum-kernel.js',
  './arcanum-package.js',
  './arcanum-git.js',
  './arcanum-game-engine.js',
  './arcanum-ai.js',
  './arcanum-telemetry.js',
  './arcanum-ide.js',
  './arcanum-network.js',
  './app.js',
  './manifest.json'
];

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      return cache.addAll(ASSETS_TO_CACHE);
    })
  );
  self.skipWaiting();
});

self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((cache) => {
          if (cache !== CACHE_NAME) {
            return caches.delete(cache);
          }
        })
      );
    })
  );
  self.clients.claim();
});

self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      return response || fetch(event.request);
    })
  );
});
