package com.example.blob_stepper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blobstepper.component.BlobCircleComposable
import com.example.blobstepper.component.ProgressCircleComposable
import com.example.blobstepper.controller.BlobActionListener
import com.example.blobstepper.controller.BlobProgressController
import com.example.blobstepper.data.BlobCircle
import com.example.blobstepper.data.BlobText
import com.example.blobstepper.data.ProgressBorderCircle
import com.example.blob_stepper.ui.theme.BlobStepperTheme

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
fun BlobScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val controller = remember { com.example.blobstepper.controller.BlobProgressController(steps = 8) }
        val textValue = remember {
            mutableStateOf("Next")
        }
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Blue)
            ) {
            }
            BlobStepper(controller = controller,
                blobCircle = com.example.blobstepper.data.BlobCircle(
                    blobText = com.example.blobstepper.data.BlobText(
                        textStateValue = textValue
                    )
                ),
                blobActionListener = object : com.example.blobstepper.controller.BlobActionListener() {
                    override fun onChangeListener(step: Int) {
                        Log.d("TAG", "onChangeListener: $step")
                    }

                    override fun onFinishListener() {
                        Log.d("TAG", "onFinishListener: ")
                        textValue.value = "Done"
                    }


                    override fun onStartListener() {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}

@Composable
private fun BlobStepper(
    modifier: Modifier = Modifier,
    controller: com.example.blobstepper.controller.BlobProgressController,
    progressBorderCircle: com.example.blobstepper.data.ProgressBorderCircle = com.example.blobstepper.data.ProgressBorderCircle(),
    blobCircle: com.example.blobstepper.data.BlobCircle = com.example.blobstepper.data.BlobCircle(),
    blobActionListener: com.example.blobstepper.controller.BlobActionListener
) {
    Box(
        modifier = modifier
            .height(300.dp)
            .fillMaxWidth(),
    ) {
        com.example.blobstepper.component.ProgressCircleComposable(
            progressBorderCircle = progressBorderCircle,
            controller = controller,
            blobActionListener = blobActionListener
        )
        com.example.blobstepper.component.BlobCircleComposable(
            blobCircle = blobCircle,
            controller = controller,
            blobActionListener = blobActionListener
        )
    }
}

