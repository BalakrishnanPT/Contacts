package `in`.company.contacts.contactListing

import `in`.company.contacts.BR
import `in`.company.contacts.R
import `in`.company.contacts.repo.model.Contact
import `in`.company.contacts.util.AutoUpdatableAdapter
import `in`.company.contacts.util.CustomClickListner
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates


class ContactsAdapter(
    private val listener: CustomClickListner<Contact>
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>(),
    AutoUpdatableAdapter {
    var searchString = ""
    var contacts: List<Contact> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    private val TAG = javaClass.name
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                R.layout.list_item_contact,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount() = contacts.size

    fun updateContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var visibility = true
        if (position > 0)
            if (contacts[position].name[0] == contacts[position - 1].name[0])
                visibility = false
        holder.bind(contacts[position], visibility)
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, visibility: Boolean) {
            this.binding.setVariable(BR.item, contact);
            this.binding.setVariable(BR.labelVisibility, visibility)
            this.binding.setVariable(BR.searchString, searchString)
            this.binding.setVariable(BR.listener, listener)
            this.binding.executePendingBindings()

        }
    }

}