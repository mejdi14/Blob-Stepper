package com.example.blob_stepper.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import com.example.blob_stepper.controller.BlobActionListener
import com.example.blob_stepper.controller.BlobProgressController
import com.example.blob_stepper.data.BlobCircle
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BlobCircleComposable(
    blobCircle: BlobCircle = BlobCircle(),
    controller: BlobProgressController,
    blobActionListener: BlobActionListener
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedWave = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val targetRadius = if (controller.isExpanded.value) 250f else 200f
    val animatedRadius by animateFloatAsState(
        targetValue = targetRadius,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing), label = ""
    )

    Box {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (controller.isExpanded.value) {
                                blobActionListener.onStartListener()
                                controller.shrink()
                            } else {
                                blobActionListener.onNextStepListener()
                                controller.next()
                            }
                            blobActionListener.onChangeListener(controller.currentStep.value)
                        }
                    )
                }
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val path = Path()

            if (controller.isExpanded.value) {
                path.addOval(
                    Rect(
                        centerX - animatedRadius,
                        centerY - animatedRadius,
                        centerX + animatedRadius,
                        centerY + animatedRadius
                    )
                )
            } else {
                for (angle in 0 until 360 step 5) {
                    val radian = Math.toRadians(angle.toDouble()).toFloat()
                    val phaseShift = Math.PI.toFloat() * (angle / (360f / blobCircle.wavesCount))
                    val waveOffset =
                        if (!controller.isExpanded.value) 10 * sin(blobCircle.wavesCount * radian + animatedWave.value + phaseShift) else 0f
                    val currentRadius = animatedRadius + waveOffset
                    val x = centerX + currentRadius * cos(radian)
                    val y = centerY + currentRadius * sin(radian)

                    if (angle == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }
                path.close()
            }
            drawPath(path, Color.Black)
        }
        Text(
            text = blobCircle.blobText.textStateValue.value,
            style = blobCircle.blobText.textStyle,
            color = blobCircle.blobText.color,
            modifier = blobCircle.blobText.modifier.align(Alignment.Center)
        )
    }
}