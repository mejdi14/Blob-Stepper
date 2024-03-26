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
import com.example.blob_stepper.component.BlobCircleComposable
import com.example.blob_stepper.component.ProgressCircleComposable
import com.example.blob_stepper.controller.BlobActionListener
import com.example.blob_stepper.controller.BlobProgressController
import com.example.blob_stepper.data.BlobCircle
import com.example.blob_stepper.data.BlobText
import com.example.blob_stepper.data.ProgressBorderCircle
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
        val controller = remember { BlobProgressController(steps = 8) }
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
                blobCircle = BlobCircle(blobText = BlobText(textStateValue = textValue)),
                blobActionListener = object : BlobActionListener() {
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
    controller: BlobProgressController,
    progressBorderCircle: ProgressBorderCircle = ProgressBorderCircle(),
    blobCircle: BlobCircle = BlobCircle(),
    blobActionListener: BlobActionListener
) {
    Box(
        modifier = modifier
            .height(300.dp)
            .fillMaxWidth(),
    ) {
        ProgressCircleComposable(
            progressBorderCircle = progressBorderCircle,
            controller = controller,
            blobActionListener = blobActionListener
        )
        BlobCircleComposable(
            blobCircle = blobCircle,
            controller = controller,
            blobActionListener = blobActionListener
        )
    }
}

