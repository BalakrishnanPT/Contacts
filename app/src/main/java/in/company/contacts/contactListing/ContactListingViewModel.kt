package `in`.company.contacts.contactListing

import `in`.company.contacts.Injection
import `in`.company.contacts.repo.local.ContactDatabase
import `in`.company.contacts.repo.local.LocalRepository
import `in`.company.contacts.repo.model.Contact
import `in`.company.contacts.repo.model.Status
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ContactListingViewModel(application: Application) : AndroidViewModel(application) {

    val searchText = MediatorLiveData<String>().apply { value = "" }
    private val TAG = javaClass.name
    private var order = 1
    var isAPICallMade = false

    /**
     * The ViewModel maintains a reference to the repository to get data.
     */
    private val repository: LocalRepository
    /**
     * LiveData gives us updated words when they change.
     */
    val allContacts: MediatorLiveData<List<Contact>> = MediatorLiveData()

    init {
        // Gets reference to ContactDao from ContactDatabase to construct
        // the correct LocalRepository.
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = LocalRepository(contactDao)
        allContacts.addSource(repository.allContacts) {
            allContacts.value = it
        }
    }

    /**
     * This function is used to check if the Data is loaded either from Local DB or API
     */
    fun isDataLoadedFromDB(): Boolean {
        return !isAPICallMade
    }

    /**
     * This function is used to check if Data is available in Local DB
     */
    fun isDataAvailableOffline(): Boolean {
        var isAvailable = false
        runBlocking {
            isAvailable = withContext(Dispatchers.Default) {
                repository.getOffline()
            }
        }
        return isAvailable
    }

    /**
     * This function triggers the API call for contacts
     */
    fun makeAPICall() {
        isAPICallMade = true
        viewModelScope.launch {
            val response = Injection.provideRepository().getContacts()
            if (response.status == Status.SUCCESS) {
                repository.delete()
                repository.insert(response.data!!)
            } else
                Log.d(TAG, "Error Please handle ${response.error} ")

        }
    }

    /**
     * This function keeps track of the current order and changes the order of the contacts.
     * Either in Ascending(1) or Descending(2)
     */
    fun orderChange() {
        order = if (order == 1) {
            2
        } else {
            1
        }
        val search: LiveData<List<Contact>> = repository.getContact(order)
        allContacts.addSource(search) {
            allContacts.value = it
            allContacts.removeSource(search)
        }

    }

    /**
     * This function is used to trigger search and it initiate the query using DAO
     */
    fun searchForText(p0: String?) {
        searchText.value = p0;
        val search = repository.search(p0!!)
        allContacts.addSource(search) {
            allContacts.value = it
            allContacts.removeSource(search)
        }

    }

}