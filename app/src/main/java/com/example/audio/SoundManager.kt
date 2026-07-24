package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log

class SoundManager(private val context: Context) {
    var isSoundEnabled: Boolean = true

    private var toneGenerator: ToneGenerator? = null

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
        } catch (e: Exception) {
            Log.e("SoundManager", "Error initializing ToneGenerator", e)
        }
    }

    fun playClick() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 50)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playAttack() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 80)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playCraft() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 180)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playHit() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_NACK, 120)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playCrit() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_DTMF_D, 150)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playHeal() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_DTMF_A, 120)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playMagic() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_DTMF_B, 150)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playVictory() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_DTMF_C, 250)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playDefeat() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_PROMPT, 300)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playLevelUp() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_SUP_RINGTONE, 200)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playBuy() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_DTMF_S, 80)
        } catch (e: Exception) { /* ignore */ }
    }

    fun playChest() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 200)
        } catch (e: Exception) { /* ignore */ }
    }

    fun release() {
        toneGenerator?.release()
        toneGenerator = null
    }
}
