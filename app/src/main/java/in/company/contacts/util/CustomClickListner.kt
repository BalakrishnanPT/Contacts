package `in`.company.contacts.util

import android.view.View

interface CustomClickListner<T> {
    fun onClick(v: View, data: T)
}