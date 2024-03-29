package com.example.blobstopper.component

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.blobstopper.R

sealed class BlobContent {
    data class TextContent(
        val text: State<String> = mutableStateOf(""),
        val textStyle: TextStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.open_sans_regular)),
            fontSize = 18.sp
        ),
        val color: Color = Color.White,
        val modifier: Modifier = Modifier
    ) : BlobContent()

    data class ImageContent(
        val painter: State<Painter>,
        val contentDescription: String?,
        val modifier: Modifier
    ) : BlobContent()
}