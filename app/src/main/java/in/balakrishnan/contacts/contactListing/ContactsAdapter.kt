package `in`.balakrishnan.contacts.contactListing

import `in`.balakrishnan.contacts.BR
import `in`.balakrishnan.contacts.R
import `in`.balakrishnan.contacts.repo.model.Contact
import `in`.balakrishnan.contacts.util.AutoUpdatableAdapter
import `in`.balakrishnan.contacts.util.CustomClickListner
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_contact.view.*
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
            if (contacts[position].name[0].equals(contacts[position - 1].name[0]))
                visibility = false
        holder.bind(contacts[position], visibility)
    }

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var contact: Contact

        fun bind(contact: Contact, visibility: Boolean) {
            this.contact = contact

            this.binding.setVariable(BR.item, contact);
            this.binding.setVariable(BR.labelVisibility, visibility)
            this.binding.setVariable(BR.searchString, searchString)
            this.binding.executePendingBindings();

            itemView.tvLabel.text = contact.name[0].toString()
            itemView.tvName.text = contact.name
            this.itemView.setOnClickListener {
                listener.onClick(it, this.contact)
            }
        }
    }

}