package com.example.guitareffectapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.synervoz.switchboard.sdk.SwitchboardSDK
import com.synervoz.switchboard.ui.UIGenerator
import com.synervoz.switchboardsuperpowered.SuperpoweredExtension

class MainActivity : AppCompatActivity() {

    lateinit var engine: GuitarEffectEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkMicrophonePermission()) {
            initAudioEngine()
        }
    }

    fun checkMicrophonePermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, ask the user.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initAudioEngine()
                }
            }
        }
    }

    private fun initAudioEngine() {
        SwitchboardSDK.initialize(this, "Your client ID", "Your client secret")
        SuperpoweredExtension.initialize("ExampleLicenseKey-WillExpire-OnNextUpdate")
        engine = GuitarEffectEngine(this)
        engine.start()
        setContentView(UIGenerator.generateViews(this, engine.audioGraph).getView())
    }

    override fun onDestroy() {
        super.onDestroy()
        engine.stop()
        engine.close()
    }
}