# Note-Taking App
This is a simple Android note-taking application developed using Kotlin. It allows users to create, view, and delete notes. 

## Download APK
The APK can be downloaded for testing purposes. The link will be provided upon successful build and release.

- [APK DEMO LINK](https://github.com/daviddagb2/Note-taking-application/raw/master/apk/app-debug.apk)

## Project Structure

The project follows the MVVM (Model-View-ViewModel) architecture pattern and uses the Android Room library for data persistence.

### Project Components

- `core` module: Contain core components for the app like the BaseViewModel
- `data` package: Includes data source, database, and repository classes.
- `data/database`: Includes packages related to Room database implementation
- `data/database/dao`: Includes all Dao (Data Access Object) with the queries of the project
- `data/database/entities`: All Database entities
- `data/database/mappers`: These mappers enable the conversion of business model data into database entity representations.
- `data/repository`: Includes all the data repositories related to the app.
- `di`: dependency Injection
- `domain` package: Contains business logic and use cases. Also contains the Bussine Models.
- `presentation` package: Contains UI-related components such as activities, fragments, and adapters.
- `utils` package: Includes utility classes and constants.

## Running the Application

To run the application, follow these steps:

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/daviddagb2/Note-taking-application.git


2. Open Android Studio.
3. In Android Studio, select "File" from the top menu bar, and then select "Open." Navigate to the location where you cloned the repository and select the project folder.
4. Wait for Android Studio to sync the project and set up dependencies.
5. Connect an Android device to your computer or use an Android emulator.
6. Ensure that Android Studio has detected your device or emulator.
7. Click the "Run" button in Android Studio (usually a play icon) or use the keyboard shortcut Shift + F10.
8. Android Studio will build and run the app on your selected device or emulator.
9. Wait for the app to launch. You should see the list of notes and be able to interact with the app.

## Design Decisions

- The MVVM architecture pattern is used to separate concerns and maintain a clean and testable codebase.
- Room is used for data persistence, allowing efficient storage and retrieval of notes.
- Deletion functionality is implemented with a swipe gesture and animation for a smooth user experience. Also is possible to delete a note from the detail
- Proper error handling is in place to handle edge cases, such as empty fields.

## Evaluation Criteria
This project is evaluated based on the following criteria:
- Code Readability: The code should be clear and well-organized.
- Use of Kotlin: Kotlin-specific features should be appropriately utilized.
- Data Handling: Data should be effectively persisted and retrieved from the Room database.
- User Interface Design: The user interface should be user-friendly and visually appealing.
- Error Handling: The application should handle errors gracefully.

## Extra Points
To earn extra points, consider the following:
- Implement unit tests for key functions to ensure code reliability, for this project unit tests were implemented for the use cases.
- Utilize dependency injection with Dagger.

## Tech stack & Open-source libraries
- [Android Kotlin](https://developer.android.com/kotlin)
- [MVVM Architecture](https://developer.android.com/jetpack/guide?gclsrc=aw.ds&gclid=CjwKCAjw_ISWBhBkEiwAdqxb9up3VFjuEbls5467JIVkyOdTgg-z-_NntWqaSFgkJr5qt6EmGsb7vxoCj9kQAvD_BwE)
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room?gclsrc=aw.ds&gclid=CjwKCAjw_ISWBhBkEiwAdqxb9r5eN7phvDex2hZ5gGRkm1GckeBjkR8LNm3GwDU_4EC8OdDDtDxt_xoCH8QQAvD_BwE)
- [Dagger Hilt](https://dagger.dev/hilt/)

## Contact me

- [Website](https://gonzalezblanchard.com/)
- [LinkedIn](https://www.linkedin.com/in/davidgb2021/)
- [Github](https://github.com/daviddagb2)
- [Blog](https://blanchardspace.wordpress.com/)
- [Linktree](https://linktr.ee/davidgb77)
- [Youtube](https://www.youtube.com/@developergb)
