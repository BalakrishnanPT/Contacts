package `in`.balakrishnan.contacts.contactListing

import `in`.balakrishnan.contacts.R
import `in`.balakrishnan.contacts.databinding.ActivityMainBinding
import `in`.balakrishnan.contacts.repo.model.Contact
import `in`.balakrishnan.contacts.util.CustomClickListner
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var binding: ActivityMainBinding
    lateinit var contactsAdapter: ContactsAdapter
    private var contactsList: MutableList<Contact> = arrayListOf()
    private val viewModel: ContactListingViewModel by lazy {
        ViewModelProviders.of(this)[ContactListingViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_main, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.searchForText(p0)
                return true
            }

        })

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        contactsAdapter = ContactsAdapter(
            object : CustomClickListner<Contact> {
                override fun onClick(v: View, data: Contact) {
                    Log.d(TAG, "data: ${data.name} ")
                }
            })

        binding.rvContacts.layoutManager = LinearLayoutManager(this)

        binding.rvContacts.adapter = contactsAdapter

        viewModel.allContacts.observe(this, Observer {
            contactsList = it as MutableList<Contact>
            contactsAdapter.updateContacts(contactsList)
        })


    }

}
