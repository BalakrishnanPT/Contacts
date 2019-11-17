package `in`.balakrishnan.contacts.repo.local

import `in`.balakrishnan.contacts.repo.model.Contact
import androidx.lifecycle.LiveData

class LocalRepository(val contactDao: ContactDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    var allContacts: LiveData<List<Contact>> = contactDao.getAlphabetizedContact()

    suspend fun insert(word: List<Contact>) {
        contactDao.insert(word)
    }

    fun search(value: String): LiveData<List<Contact>> {
       return contactDao.search("%$value%")

    }

}