package com.example.blobstopper.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.blobstepper.data.BlobCircle

@Composable
 fun BlobContentComposable(
    contentVisibility: MutableState<Boolean>,
    blobCircle: BlobCircle,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = contentVisibility.value,
        modifier = modifier
    ) {
        Box {
            when (blobCircle.blobContent) {
                is BlobContent.TextContent -> {
                    Text(
                        text = blobCircle.blobContent.text.value,
                        style = blobCircle.blobContent.textStyle,
                        color = blobCircle.blobContent.color,
                        modifier = blobCircle.blobContent.modifier.align(Alignment.Center)
                    )
                }

                is BlobContent.ImageContent -> {
                    Image(
                        painter = blobCircle.blobContent.painter.value,
                        contentDescription = blobCircle.blobContent.contentDescription,
                        modifier = blobCircle.blobContent.modifier
                    )
                }
            }
        }
    }
}