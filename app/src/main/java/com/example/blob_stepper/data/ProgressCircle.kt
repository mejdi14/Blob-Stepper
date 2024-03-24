package com.example.blob_stepper.data

import androidx.compose.ui.graphics.Color

data class ProgressBorderCircle (
    override val radius : Float = 250f,
    val strokeWidth: Float = 8f,
    val strokeDefaultColor: Color = Color.LightGray,
    val strokeFilledColor: Color = Color.Black,
    val startAngle: ProgressStartAngle = ProgressStartAngle.Left,
    val progressAnimationDurationMillis: Int = 500,
): Circle() {
    val diameter: Float
        get() = radius * 2 - strokeWidth
}