package `in`.balakrishnan.contacts.model


import androidx.annotation.Keep

@Keep
data class Contact(
    val address: Address = Address(),
    val company: Company = Company(),
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val username: String = "",
    val website: String = ""
)