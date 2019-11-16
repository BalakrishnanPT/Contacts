package `in`.balakrishnan.contacts.repo

import `in`.balakrishnan.contacts.model.Contact
import `in`.balakrishnan.contacts.model.Either
import androidx.lifecycle.LiveData

interface Repository {
  fun getContacts(): LiveData<Either<List<Contact>>>
}