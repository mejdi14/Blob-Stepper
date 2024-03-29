package com.example.blobstepper.controller

import androidx.compose.runtime.State

interface BlobProgressControllerInterface {
    val progress: State<Float>

    val currentStep: State<Int>

    val isExpanded: State<Boolean>

    val isExploded: State<Boolean>

    val isFinished: State<Boolean>

    val stepsCount: Int

    val completionListener: ProgressCompletionListener
    fun shrink()
    fun expand()

    fun explode()
    fun next()
    fun back()
    fun goTo(step: Int)
    fun reset()
}