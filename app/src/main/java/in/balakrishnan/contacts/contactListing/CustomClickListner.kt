package `in`.balakrishnan.contacts.contactListing

import android.view.View

/**
 * Created by BalaKrishnan
 */
interface CustomClickListner<T> {
    fun onClick(v: View, data: T)
}