package `in`.company.contacts.util

import `in`.company.contacts.repo.model.Contact
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object BindAdapter {
    private val TAG = javaClass.name
    /**
     * `profileImage` binding adapter is used to bind image view with view
     * At the time of binding image view with url, this function will be called
     * Glide will download and set the image
     *
     * @param view     ImageView where image should be loaded
     * @param contact url for fetching the image
     */
    @JvmStatic
    @androidx.databinding.BindingAdapter("profileImage")
    fun loadImage(view: ImageView, contact: Contact) {
        // If there is url available, Add condition to load image from given url

        view.generateImage(contact.name[0].toString())
/*
        Glide.with(view.context)
            .load(
            )
            .circleCrop()
            .fitCenter()
            .into(view)*/
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("headerText")
    fun setText(view: TextView, imageUrl: String) {
        view.text = imageUrl[0].toString()
        view.visibility = View.VISIBLE
    }

    @androidx.databinding.BindingAdapter(value = ["original", "search", "owner"], requireAll = true)
    @JvmStatic
    fun setTextIF(
        view: TextView,
        original: String,
        search: LiveData<String>,
        owner: LifecycleOwner?
    ) {
        if (owner != null)
            search.observe(owner, Observer {
                val value = search.value
                val spannable = SpannableString(original)
                if (value != null && !value.isNullOrBlankOrNullString()) {
                    val indexOf = original.indexOf(value, 0, true)
                    if (indexOf >= 0)
                        spannable.setSpan(
                            BackgroundColorSpan(Color.YELLOW),
                            indexOf, indexOf + value.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                }
                view.text = spannable
            })
        else {
            view.text = original
        }

    }


}
