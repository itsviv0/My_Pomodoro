# MY Pomodoro Timer App

This is a Pomodoro Timer application built using Kotlin. The app helps users manage their time using the Pomodoro Technique, which involves working for a set period and then taking a short break.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)

## Features

- Start, pause, and reset the timer
- Customizable work and break intervals
- Notification alerts for work and break periods
- Simple and intuitive user interface
- Notification tone after completion

## Installation

1. Clone the repository:
    ```sh
    https://github.com/your-user-name/My_Pomodoro.git
    ```
2. Open the project in Android Studio.

3. Build the project and run it on an emulator or a physical device.

## Usage

1. Open the app.
2. Set your desired work and break intervals.
3. Press the start button to begin the timer.
4. The app will notify you when it's time to take a break or start working again.

## Project Structure

```
app/
  src/
    androidTest/java/com/example/mypomodoro
        ExampleInstrumentedTest.kt
    test/java/com/example/mypomodoro
        ExampleUnitTest.kt
    main/
      java/
        com/
          example/
            mypomodoro/
                AddTimerActivity.kt
                MainActivity.kt
                PomoTimer.kt
                TimerAdapter.kt
                TimerDatabaseHelper.kt
                TimerService.kt
      res/
        drawable/
        mipmap-anydpi-v26/
        mipmap-hdpi/
        mipmap-mdpi/
        mipmap-xhdpi/
        mipmap-xxhdpi/
        mipmap-xxxhdpi/
        raw/
        values-night/
        values/
        xml/
        layout/
          activity_main.xml
        values/
          strings.xml
          styles.xml
        AndroidManifest.xml
        ic_launcher-playstore.png
  build.gradle.kts
  AndroidManifest.xml
gradle/
  wrapper/
    gradle-wrapper.properties
gradle.properties
gradlew
gradlew.bat
settings.gradle
```

## Technologies Used

- Kotlin
- Android SDK
- Android Studio
