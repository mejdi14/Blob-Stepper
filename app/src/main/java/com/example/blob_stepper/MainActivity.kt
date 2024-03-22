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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

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
fun AnimatedSmoothAlternatingWavesBlob(waveCount: Int = 5) {
    var isExpanded by remember { mutableStateOf(true) }
    val infiniteTransition = rememberInfiniteTransition()
    val animatedWave = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val targetRadius = if (isExpanded) 250f else 200f
    val animatedRadius by animateFloatAsState(
        targetValue = targetRadius,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { isExpanded = !isExpanded }
                )
            }
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val path = Path()

        if (isExpanded) {
            path.addOval(androidx.compose.ui.geometry.Rect(centerX - animatedRadius, centerY - animatedRadius, centerX + animatedRadius, centerY + animatedRadius))
        } else {
            for (angle in 0 until 360 step 5) {
                val radian = Math.toRadians(angle.toDouble()).toFloat()
                val phaseShift = Math.PI.toFloat() * (angle / (360f / waveCount))
                val waveOffset = if (!isExpanded) 10 * sin(waveCount * radian + animatedWave.value + phaseShift) else 0f // Remove wave effect when expanded
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
        AnimatedSmoothAlternatingWavesBlob(2)
        CanvasWithBorderCircle()
    }
}


@Composable
fun CanvasWithBorderCircle() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = 250f

        drawCircle(
            color = Color(color = 0xFFF2F2F2),
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 8f)
        )
    }
}