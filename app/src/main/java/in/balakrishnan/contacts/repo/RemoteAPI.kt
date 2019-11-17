package `in`.balakrishnan.contacts.repo

import `in`.balakrishnan.contacts.repo.model.Contact
import retrofit2.Call
import retrofit2.http.GET


interface RemoteAPI {
    @GET("users")
    suspend fun getContacts(): List<Contact>
}