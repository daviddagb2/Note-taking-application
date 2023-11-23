# Note-Taking App
This is a simple Android note-taking application developed using Kotlin. It allows users to create, view, and delete notes. 

## Project Structure

The project follows the MVVM (Model-View-ViewModel) architecture pattern and uses the Android Room library for data persistence.

### Project Components

- `core` module: Contain core componentes for the app like the BaseViewModel
- `data` package: Includes data source, database, and repository classes.
- `data/database`: Includes packages related with Room database implementation
- `data/database/dao`: Includes all Dao (Data Access Object) with the querys of project
- `data/database/entities`: All Database entities
- `data/database/mappers`: These mappers enable the conversion of business model data into database entity representations.
- `data/repository`: Includes all the data repositories related with the app.
- `di`: dependency Injection
- `domain` package: Contains business logic and use cases. Also contains the Bussine Models.
- `presentation` package: Contains UI-related components such as activities, fragments, and adapters.
- `utils` package: Includes utility classes and constants.

## Running the Application

To run the application, follow these steps:

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/your-username/note-taking-app.git

   
