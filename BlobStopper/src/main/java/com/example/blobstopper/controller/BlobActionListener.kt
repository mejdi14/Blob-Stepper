package com.example.blobstopper.controller

abstract class BlobActionListener {
    open fun onStartListener(){
    }
    open fun onChangeListener(step: Int){

    }
    open fun onNextStepListener(){

    }
    open fun onFinishListener(){

    }

    open fun onClickListener(){

    }
}