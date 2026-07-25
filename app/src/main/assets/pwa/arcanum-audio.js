/**
 * Arcanum Audio & Spatial WebAudio Sound Synthesizer Engine v2.3
 * Procedural web sound generator for magical UI clicks, card flips, combat strikes, crafting chimes, and ambient drones.
 */

(function () {
  'use strict';

  class ArcanumAudioEngine {
    static audioCtx = null;
    static soundEnabled = true;

    static init() {
      if (!this.audioCtx) {
        const AudioContextClass = window.AudioContext || window.webkitAudioContext;
        if (AudioContextClass) {
          this.audioCtx = new AudioContextClass();
        }
      }
      if (this.audioCtx && this.audioCtx.state === 'suspended') {
        this.audioCtx.resume();
      }
    }

    static playTone(freq, type = 'sine', duration = 0.15, gainVal = 0.1) {
      if (!this.soundEnabled) return;
      this.init();
      if (!this.audioCtx) return;

      runCatching(() => {
        const osc = this.audioCtx.createOscillator();
        const gain = this.audioCtx.createGain();

        osc.type = type;
        osc.frequency.setValueAtTime(freq, this.audioCtx.currentTime);

        gain.gain.setValueAtTime(gainVal, this.audioCtx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.0001, this.audioCtx.currentTime + duration);

        osc.connect(gain);
        gain.connect(this.audioCtx.destination);

        osc.start();
        osc.stop(this.audioCtx.currentTime + duration);
      });
    }

    static playUiClick() {
      this.playTone(440, 'triangle', 0.08, 0.08);
    }

    static playCardFlip() {
      this.playTone(280, 'sine', 0.12, 0.1);
    }

    static playMagicChime() {
      this.playTone(523.25, 'sine', 0.2, 0.12);
      setTimeout(() => this.playTone(659.25, 'sine', 0.25, 0.1), 80);
      setTimeout(() => this.playTone(783.99, 'sine', 0.3, 0.08), 160);
    }

    static playCombatHit() {
      this.playTone(110, 'sawtooth', 0.25, 0.2);
    }

    static playSuccess() {
      this.playTone(440, 'sine', 0.15, 0.1);
      setTimeout(() => this.playTone(880, 'sine', 0.25, 0.12), 100);
    }
  }

  function runCatching(fn) {
    try { fn(); } catch (e) { console.warn('Audio synthesis note skipped:', e); }
  }

  window.ArcanumAudioEngine = ArcanumAudioEngine;

  // Bind universal interaction audio hooks
  window.addEventListener('click', (e) => {
    if (e.target.closest('button, a, select, [role="button"], ar-button, .pwa-nav-btn')) {
      ArcanumAudioEngine.playUiClick();
    }
  });
})();
