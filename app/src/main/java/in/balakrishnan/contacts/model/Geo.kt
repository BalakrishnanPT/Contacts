package `in`.balakrishnan.contacts.model


import androidx.annotation.Keep

@Keep
data class Geo(
    val lat: String = "",
    val lng: String = ""
)