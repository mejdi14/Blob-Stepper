package com.example.blobstepper.controller

import androidx.compose.runtime.State

interface BlobProgressControllerInterface {
    val progress: State<Float>

    val currentStep: State<Int>

    val isExpanded: State<Boolean>

    val completionListener: ProgressCompletionListener
    fun shrink()
    fun expand()
    fun next()
    fun back()
    fun goTo(step: Int)
    fun reset()
}