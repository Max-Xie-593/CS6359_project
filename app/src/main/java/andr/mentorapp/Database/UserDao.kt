package andr.mentorapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll() : List<User>

    @Query("SELECT * FROM user WHERE userId IS :userId")
    fun findUserById(userId : String) : User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

}