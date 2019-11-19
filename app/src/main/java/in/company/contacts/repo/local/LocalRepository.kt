package `in`.company.contacts.repo.local

import `in`.company.contacts.repo.model.Contact
import androidx.lifecycle.LiveData

class LocalRepository(val contactDao: ContactDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    var allContacts: LiveData<List<Contact>> = contactDao.getAlphabetizedContact()

    var allContactsreversed: LiveData<List<Contact>> = contactDao.getContactReversed()

    fun getContact(type: Int): LiveData<List<Contact>> {
        return if (type == 1) {
            contactDao.getAlphabetizedContact()
        } else {
            contactDao.getContactReversed()
        }

    }

    fun getOffline(): Boolean {
        val alphabetizedContact = contactDao.getData()
        return alphabetizedContact > 0
    }

    suspend fun insert(word: List<Contact>) {
        contactDao.insert(word)
    }

    fun search(value: String): LiveData<List<Contact>> {
        return contactDao.search("%$value%")

    }

    suspend fun delete() {
        contactDao.deleteAll()
    }

}