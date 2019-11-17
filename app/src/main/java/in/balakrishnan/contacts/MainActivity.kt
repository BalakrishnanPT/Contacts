package `in`.balakrishnan.contacts

import `in`.balakrishnan.contacts.contactListing.ContactsAdapter
import `in`.balakrishnan.contacts.contactListing.CustomClickListner
import `in`.balakrishnan.contacts.databinding.ActivityMainBinding
import `in`.balakrishnan.contacts.repo.model.Contact
import `in`.balakrishnan.contacts.repo.model.Status
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var binding: ActivityMainBinding
    lateinit var contactsAdapter: ContactsAdapter
    private var contactsList: MutableList<Contact> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        contactsAdapter = ContactsAdapter(
            object : CustomClickListner<Contact> {
                override fun onClick(v: View, data: Contact) {
                    Log.d(TAG, "data: ${data.name} ")
                    startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.fromParts(
                                "tel",
                                PhoneNumberUtils.formatNumberToE164(
                                    data.phone,
                                    Locale.getDefault().country
                                ),
                                null
                            )
                        )
                    )

                }
            })

        binding.rvContacts.layoutManager = LinearLayoutManager(this)

        binding.rvContacts.adapter = contactsAdapter

        makeAPICall()

    }

    private fun makeAPICall() {
        Injection.provideRepository().getContacts().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "Success: API call ")
                    contactsList = it.data as MutableList<Contact>
                    contactsAdapter.updateContacts(contactsList)
                }
                Status.ERROR -> Log.d(TAG, ": Error ${it.error}")
            }
        })
    }
}
