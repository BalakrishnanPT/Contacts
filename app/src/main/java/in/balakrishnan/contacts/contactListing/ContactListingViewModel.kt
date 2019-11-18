package `in`.balakrishnan.contacts.contactListing

import `in`.balakrishnan.contacts.Injection
import `in`.balakrishnan.contacts.repo.local.ContactDatabase
import `in`.balakrishnan.contacts.repo.local.LocalRepository
import `in`.balakrishnan.contacts.repo.model.Contact
import `in`.balakrishnan.contacts.repo.model.Status
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by BalaKrishnan
 */
class ContactListingViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = javaClass.name
    fun makeAPICall() {
        viewModelScope.launch {
            val response = Injection.provideRepository().getContacts()
            if (response.status == Status.SUCCESS)
                repository.insert(response.data!!)
            else
                Log.d(TAG, "Error Please handle ${response.error} ")

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
        makeAPICall()
    }

}