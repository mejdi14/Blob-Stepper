package com.example.blob_stepper.controller

import androidx.compose.runtime.State

interface ProgressController {
    val progress: State<Float>
    fun next()
    fun goTo(step: Int)
}