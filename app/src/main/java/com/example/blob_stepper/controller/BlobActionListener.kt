package com.example.blob_stepper.controller

interface BlobActionListener {
    fun onChangeListener(step: Int)
    fun onFinishListener()
}