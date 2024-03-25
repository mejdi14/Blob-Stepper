package com.example.blob_stepper.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier

data class BlobText(
    val textStateValue: State<String> = mutableStateOf(""),
    val modifier: Modifier,
)