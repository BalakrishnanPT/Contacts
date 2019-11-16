package `in`.balakrishnan.contacts.repo

import `in`.balakrishnan.contacts.model.Contact
import retrofit2.Call
import retrofit2.http.GET


interface RemoteAPI {
    @GET("users")
    fun getContacts(): Call<List<Contact>>
}