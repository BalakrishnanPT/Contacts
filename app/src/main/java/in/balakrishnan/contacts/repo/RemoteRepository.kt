package `in`.balakrishnan.contacts.repo

import `in`.balakrishnan.contacts.Injection
import `in`.balakrishnan.contacts.model.ApiError
import `in`.balakrishnan.contacts.model.Contact
import `in`.balakrishnan.contacts.model.Either
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RemoteRepository : Repository {

    private val api = Injection.provideRemoteAPI()

    override fun getContacts(): LiveData<Either<List<Contact>>> {
        val liveData = MutableLiveData<Either<List<Contact>>>()

        api.getContacts().enqueue(object : Callback<List<Contact>> {
            override fun onResponse(
                call: Call<List<Contact>>?,
                response: Response<List<Contact>>?
            ) {
                if (response != null && response.isSuccessful) {
                    liveData.value = Either.success(response.body())
                } else {
                    liveData.value = Either.error(ApiError.CONTACTS, null)
                }
            }

            override fun onFailure(call: Call<List<Contact>>?, t: Throwable?) {
                liveData.value = Either.error(ApiError.CONTACTS, null)
            }
        })

        return liveData
    }
}