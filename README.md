# Challenge App

A modern Android application that displays brochures from various publishers. The app showcases best practices in Android development with a clean architecture and responsive UI design.

## Features

- **Brochure List**: Browse through a collection of brochures
  - Premium brochures are highlighted and span the full width
  - Regular brochures are displayed in a grid layout
  - Filter toggle to show only nearby brochures (within 5km)
  - Responsive grid layout (2 columns in portrait, 3 columns in landscape)

- **Brochure Details**: View detailed information about a selected brochure
  - Publisher name
  - Distance from current location
  - Brochure type (premium or regular)
  - Expiration status

- **Adaptive Layout**:
  - In portrait mode: Shows either the list or detail screen
  - In landscape mode: Shows both the list and detail screens side by side (master-detail pattern)

## Architecture

The app follows the **MVI (Model-View-Intent)** architecture pattern:

- **Model**: Represents the state of the UI
- **View**: Displays the UI and forwards user actions to the ViewModel
- **Intent**: Represents user actions that can change the state

This architecture provides a unidirectional data flow, making the app more predictable and easier to debug.

### Project Structure

- **data**: Contains data sources, DTOs, and mappers
  - **networking**: API client, DTOs, and network utilities
  - **mappers**: Converts between domain models and DTOs

- **domain**: Contains business logic and domain models
  - Core business entities
  - Data source interfaces

- **presentation**: Contains UI components
  - **brochure_list**: List screen components and ViewModel
  - **brochure_details**: Detail screen components
  - **model**: UI models

- **core**: Contains shared utilities and base components
  - **navigation**: Navigation components
  - **presentation/util**: UI utilities
  - **domain/util**: Domain utilities

## Technologies

### Core Libraries

- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit for building native UI
- **Material3**: Material Design components for Compose
- **Material3 Adaptive**: Components for adaptive layouts (list-detail pattern)

### Dependency Injection

- **Koin**: Lightweight dependency injection framework
  - Alternative option: The app could be rewritten to use Hilt

### Networking

- **Ktor**: Kotlin-first HTTP client
  - Alternative option: The app could be rewritten to use Retrofit

### Image Loading

- **Coil**: Image loading library for Compose

### Asynchronous Programming

- **Kotlin Coroutines**: For managing background tasks
- **Kotlin Flow**: For reactive programming

### Testing

- **JUnit**: Unit testing framework
- **Truth**: Fluent assertions library
- **Espresso**: UI testing framework
- **Compose Testing**: For testing Compose UI components
- **Robolectric + Roborazzi**: Screenshot testing on the JVM (no emulator required)

## Getting Started

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

### Screenshot testing (Robolectric + Roborazzi)

- To record screenshots (first run or when updating baselines):
  - ./gradlew test -Droborazzi.test.record=true
- To verify screenshots against baselines:
  - ./gradlew test

Screenshots and baselines will be generated under app/build/outputs/roborazzi and/or under src/test/resources/roborazzi depending on your configuration. The included sample test is BrochureListScreenshotTest which captures the empty state of BrochureListScreen.

## Minimum Requirements

- Android API level 29 (Android 10) or higher