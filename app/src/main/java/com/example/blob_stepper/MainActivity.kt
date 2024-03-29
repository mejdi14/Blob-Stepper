package com.example.blob_stepper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
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
import com.example.blobstopper.controller.BlobActionListener
import com.example.blobstepper.controller.BlobProgressController
import com.example.blobstepper.data.BlobCircle
import com.example.blobstepper.data.BlobText
import com.example.blobstepper.data.ProgressBorderCircle
import com.example.blob_stepper.ui.theme.BlobStepperTheme
import com.example.blobstopper.component.BlobContent
import com.example.blobstopper.component.BlobStepper

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
    val showLastScreen = remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val controller = remember { BlobProgressController(steps = 3) }
        val pagerState = rememberPagerState(pageCount = { controller.stepsCount })
        val textValue = remember {
            mutableStateOf("Next")
        }
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) {
                        when (controller.currentStep.value) {
                            1 -> PagerImage(R.drawable.peep1)
                            2 -> PagerImage(R.drawable.peep2)
                            3 -> PagerImage(R.drawable.peep3)
                            else -> PagerImage(R.drawable.peep4)
                        }

                    }
                }
                BlobStepper(controller = controller,
                    blobCircle = BlobCircle(
                        blobContent = BlobContent.TextContent(text = textValue)
                    ),
                    blobActionListener = object : BlobActionListener() {
                        override fun onChangeListener(step: Int) {
                            if (controller.isFinished.value)
                                controller.explode()
                        }

                        override fun onFinishListener() {
                            textValue.value = "Done"
                        }

                        override fun onExplodeListener() {
                            Log.d("TAG", "onExplodeListener: ")

                            showLastScreen.value = true
                        }

                    })
            }
            AnimatedVisibility(visible = showLastScreen.value, modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                Text(text = "ghsoiufghoizehgiohrezioghiorez", color = Color.White)
            }
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
