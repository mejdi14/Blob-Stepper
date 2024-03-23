package com.example.blob_stepper

import android.os.Bundle
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
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.blob_stepper.controller.ProgressController
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlobStepperTheme {
                // A surface container using the 'background' color from the theme
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
fun AnimatedSmoothAlternatingWavesBlob(waveCount: Int = 5, controller: ProgressController) {
    val infiniteTransition = rememberInfiniteTransition()
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
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { if(controller.isExpanded.value) controller.shrink() else controller.expand()}
                )
            }
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val path = Path()

        if (controller.isExpanded.value) {
            path.addOval(androidx.compose.ui.geometry.Rect(centerX - animatedRadius, centerY - animatedRadius, centerX + animatedRadius, centerY + animatedRadius))
        } else {
            for (angle in 0 until 360 step 5) {
                val radian = Math.toRadians(angle.toDouble()).toFloat()
                val phaseShift = Math.PI.toFloat() * (angle / (360f / waveCount))
                val waveOffset = if (!controller.isExpanded.value) 10 * sin(waveCount * radian + animatedWave.value + phaseShift) else 0f // Remove wave effect when expanded
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
        val controller = remember { ProgressController() }
        AnimatedSmoothAlternatingWavesBlob(2, controller)
        AnimatedCircularBorderProgress(controller)
    }
}

@Composable
fun AnimatedCircularBorderProgress(controller: ProgressController) {
    var targetProgress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = controller.progress.value,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    val onClick = {
        targetProgress += 0.25f
        if (targetProgress > 1f) targetProgress = 0f // Reset after full circle
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val strokeWidth = 20f
        val diameter = size.minDimension / 2 - strokeWidth
        val topLeft = Offset(
            (size.width - diameter) / 2,
            (size.height - diameter) / 2
        )
        val size = Size(diameter, diameter)
        val startAngle = 90f
        val sweepAngle = 360 * animatedProgress

        drawArc(
            color = Color.LightGray,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = Color.Black,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}