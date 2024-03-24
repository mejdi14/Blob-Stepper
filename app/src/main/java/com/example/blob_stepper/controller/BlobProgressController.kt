package com.example.blob_stepper.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf

class BlobProgressController(
    initialProgress: Float = 0f,
    private val steps: Int = 4,
) : BlobProgressControllerInterface {
    private var _progress = mutableFloatStateOf(initialProgress)
    override val progress: State<Float> get() = _progress

    private var _isExpanded = mutableStateOf(true)
    override val isExpanded: State<Boolean> get() = _isExpanded

    override val completionListener: ProgressCompletionListener = object : ProgressCompletionListener{
        override fun onProgressCompleted() {
            expand()
        }
    }


    override fun next() {
        val targetStep = (progress.value * steps).toInt() + 1
        goTo(targetStep)
    }

    override fun back() {
        val targetStep = (progress.value * steps).toInt() - 1
        goTo(targetStep)
    }

    override fun goTo(step: Int) {
        _progress.floatValue = step.coerceIn(0, steps) / steps.toFloat()
    }

    override fun shrink() {
        reset()
        _isExpanded.value = false
    }

    override fun expand() {
        _isExpanded.value = true
    }

    override fun reset() {
        goTo(0)
    }
}