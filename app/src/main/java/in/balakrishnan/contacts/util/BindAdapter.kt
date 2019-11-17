package `in`.balakrishnan.contacts.util

import `in`.balakrishnan.contacts.repo.model.Contact
import android.view.View
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by BalaKrishnan
 */
object BindAdapter {
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

}
