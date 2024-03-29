package com.example.blobstopper.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blobstepper.component.BlobCircleComposable
import com.example.blobstepper.component.ProgressCircleComposable
import com.example.blobstepper.controller.BlobProgressController
import com.example.blobstepper.data.BlobCircle
import com.example.blobstepper.data.ProgressBorderCircle
import com.example.blobstopper.controller.BlobActionListener

@Composable
fun BlobStepper(
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