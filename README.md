## Architecture followed 
This project is built in MVVM Architecture
### Reason
* The driving factor for choosing MVVM is Data driven architecture. 
* MVVM separates Data layer from Activity/Fragment making Views part easier for changes in View part
* Even though I have used DataBinding in this project, I didn't overkill this by moving everything to xml file. 
* I have used LiveData for easier UI updates
* Project is in Kotlin 

## Libraries used
1. ViewModel and Live Data - Viewmodel for keeping the data until the Activity / Fragment is destroyed. LiveData for Observable pattern and it is lifecycle aware.  
2. Standard design libraries - Material Design , Constraint Layout and RecyclerView 
3. Room DB for storing the response locally
4. Coroutines for making API calls and getting data from DB
5. Retrofit and Gson - Networking - I could used simple `HttpConnection` for this. But went with Retrofit and Gson for making parsing the data easier
6. Glide - Loading images, Its not currently used in this project

## Highlights
* MediatorLiveData is used in this project to update the value to UI.
    * Irrespective of the Where the data is manipulated from, i.e through API call, Search query, Order query. It is updated to single MediatorLiveData, which is observed in activity. 
    * By this pattern single data is updated and Activity doesn't need to know source of the data. Hence encapsulating the the process from Activity
* As per design there are images for the contact but in API there are no image url to load. 
* As a place holder for user image there is a circle image with the starting letter of first name on it. I have dynamically generated this images with random color.
This color is generated randomly now but color should be based on the a unique value from the contact.
However, this is not a perfect solution  
* I haven't used Navigation drawer in this project. I find there is no use to it as it doesn't have anything on user experience perspective
* AutoUpdatableAdapter is a interface with the extension function for DiffUtil in recyclerView adapter. This is interesting thing I came across recently
* ConnectionStateMonitor - Its a another great thing I came across a month back. Internet state is wrapped around live data making this as observable.
It makes easier to notify the activity/fragments about internet state in real time. We can use this technique for constant update of the process like Location  
* I haven't used Dagger for this project but I made a singleton class with all necessary dependencies and Named as `Injection`. Its a low cost [Dependency Injection](https://developer.android.com/training/dependency-injection/dagger-android) 

## Additional Things 
1. Suggestion for search - Currently commented out, As it require much effort to change the appearance of the suggested list by writing cursor aapter along with customising content provider
2. Highlighting text on search
    * When we search for the contact, the search keyword is highlighted in the result
3. Internet Connection state check
    *  No internet and No cache - No internet state
    *  No internet and Cache Available - Show list of contacts with Toast saying "No internet Loaded from Local"
    *  Internet Available and Cache available - clear local and update the datable with new entries
    *  Auto Reload when internet state changed from no internet to Internet available
    
## Things that can be updated in this, if I have extra time 
* Write test cases for the app
* Make a Repository that encapsulates Local and Remote Repository
* Create a Details page and Add call option, by splitting the extension number from phone number
* Enable proguard for release build 
