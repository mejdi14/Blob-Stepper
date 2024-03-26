package com.example.blob_stepper.controller

abstract class BlobActionListener {
    open fun onStartListener(){
    }
    open fun onChangeListener(step: Int){

    }
    open fun onNextStepListener(){

    }
    open fun onFinishListener(){

    }
}