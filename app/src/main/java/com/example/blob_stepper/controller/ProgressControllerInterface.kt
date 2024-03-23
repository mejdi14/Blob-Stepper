package com.example.blob_stepper.controller

import androidx.compose.runtime.State

interface ProgressControllerInterface {
    val progress: State<Float>

    val isExpanded: State<Boolean>
    fun shrink()
    fun expand()
    fun next()
    fun goTo(step: Int)
}