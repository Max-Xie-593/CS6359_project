package andr.mentorapp

import andr.mentorapp.Database.*
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll() : List<User>

    @Query("SELECT * FROM user WHERE userLevel = $TUTOR_LEVEL")
    fun getAllTutors() : List<TutorUser>

    @Query("SELECT * FROM user WHERE userLevel = $ADMIN_LEVEL")
    fun getAllAdmins() : List<AdminUser>

    @Query("SELECT * FROM user WHERE userLevel IS :level")
    fun getUsersByLevel(level: Int) : List<User>

    @Query("SELECT * FROM user WHERE userId IS :userId")
    fun findUserByIdFromdDB(userId : String) : User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("UPDATE user SET userName = :newName WHERE userId = :userId")
    fun update(userId: String, newName : String)

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
