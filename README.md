# SweetDogs

SweetDogs is an Android application that displays a list of cute dog pictures fetched from a public API. The project is built using MVVM architecture, Clean architecture, Flow, StateFlow, SharedFlow, Kotlin Coroutines, and Retrofit client.

## Architecture

The app is structured using the Clean architecture principles, which separates the codebase into three layers: the presentation layer, domain layer, and data layer. Each layer has its own responsibility and interacts with the other layers through well-defined interfaces.

The presentation layer is implemented using the MVVM (Model-View-ViewModel) architecture pattern, which allows for a clean separation of concerns between the UI and business logic. The ViewModel is responsible for holding the presentation logic and for communicating with the domain layer.

The domain layer contains the business logic and use cases, which represent the actions that can be performed by the app. The use cases are implemented using Kotlin Coroutines and provide an abstraction layer between the presentation and data layers.

The data layer is responsible for retrieving and storing data from various sources, such as a remote API or a local database. The data layer is implemented using Retrofit client and exposes the data through well-defined interfaces.
## Flow and Coroutines

The app uses Kotlin Coroutines for asynchronous programming and Flow for reactive programming. Flow is used to represent streams of data that can be observed and consumed by other components of the app. StateFlow and SharedFlow are used to represent reactive state and events, respectively.
## Retrofit client

The app uses Retrofit client to fetch data from a public API. The API provides a list of dog pictures in JSON format, which is parsed by the app into domain models. The data layer is responsible for communicating with the API using Retrofit client and for exposing the data to the domain layer through a well-defined interface.
## Prerequisites

  - Android Studio Arctic Fox or later
  - Android SDK version 21 or later

## Getting started

To run the app, follow these steps:

  1. Clone this repository
  2. Open the project in Android Studio
  3. Build and run the app on an emulator or physical device

## Credits

The app uses the Dog API to fetch dog pictures.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
