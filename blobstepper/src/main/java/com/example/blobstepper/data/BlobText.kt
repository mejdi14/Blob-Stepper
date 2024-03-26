package com.example.blobstepper.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.blob_stepper.R

data class BlobText(
    val textStateValue: State<String> = mutableStateOf(""),
    val modifier: Modifier = Modifier,
    val color: Color = Color.White,
    val textStyle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.open_sans_regular)),
        fontSize = 18.sp
    )
)