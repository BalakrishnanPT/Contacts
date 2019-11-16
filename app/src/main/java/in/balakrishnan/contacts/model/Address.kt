package `in`.balakrishnan.contacts.model


import androidx.annotation.Keep

@Keep
data class Address(
    val city: String = "",
    val geo: Geo = Geo(),
    val street: String = "",
    val suite: String = "",
    val zipcode: String = ""
)