package `in`.company.contacts.repo.remote

import `in`.company.contacts.repo.model.Contact
import `in`.company.contacts.repo.model.Either

interface IRemoteRepository {
  suspend fun getContacts(): Either<List<Contact>>
}