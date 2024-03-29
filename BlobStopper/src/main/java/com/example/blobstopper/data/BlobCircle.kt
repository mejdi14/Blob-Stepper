package com.example.blobstepper.data

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.blobstopper.component.BlobContent

data class BlobCircle(
    override val radius: Float = 250f,
    val shrinkRadius: Float = 200f,
    val color: Color = Color.Black,
    val wavesMovementDurationMillis: Int = 1000,
    val sizeTransformationDurationMillis: Int = 500,
    val wavesCount: Int = 2,
    val blobContent: BlobContent = BlobContent.TextContent(mutableStateOf(""))
) : Circle()