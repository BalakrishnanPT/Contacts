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
    private val TAG = javaClass.name
    private var order = 1
    var isAPICallMade = false

    fun isDataLoadedFromDB(): Boolean {
        return !isAPICallMade
    }

    fun isDataAvailableOffline(): Boolean {
        var isAvailable = false
        runBlocking {
            isAvailable = withContext(Dispatchers.Default) {
                repository.getOffline()
            }
        }
        return isAvailable
    }


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

    fun searchForText(p0: String?) {
        val search = repository.search(p0!!)
        allContacts.addSource(search) {
            allContacts.value = it
            allContacts.removeSource(search)
        }

    }

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: LocalRepository
    // LiveData gives us updated words when they change.
    val allContacts: MediatorLiveData<List<Contact>> = MediatorLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct LocalRepository.
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = LocalRepository(contactDao)
        allContacts.addSource(repository.allContacts) {
            allContacts.value = it
        }
    }

}