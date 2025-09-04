# TranslatorFromEng

**TranslatorFromEng** is an Android app for translating English words to Russian using the Skyeng Dictionary API (https://dictionary.skyeng.ru/). The app supports offline access by caching translations in a local database and provides features like history tracking and favoriting translations. Built with Clean Architecture, Jetpack Compose, and modern Android libraries.

---

## 🧰 Key Features

* **Main Screen**:
  * Allows users to input an English word and view its Russian translation.
  * Displays a history of previous translations in a scrollable list.
  * Supports pull-to-refresh for updating the history list.
  * Includes a search button to initiate translation.
* **Favorites Screen**:
  * Shows a list of favorited translations.
  * Allows users to toggle favorite status or delete translations.
* **Local Storage**:
  * Persists translation data in a Room database for offline access.
* **Navigation**:
  * Seamless navigation between Main and Favorites screens using Jetpack Compose Navigation.

---

## 📁 Project Structure

```
translatorfromeng/
├── data/                 # Data layer
│   ├── local/            # Room database, DAOs, and entities
│   │   ├── model/        # Local data models (TranslationEntity)
│   ├── mapper/           # Mappers for converting between entity and domain models
│   ├── remote/           # Retrofit API service and DTOs
│   │   ├── dto/          # Data transfer objects for API responses
│   ├── repository/       # Repository implementations
├── di/                   # Dependency injection (Hilt)
├── domain/               # Business logic layer
│   ├── model/            # Domain models
│   ├── repository/       # Repository interfaces
│   ├── usecase/          # Use cases for business logic
├── presentation/         # UI layer (Jetpack Compose)
│   ├── navigation/       # Navigation setup
│   ├── ui/               # UI components and screens
│   │   ├── component/    # Reusable UI components
│   │   ├── screen/       # Screen composables
│   ├── viewmodel/        # ViewModels for state management
│   │   ├── state/        # UI state definitions
├── ui/                   # App theming
│   ├── theme/            # Theme, typography, and color definitions
```

---

## ⚙️ Technologies Used

* **Kotlin**, **Jetpack Compose** for declarative UI.
* **Room** for local storage of translation data.
* **Dagger Hilt** for dependency injection.
* **Retrofit** for API calls to https://dictionary.skyeng.ru/.
* **Kotlin Coroutines + Flow** for asynchronous operations and state management.
* **MVVM + Clean Architecture** for modular and maintainable design.

---

## 📊 Technical Highlights

* **API Integration**:
  * Fetches translations from https://dictionary.skyeng.ru/ using Retrofit with Gson for JSON parsing.
* **Local Storage**:
  * Stores translation history and favorites in a Room database, enabling offline access.
* **State Management**:
  * Uses StateFlow in ViewModels for reactive UI updates.
* **Offline Support**:
  * Falls back to cached data when no network connection is available.
* **Error Handling**:
  * Displays user-friendly error messages for failed translations.

---

## 📃 Code Style and Conventions

* Follows Clean Architecture with distinct data, domain, and presentation layers.
* Uses MVVM for separation of UI and business logic.
* Employs Dagger Hilt for dependency injection.
* Leverages Jetpack Compose for modern, declarative UI design.
* Ensures type safety with Kotlin’s nullability features.
* Uses coroutines and Flow for asynchronous operations.

---

## 🚀 Getting Started

1. Install Android Studio (latest stable version recommended).
2. Clone the repository:

   ```bash
   git clone https://github.com/Bphoenix134/TranslatorFromEng.git
   ```
3. Build and run the app:

   ```bash
   ./gradlew assembleDebug
   ```
4. Main entry point: `MainActivity`.

---

## 📸 Screenshots

Below are placeholders for screenshots showcasing the app's key screens (images not provided in the input):

| Main Screen | Favorites Screen | Empty List |
|-------------|------------------|------------|
| <img src="screenshots/mainscreen.jpg" width="200"/> | <img src="screenshots/favoritesscreen.jpg" width="200"/> | <img src="screenshots/empty_list.jpg" width="200"/> |

---

## 📌 Notes

* All translation data is cached locally using Room for offline access.
* External API (https://dictionary.skyeng.ru/) is used for fetching translations.
* The app supports dynamic theming with light and dark modes using Material 3.
* Custom fonts (FindSansPro) are used for typography.