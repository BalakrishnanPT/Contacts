package `in`.balakrishnan.contacts.repo.model


import androidx.annotation.Keep

@Keep
data class Company(
    val bs: String = "",
    val catchPhrase: String = "",
    val name: String = ""
)