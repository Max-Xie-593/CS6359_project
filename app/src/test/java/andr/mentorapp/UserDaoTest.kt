package andr.mentorapp

import andr.mentorapp.Database.*
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * This class tests the database queries associated with the UserScheduleDao
 *
 * @author Courtney Erbes
 * @date 10/27/19
 */
@RunWith(RobolectricTestRunner::class)
class UserDaoTest {
    private lateinit var userDao : UserDao
    private var db : MentorAppDatabase? = null

    // Before test: setup database
    @Before
    fun setup() {
        MentorAppDatabase.TEST_MODE = true
        var context = ApplicationProvider.getApplicationContext<Context>()
        db = MentorAppDatabase.invoke(context)
        userDao = db!!.userDao()
    }

    // After test: close database instance
    @After
    fun after() {
        db!!.close()
    }


    // Test: should return empty list since no users in db
    @Test
    fun getAllUsersTestEmpty() {
        var users = userDao.getAll()
        assertEquals(users.size, 0)
    }

    // Test: should return 1 since size of list
    @Test
    fun getAllUsersTestList1() {
        addStudent("stu", "stu")

        var users = userDao.getAll()
        assertEquals(users.size, 1)
    }

    // Test: should return 3 since size of list
    @Test
    fun getAllUsersTestList3() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")

        var users = userDao.getAll()
        assertEquals(users.size, 3)
    }

    // Test: should return empty list since no tutors in db
    @Test
    fun getUsersByLevelTestEmpty() {
        var users = userDao.getUsersByLevel(ADMIN_LEVEL)
        assertEquals(users.size, 0)
    }

    // Test: should return 1 since size of list
    @Test
    fun getUsersByLevelTestList1() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")

        var users = userDao.getUsersByLevel(ADMIN_LEVEL)
        assertEquals(users.size, 1)
        users = userDao.getUsersByLevel(STUDENT_LEVEL)
        assertEquals(users.size, 1)
        users = userDao.getUsersByLevel(TUTOR_LEVEL)
        assertEquals(users.size, 1)
        users = userDao.getUsersByLevel(USER_DOES_NOT_EXIST)
        assertEquals(users.size, 0)
    }

    // Test: should return 3 since size of list
    @Test
    fun getUsersByLevelTestList3() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")
        addTutor("tu2", "tu")
        addTutor("tu3", "tu")

        var users = userDao.getUsersByLevel(ADMIN_LEVEL)
        assertEquals(users.size, 1)
        users = userDao.getUsersByLevel(STUDENT_LEVEL)
        assertEquals(users.size, 1)
        users = userDao.getUsersByLevel(TUTOR_LEVEL)
        assertEquals(users.size, 3)
        users = userDao.getUsersByLevel(USER_DOES_NOT_EXIST)
        assertEquals(users.size, 0)
    }

    // Test: should insert new User into database
    @Test
    fun insertUserTest() {
        addStudent("stu", "stu")
        var users = userDao.getAll()

        assertEquals(users.size, 1)
        assertEquals(users[0].userId, "stu")
        assertEquals(users[0].userName, "stu")
        assertEquals(users[0].userLevel, STUDENT_LEVEL)
    }

    // Test: delete successfully deletes user from db
    @Test
    fun deleteTest() {
        var student = StudentUser("stu", "stu")
        userDao.insert(student)
        assertEquals(userDao.findUserByIdFromdDB("stu")!!.userId, "stu")

        userDao.delete(student)
        assertEquals(userDao.findUserByIdFromdDB("stu"), null)
    }

    // Test: update user in db successfully
    @Test
    fun updateTest() {
        var student = StudentUser("stu", "stu2")
        userDao.insert(student)
        assertEquals(userDao.findUserByIdFromdDB("stu")!!.userName, "stu2")

        userDao.update("stu", "newName")
        assertEquals(userDao.findUserByIdFromdDB("stu")!!.userName, "newName")
    }

    /*
     * The following tests test findUserById as well as findUserByIdFromDB
     * since findUserById calls findUserByIdFromDB
     */

    // Test: returns new user
    @Test
    fun findUserByIdNotInDBTest() {
        var result = userDao.findUserById("stu")

        assert(result is User)
        assertEquals(result.userId, "stu")
    }

    // Test: returns Student
    @Test
    fun findUserByIdStudentTest() {
        addStudent("stu", "stu")
        var result = userDao.findUserById("stu")

        assert(result is StudentUser)
        assertEquals(result.userId, "stu")
    }

    // Test: returns Tutor
    @Test
    fun findUserByIdTutorTest() {
        addTutor("tu", "tu")
        var result = userDao.findUserById("tu")

        assert(result is TutorUser)
        assertEquals(result.userId, "tu")
    }

    // Test: returns Admin
    @Test
    fun findUserByIdAdminTest() {
        addAdmin("ad", "ad")
        var result = userDao.findUserById("ad")

        assert(result is AdminUser)
        assertEquals(result.userId, "ad")
    }

    // Test: returns User
    @Test
    fun findUserByIdUserTest() {
        userDao.insert(StudentUser("us", "us"))
        var result = userDao.findUserById("us")

        assert(result is User)
        assertEquals(result.userId, "us")
    }

    /**
     * Helper function to add student
     * @param id        String id of student
     * @param name    String name of student
     * @return void
     */
    private fun addStudent(id: String, name: String) {
        userDao.insert(StudentUser(id, name))
    }

    /**
     * Helper function to add tutor
     * @param id        String id of tutor
     * @param name    String name of tutor
     * @return void
     */
    private fun addTutor(id: String, name: String) {
        userDao.insert(TutorUser(id, name))
    }

    /**
     * Helper function to add admin
     * @param id        String id of admin
     * @param name    String name of admin
     * @return void
     */
    private fun addAdmin(id: String, name: String) {
        userDao.insert(AdminUser(id, name))
    }
}
