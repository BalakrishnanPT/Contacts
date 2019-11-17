package `in`.balakrishnan.contacts.util

import android.view.View

/**
 * Created by BalaKrishnan
 */
interface CustomClickListner<T> {
    fun onClick(v: View, data: T)
}