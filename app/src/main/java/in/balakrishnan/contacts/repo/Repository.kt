package `in`.balakrishnan.contacts.repo

import `in`.balakrishnan.contacts.repo.model.Contact
import `in`.balakrishnan.contacts.repo.model.Either
import androidx.lifecycle.LiveData

interface Repository {
  fun getContacts(): LiveData<Either<List<Contact>>>
}