package `in`.balakrishnan.contacts.model


import androidx.annotation.Keep

@Keep
data class Company(
    val bs: String = "",
    val catchPhrase: String = "",
    val name: String = ""
)