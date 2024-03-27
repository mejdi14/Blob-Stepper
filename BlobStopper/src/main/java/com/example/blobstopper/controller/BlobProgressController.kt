package com.example.blobstepper.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class BlobProgressController(
    initialProgress: Float = 0f,
    private val steps: Int = 4,
) : BlobProgressControllerInterface {
    private var _progress = mutableFloatStateOf(initialProgress)
    override val progress: State<Float> get() = _progress

    private var _currentStep = mutableIntStateOf(0)
    override val currentStep: State<Int> get() = _currentStep

    override val stepsCount: Int get() = steps

    private var _isExpanded = mutableStateOf(true)
    override val isExpanded: State<Boolean> get() = _isExpanded

    private var _isExploded = mutableStateOf(false)
    override val isExploded: State<Boolean> get() = _isExploded

    override val completionListener: ProgressCompletionListener = object :
        ProgressCompletionListener {
        override fun onProgressCompleted() {
            expand()
            _progress.floatValue = 0f
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
        _currentStep.intValue = step
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