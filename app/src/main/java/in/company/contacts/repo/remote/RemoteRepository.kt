package `in`.company.contacts.repo.remote

import `in`.company.contacts.Injection
import `in`.company.contacts.repo.model.Contact
import `in`.company.contacts.repo.model.Either


object RemoteRepository : IRemoteRepository {

    private val api = Injection.provideRemoteAPI()

    override suspend fun getContacts(): Either<List<Contact>> {

        return Either.success(api.getContacts())

    }

}