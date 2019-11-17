package `in`.balakrishnan.contacts.repo

import `in`.balakrishnan.contacts.Injection
import `in`.balakrishnan.contacts.repo.model.Contact
import `in`.balakrishnan.contacts.repo.model.Either


object RemoteRepository : Repository {

    private val api = Injection.provideRemoteAPI()

    override suspend fun getContacts(): Either<List<Contact>> {

        return Either.success(api.getContacts())

    }

}