package andr.mentorapp

import andr.mentorapp.Database.*
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll() : List<User>

    @Query("SELECT * FROM user WHERE userId IS :userId")
    fun findUserByIdFromdDB(userId : String) : User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Transaction
    fun findUserById(userId: String): User{
        val user = findUserByIdFromdDB(userId)

        if(user == null)
            return NewUser(userId)

        when(user.userLevel){
            STUDENT_LEVEL -> return StudentUser(user.userId, user.userName)
            TUTOR_LEVEL -> return TutorUser(user.userId, user.userName)
            ADMIN_LEVEL -> return AdminUser(user.userId, user.userName)
        }

        return user
    }
}