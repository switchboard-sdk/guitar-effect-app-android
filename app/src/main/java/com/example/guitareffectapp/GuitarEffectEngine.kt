package com.example.guitareffectapp

import android.content.Context
import com.synervoz.switchboard.sdk.audioengine.AudioEngine
import com.synervoz.switchboard.sdk.audiograph.AudioGraph
import com.synervoz.switchboard.sdk.audiographnodes.GainNode
import com.synervoz.switchboardsuperpowered.audiographnodes.CompressorNode
import com.synervoz.switchboardsuperpowered.audiographnodes.FlangerNode
import com.synervoz.switchboardsuperpowered.audiographnodes.GuitarDistortionNode
import com.synervoz.switchboardsuperpowered.audiographnodes.ReverbNode

class GuitarEffectEngine(context: Context) {
    val audioEngine = AudioEngine(context = context, microphoneEnabled = true)
    val audioGraph = AudioGraph()
    val gainNode = GainNode()

    val compressorNode = CompressorNode()
    val flangerNode = FlangerNode()
    val guitarDistortionNode = GuitarDistortionNode()
    val reverbNode = ReverbNode()

    init {
        audioGraph.addNode(gainNode)

        audioGraph.addNode(compressorNode)
        audioGraph.addNode(flangerNode)
        audioGraph.addNode(guitarDistortionNode)
        audioGraph.addNode(reverbNode)

        audioGraph.connect(audioGraph.inputNode, compressorNode)
        audioGraph.connect(compressorNode, flangerNode)
        audioGraph.connect(flangerNode, guitarDistortionNode)
        audioGraph.connect(guitarDistortionNode, reverbNode)
        audioGraph.connect(reverbNode, gainNode)
        audioGraph.connect(gainNode, audioGraph.outputNode)
    }

    fun start() {
        audioEngine.start(audioGraph)
    }

    fun stop() {
        audioEngine.stop()
    }

    fun close() {
        audioEngine.close()
        audioGraph.close()
        gainNode.close()
    }
}