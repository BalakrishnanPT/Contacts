package `in`.company.contacts.repo.remote

import `in`.company.contacts.repo.model.Contact
import retrofit2.http.GET


interface RemoteAPI {
    @GET("users")
    suspend fun getContacts(): List<Contact>
}