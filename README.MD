### DATA MANAGEMENT IN COMPOSABLES
-  A composable defines the layout and elements in an app screen
- the composables will contai n data that will allow users to interact with and also view
- Data inside a composable unit called STATE
- To manage this state android-kotlin utilizes the 

### VIEWMODEL
it updates in a composable which det how a composable is drawn on a screen
- apart from that it encapsulates business logic i.e. fun working on the data

## ROOM DATABASE
steps to work with room persistence databases library
1. Add the library dependencies to build.gradle
    implementation(androidx.room.room-runtime:2.7.7)
    implementation(androidx.room.room-runtime:2.7.7)
2. Create Entity : select model and annotate it with the @Entity(tableName = """)
3. Create a Datatbase Access Object(DAD) :: this will provide methods that will perfom operations on a databaswe table records
4. Create the Databases classes : will be used to track diff databases versions , ww cam/n also include seed data in this file
    i.e. seed refers tio mock data
5. Create a repository to work with the viewModel
          UI (composables ie screens) -> ViewModel -> Repository class -> Room Database
 = The repository acts as :
       a. A single source of truth your data
       b.An abstraction layer between data sources
        c.The component to handle all data operations
   6. To provide the viewmodel on compilation we will add a manager :: HILT
   To add hilt to project
   a. in build.gradle project level add hilt plugin : id("com.google.dagger.jilt.android") version "2.56.1"apply false
   b. in build.gradle app level add hilt dependency plugins
   dependencies {
   implementation(com.google.dagger:hilt-android:2.48)
   implementation("androidx.hilt:hilt-:navigation-compose:1.2.0")
    }
## Dependency injection
is a software design pattern that manages dependencies btwn diff components(classes,obj,etc.) in a clean and maintainable way
            
        MainActivity (launched screen)
                - showcase DashboardScreen
        DashboardScreen - list of todos
                        - add to form , alertdialog
        Dashboardviewmodel - 0bserve changes on data cred provided data via the repository

        TodoRepository - gets data methods from DAO and provides it to the VM

Steps
1. Create an application class : will denote our app as a hilt app : allowing hilt to be used for DI
2. Add the created hilt class in the manifest
3. Tell hilt how to provide repository to  the VM , create a module class to provide the info.
4. Provide hilt to our viewmodel
5. provide hiltViewModel to screen














   