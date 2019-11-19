package `in`.company.contacts.repo.model


import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class Contact(
    @Embedded
    val address: Address = Address(),
    @Embedded
    val company: Company = Company(),
    val email: String = "",
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val username: String = "",
    val website: String = ""
)