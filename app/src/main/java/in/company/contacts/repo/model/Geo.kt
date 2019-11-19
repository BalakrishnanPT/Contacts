package `in`.company.contacts.repo.model


import androidx.annotation.Keep

@Keep
data class Geo(
    val lat: String = "",
    val lng: String = ""
)