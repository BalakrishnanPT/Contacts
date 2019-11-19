package `in`.company.contacts.repo.model


import androidx.annotation.Keep
import androidx.room.Embedded

@Keep
data class Address(
    val city: String = "",
    @Embedded
    val geo: Geo = Geo(),
    val street: String = "",
    val suite: String = "",
    val zipcode: String = ""
)