<h1 align="center">Welcome to Card Switcher Library 👋</h1>

<p align="center">
  <a href="https://github.com/frinyvonnick/gitmoji-changelog">
    <img src="https://img.shields.io/badge/API-15%2B-blue.svg?style=flat" alt="gitmoji-changelog">
  </a>  <a href="https://github.com/frinyvonnick/gitmoji-changelog">
    <img src="https://jitpack.io/v/mejdi14/AndroidColorPicker.svg" alt="gitmoji-changelog">
  </a>
  </a>
	<a href="https://github.com/kefranabg/readme-md-generator/blob/master/LICENSE">
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-yellow.svg" target="_blank" />
  </a>
  <a href="https://codecov.io/gh/kefranabg/readme-md-generator">
    <img src="https://codecov.io/gh/kefranabg/readme-md-generator/branch/master/graph/badge.svg" />
  </a>
</p>

## ✨ Demo
<p align="center">
<img src="https://github.com/mejdi14/Card-Switcher/blob/main/app/images/demo.gif" height="400" width="550" >
	</p>
	
	
## :art:Design inspiration
many thanks goes to [Kim Baschet](https://twitter.com/Kim_____B) for the beautiful design and animation




## Installation

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
``` 
## :hammer:Dependency

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge above):

```gradle
dependencies {
	...
	implementation 'com.github.mejdi14:blob-stepper:0.0.1'
}
```


## :fire:How to use

``` java
val controller = remember { BlobProgressController(steps = 3) }
     BlobStepper(controller = controller,
                    blobCircle = BlobCircle(
                        blobContent = BlobContent.TextContent(text = textValue)
                    ),
                    blobActionListener = object : BlobActionListener() {
                        override fun onChangeListener(step: Int) {
                            
                        }

                        override fun onFinishListener() {
                            
                        }

                        override fun onExplodeListener() {
                            
                        }
                    })
```

Blob Cicrle Options
-----

``` java
     radius: Initial circle radius
     shrinkRadius: Circle radius after transformation
     color: Color = Cicle color
     wavesHeight: Blob waves height
     wavesMovementDurationMillis: Blob waves animation duration
     sizeTransformationDurationMillis: Circle state change duration
     wavesCount: Number of waves
     blobContent: Blob content (Text or Image)
```
Progress Circle Options
-----

``` java
     radius : Initial progress circle radius
     strokeWidth: Border stroke width
     strokeDefaultColor: Stroke background color
     strokeFilledColor: Stroke progress color
     startAngle: At which angle the progress animation starts
     progressAnimationDurationMillis: Progress animation duration
```

Controller Functions
-----

``` java
     progress: State<Float> : Progress percent
     currentStep: State<Int> : Current Step
     isExpanded: State<Boolean> : Cicle is on initial size
     isExploded: State<Boolean> : Circle covers all the screen
     isFinished: State<Boolean> : Progress circle reached final step
     stepsCount: Int : All steps count
     completionListener: ProgressCompletionListener : Progress is over
     shrink() : Transform the circle to the smaller size and start blob waves animation
     expand() : Go back to the initial circle size and cancel animation
     explode() : Cover all screen 
     next() : Go to the next step progress
     back() : Go back to the previous step
     goTo(step: Int) : Go to a specific step
     reset() : Rest the progress counter
```

Actions Listener
-----

``` java
     onStartListener() : Animation started (circle shrink and blob waves are moving)

     onChangeListener(step: Int) : Triggered when there is a change in the circle state

     onNextStepListener() : Triggered when we move to a new step

     onFinishListener() : We reached the final step

     onClickListener() : On Circle click listener

     onExplodeListener() : Circle is exploded and covered all the screen

```

Blob Content
-----
``` java
 BlobContent.TextContent(text = textValue)

 BlobContent.ImageContent(painter = painterValue)
```
insde the Blob Circle you can put a  Text Composable or an Image Composable,
both accept State value so that you can change the content value while you navigate from a step to another.



## 🤝 Contributing

Contributions, issues and feature requests are welcome.<br />
Feel free to check [issues page] if you want to contribute.<br />


## Author

👤 **Mejdi Hafiane**

- profile: [@MejdiHafiane](https://twitter.com/mejdi141)

## Show your support

Please ⭐️ this repository if this project helped you!


## 📝 License

Copyright © 2019 [Mejdi Hafiane](https://github.com/mejdi14).<br />
This project is [MIT](https://github.com/mejdi14/readme-md-generator/blob/master/LICENSE) licensed.
