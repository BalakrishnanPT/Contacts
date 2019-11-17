package `in`.balakrishnan.contacts.repo.local

import `in`.balakrishnan.contacts.repo.model.Contact
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * from Contact ORDER BY name ASC")
    fun getAlphabetizedContact(): LiveData<List<Contact>>

    @Query("SELECT * from Contact WHERE name like :name ORDER BY name ASC ")
    fun search(name: String): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: List<Contact>)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()


}