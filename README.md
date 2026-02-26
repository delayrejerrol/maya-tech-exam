# Send Money App

Simple Android app built with Jetpack Compose, MVVM, Clean Architecture, Hilt, Retrofit, and OkHttp.

## Features

- Login and logout flow
- Home screen with wallet balance and show/hide toggle
- Send money screen with numeric amount validation
- Submit result via bottom sheet (success/error)
- Transactions screen with history list
- API integration using `https://jsonplaceholder.typicode.com/`
  - `POST /posts` for creating a transaction
  - `GET /posts` for loading transaction history

## Tech Stack

- Kotlin
- Android Gradle Plugin 9+
- Jetpack Compose + Navigation Compose
- MVVM + Clean Architecture
- Dagger Hilt (Compose-compatible)
- Retrofit + OkHttp
- JUnit + Coroutine test + Compose UI test

## Project Structure

- `domain/` - models, repository contracts, use cases
- `data/` - repository implementations, remote API DTO/service
- `presentation/` - UI state + ViewModel
- `ui/` - Compose screens and navigation
- `di/` - Hilt modules

## Prerequisites

- Android Studio (latest stable recommended)
- JDK 17 (or JDK required by your Android Studio/AGP setup)
- Android SDK installed via Android Studio
- Emulator or physical device for instrumented/UI tests

## Demo Credentials

- Username: `demo`
- Password: `password123`

## How to Run the App

### Using Android Studio

1. Open Android Studio.
2. Select **Open** and choose this project folder.
3. Let Gradle sync finish.
4. Run the `app` configuration on an emulator/device.

### Using Terminal

From project root:

```bash
./gradlew :app:installDebug
```

Then launch the app from your device/emulator.

## How to Run Unit Tests

Run all local unit tests (fast, no emulator needed):

```bash
./gradlew testDebugUnitTest
```

## How to Run UI/Instrumented Tests

Requires emulator/device running.

```bash
./gradlew connectedDebugAndroidTest
```

## Notes

- The app requires internet for transaction API calls.
- If Gradle sync/build fails due to Java runtime, set Android Studio/Gradle JDK to a valid JDK installation.
