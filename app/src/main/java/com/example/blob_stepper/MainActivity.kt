package com.example.blob_stepper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BlobScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val controller = remember { BlobProgressController(steps = 4) }
        val pagerState = rememberPagerState(pageCount = { controller.stepsCount })
        val textValue = remember {
            mutableStateOf("Next")
        }
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Blue)
            ) {
                HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) {
                    when (controller.currentStep.value) {
                        1 -> PagerImage(R.drawable.image1)
                        2 -> PagerImage(R.drawable.image2)
                        3 -> PagerImage(R.drawable.image3)
                        4 -> PagerImage(R.drawable.image4)
                    }

                }
            }
            BlobStepper(controller = controller,
                blobCircle = BlobCircle(
                    blobText = BlobText(
                        textStateValue = textValue
                    )
                ),
                blobActionListener = object : BlobActionListener() {
                    override fun onChangeListener(step: Int) {
                        Log.d("TAG", "onChangeListener: $step")
                    }

                    override fun onFinishListener() {
                        Log.d("TAG", "onFinishListener: ")
                        textValue.value = "Done"
                    }

                })
        }
    }
}

@Composable
private fun PagerImage(imageResources: Int) {
    Image(
        painter = painterResource(id = imageResources),
        contentDescription = "",
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentScale = ContentScale.FillBounds
    )
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

