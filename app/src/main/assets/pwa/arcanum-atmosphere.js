/**
 * Arcanum Atmosphere & Particle Canvas Engine v2.3
 * Living background ambient renderer with floating particles, time-of-day gradients, fog, and touch/cursor reaction ripples.
 */

(function () {
  'use strict';

  class ArcanumAtmosphere {
    static canvas = null;
    static ctx = null;
    static particles = [];
    static ripples = [];
    static animFrameId = null;
    static particleCount = 45;

    static init() {
      this.canvas = document.createElement('canvas');
      this.canvas.id = 'arcanum-atmosphere-canvas';
      this.canvas.style.position = 'fixed';
      this.canvas.style.top = '0';
      this.canvas.style.left = '0';
      this.canvas.style.width = '100vw';
      this.canvas.style.height = '100vh';
      this.canvas.style.pointerEvents = 'none';
      this.canvas.style.zIndex = '0';
      this.canvas.style.opacity = '0.65';
      
      document.body.prepend(this.canvas);
      this.ctx = this.canvas.getContext('2d');

      this.resize();
      this.createParticles();
      this.bindEvents();
      this.animate();
    }

    static resize() {
      if (!this.canvas) return;
      this.canvas.width = window.innerWidth;
      this.canvas.height = window.innerHeight;
    }

    static createParticles() {
      this.particles = [];
      for (let i = 0; i < this.particleCount; i++) {
        this.particles.push({
          x: Math.random() * window.innerWidth,
          y: Math.random() * window.innerHeight,
          radius: Math.random() * 2.5 + 0.8,
          speedY: -(Math.random() * 0.4 + 0.1),
          speedX: (Math.random() - 0.5) * 0.3,
          alpha: Math.random() * 0.7 + 0.3,
          color: Math.random() > 0.5 ? '#c5a059' : '#9b51e0'
        });
      }
    }

    static bindEvents() {
      window.addEventListener('resize', () => this.resize());
      
      window.addEventListener('pointerdown', (e) => {
        this.addRipple(e.clientX, e.clientY);
      });
    }

    static addRipple(x, y) {
      this.ripples.push({
        x: x,
        y: y,
        radius: 5,
        maxRadius: 60,
        alpha: 0.8,
        color: '#f3e5ab'
      });
    }

    static animate() {
      if (!this.ctx) return;
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

      // Render Floating Embers/Particles
      this.particles.forEach((p) => {
        p.y += p.speedY;
        p.x += p.speedX;

        if (p.y < -10) p.y = window.innerHeight + 10;
        if (p.x < -10) p.x = window.innerWidth + 10;
        if (p.x > window.innerWidth + 10) p.x = -10;

        this.ctx.save();
        this.ctx.globalAlpha = p.alpha;
        this.ctx.fillStyle = p.color;
        this.ctx.beginPath();
        this.ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
        this.ctx.fill();
        this.ctx.restore();
      });

      // Render Touch/Cursor Reaction Ripples
      for (let i = this.ripples.length - 1; i >= 0; i--) {
        const r = this.ripples[i];
        r.radius += 2.5;
        r.alpha -= 0.025;

        if (r.alpha <= 0 || r.radius >= r.maxRadius) {
          this.ripples.splice(i, 1);
          continue;
        }

        this.ctx.save();
        this.ctx.globalAlpha = r.alpha;
        this.ctx.strokeStyle = r.color;
        this.ctx.lineWidth = 1.5;
        this.ctx.beginPath();
        this.ctx.arc(r.x, r.y, r.radius, 0, Math.PI * 2);
        this.ctx.stroke();
        this.ctx.restore();
      }

      this.animFrameId = requestAnimationFrame(() => this.animate());
    }
  }

  window.ArcanumAtmosphere = ArcanumAtmosphere;

  window.addEventListener('DOMContentLoaded', () => {
    ArcanumAtmosphere.init();
  });
})();
