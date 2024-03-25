package com.example.blob_stepper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.blob_stepper.ui.theme.BlobStepperTheme
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.blob_stepper.controller.BlobActionListener
import com.example.blob_stepper.controller.BlobProgressController
import com.example.blob_stepper.data.BlobCircle
import com.example.blob_stepper.data.ProgressBorderCircle
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlobStepperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BlobScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlobStepperTheme {
        Greeting("Android")
    }
}


@Composable
fun AnimatedSmoothAlternatingWavesBlob(
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

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {

                        if (controller.isExpanded.value) controller.shrink() else controller.next()
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
                androidx.compose.ui.geometry.Rect(
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
}


@Composable
fun BlobScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val controller = remember { BlobProgressController(steps = 8) }
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Blue)
            ) {
            }
            BlobStepper(controller = controller, blobActionListener = object : BlobActionListener {
                override fun onChangeListener(step: Int) {
                    Log.d("TAG", "onChangeListener: $step")
                }

                override fun onFinishListener() {
                    Log.d("TAG", "onFinishListener: ")
                }

            })
        }
    }
}

@Composable
private fun BlobStepper(
    modifier: Modifier = Modifier
        .height(300.dp)
        .fillMaxWidth(),
    controller: BlobProgressController,
    progressBorderCircle: ProgressBorderCircle = ProgressBorderCircle(),
    blobCircle: BlobCircle = BlobCircle(),
    blobActionListener: BlobActionListener
) {
    Box(
        modifier = modifier
    ) {
        AnimatedCircularBorderProgress(
            progressBorderCircle = progressBorderCircle,
            controller = controller,
            blobActionListener = blobActionListener
        )
        AnimatedSmoothAlternatingWavesBlob(
            blobCircle = blobCircle,
            controller = controller,
            blobActionListener = blobActionListener
        )
    }
}

@Composable
fun AnimatedCircularBorderProgress(
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