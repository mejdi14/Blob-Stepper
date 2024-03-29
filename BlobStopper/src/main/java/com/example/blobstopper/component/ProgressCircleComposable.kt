package com.example.blobstepper.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.blobstopper.controller.BlobActionListener
import com.example.blobstepper.controller.BlobProgressController
import com.example.blobstepper.data.ProgressBorderCircle

@Composable
fun ProgressCircleComposable(
    progressBorderCircle: ProgressBorderCircle = ProgressBorderCircle(),
    controller: BlobProgressController,
    blobActionListener: BlobActionListener
) {
    val animatedProgress by animateFloatAsState(
        targetValue = controller.progress.value,
        animationSpec = tween(durationMillis = progressBorderCircle.progressAnimationDurationMillis),
        label = ""
    )

    LaunchedEffect(animatedProgress) {
        if (animatedProgress == controller.progress.value) {
            if (controller.progress.value == 1f) {
                controller.completionListener.onProgressCompleted()
                blobActionListener.onFinishListener()
            }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val diameter = progressBorderCircle.diameter
        val topLeft = Offset(
            (size.width - diameter) / 2,
            (size.height - diameter) / 2
        )
        val size = Size(diameter, diameter)
        val startAngle = progressBorderCircle.startAngle.value
        val sweepAngle = 360 * animatedProgress

        drawArc(
            color = progressBorderCircle.strokeDefaultColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = progressBorderCircle.strokeWidth)
        )

        drawArc(
            color = progressBorderCircle.strokeFilledColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = progressBorderCircle.strokeWidth, cap = StrokeCap.Round)
        )
    }
}