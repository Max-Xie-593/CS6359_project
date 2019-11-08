package andr.mentorapp

import andr.mentorapp.Database.*
import androidx.room.*

/**
 * Class for handling the data accession for TutorSchedule form the database
 *
 * @author Mugdha Gupta
 * @date 9/29/19
 */
@Dao
interface UserDao {
    /**
     * Return list of all Users in database
     *
     * @return List<User>  all users in the db
     */
    @Query("SELECT * FROM user")
    fun getAll() : List<User>

    /**
     * Return list of all Users in database at a certain user level
     *
     * @param level         int representing user's level
     * @return List<User>   all Users in the db with level
     */
    @Query("SELECT * FROM user WHERE userLevel IS :level")
    fun getUsersByLevel(level: Int) : List<User>

    /**
     * Return User from database with id == userId
     *
     * @param userId    id of the user
     * @return User     user with the userId in the db or null
     */
    @Query("SELECT * FROM user WHERE userId IS :userId")
    fun findUserByIdFromdDB(userId : String) : User?

    /**
     * insert user into db
     *
     * @param user  User object to add to db
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    /**
     * Delete user from db
     *
     * @param user      User to delete from db
     * @return void
     */
    @Delete
    fun delete(user: User)

    /**
     * Update user in db
     *
     * @param userId    id of User that has changing data
     * @param newName   new name for user
     * @return void
     */
    @Query("UPDATE user SET userName = :newName WHERE userId = :userId")
    fun update(userId: String, newName : String)

    /**
     * Return User with id = userId of type StudentUser, AdminUser, or TutorUser
     *
     * @param userId      Id of User to fetch
     * @return User       found User or new User
     */
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
