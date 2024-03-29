package com.example.blobstepper.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.blobstopper.component.BlobContent

data class BlobCircle(
    override val radius: Float = 250f,
    val shrinkRadius: Float = 170f,
    val color: Color = Color.Black,
    val wavesHeight: Int = 2,
    val wavesMovementDurationMillis: Int = 800,
    val sizeTransformationDurationMillis: Int = 500,
    val wavesCount: Int = 2,
    val blobContent: BlobContent = BlobContent.TextContent(mutableStateOf(""))
) : Circle()