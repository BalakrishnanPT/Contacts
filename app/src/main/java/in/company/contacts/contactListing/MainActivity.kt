package `in`.company.contacts.contactListing

import `in`.company.contacts.R
import `in`.company.contacts.databinding.ActivityMainBinding
import `in`.company.contacts.repo.model.Contact
import `in`.company.contacts.util.*
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var binding: ActivityMainBinding
    lateinit var contactsAdapter: ContactsAdapter
    private var contactsList: MutableList<Contact> = arrayListOf()
    private val viewModel: ContactListingViewModel by lazy {
        ViewModelProviders.of(this)[ContactListingViewModel::class.java]
    }

    private val connectionStateMonitor: ConnectionStateMonitor by lazy {
        ConnectionStateMonitor(application)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return searchViewSetup(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort -> {
                viewModel.orderChange()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        contactsAdapter = ContactsAdapter(
            object : CustomClickListner<Contact> {
                override fun onClick(v: View, data: Contact) {
                }
            })

        binding.rvContacts.adapter = contactsAdapter

        viewModel.allContacts.observe(this, Observer {
            contactsList = it as MutableList<Contact>
            if (contactsList.size > 0) {
                setListingVisibility(true)
            } else {
                setListingVisibility(
                    false
                )
            }
            contactsAdapter.updateContacts(contactsList)
        })

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.searchForText(query)
                contactsAdapter.searchString = query
            }
        }

        Transformations.distinctUntilChanged(connectionStateMonitor).observe(this, Observer {
            log("connectionStateMonitor called $it")
            if (it) {
                flEs.findViewById<TextView>(R.id.esTv).text = AppConstants.errorNoSearchResult
                if (!viewModel.isAPICallMade)
                    viewModel.makeAPICall()
                setListingVisibility(true)
            } else {
                log("isDataAvailableOffline ${viewModel.isDataAvailableOffline()}")
                log("isDataLoadedFromDB ${viewModel.isDataLoadedFromDB()}")
                if (viewModel.isDataAvailableOffline()) {
                    setListingVisibility(true)
                    if (viewModel.isDataLoadedFromDB())
                        this@MainActivity.logToast("No internet, Loaded from Local")
                } else {
                    setListingVisibility(false)
                }
            }
        })


    }

    private fun setListingVisibility(b: Boolean) {
        if (!b) {
            val message = if (viewModel.isDataAvailableOffline()) AppConstants.errorNoSearchResult
            else AppConstants.errorNoInternet
            flEs.findViewById<TextView>(R.id.esTv).text = message
        }
        rvContacts.setVisible(b)
        flEs.setVisible(!b)
    }

    private fun searchViewSetup(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val sortItem = menu.findItem(R.id.sort)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        val componentName = ComponentName(this@MainActivity, MainActivity::class.java)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                sortItem.isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                sortItem.isVisible = true
                return true
            }

        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                SearchRecentSuggestions(
                    this@MainActivity,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                    .saveRecentQuery(p0, null)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    contactsAdapter.searchString = p0
                    viewModel.searchForText(p0)
                }
                return true
            }

        })

        return true
    }


}
