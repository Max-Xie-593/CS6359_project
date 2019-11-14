package andr.mentorapp

import andr.mentorapp.Database.*
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabaseManagerTest {
    // Before test: setup database
    @Before
    fun setup() {
        MentorAppDatabase.TEST_MODE = true
        var context = ApplicationProvider.getApplicationContext<Context>()
        DatabaseManager.init(context)
    }

    // After test: close database instance
    @After
    fun after() {
        var context = ApplicationProvider.getApplicationContext<Context>()
        MentorAppDatabase.invoke(context).close()
    }

    // Test: returns new user
    @Test
    fun findUserByIdNotInDBTest() {
        var result = DatabaseManager.getUserById("stu")

        assert(result is User)
        Assert.assertEquals(result.userId, "stu")
    }

    // Test: returns Student
    @Test
    fun findUserByIdStudentTest() {
        addStudent("stu", "stu")
        var result = DatabaseManager.getUserById("stu")

        assert(result is StudentUser)
        Assert.assertEquals(result.userId, "stu")
    }

    // Test: returns Tutor
    @Test
    fun findUserByIdTutorTest() {
        addTutor("tu", "tu")
        var result = DatabaseManager.getUserById("tu")

        assert(result is TutorUser)
        Assert.assertEquals(result.userId, "tu")
    }

    // Test: returns Admin
    @Test
    fun findUserByIdAdminTest() {
        addAdmin("ad", "ad")
        var result = DatabaseManager.getUserById("ad")

        assert(result is AdminUser)
        Assert.assertEquals(result.userId, "ad")
    }

    // Test: returns User
    @Test
    fun findUserByIdUserTest() {
        DatabaseManager.insertUser(StudentUser("us", "us"))
        var result = DatabaseManager.getUserById("us")

        assert(result is User)
        Assert.assertEquals(result.userId, "us")
    }

    // Test: should return empty list since no users in db
    @Test
    fun getAllUsersTestEmpty() {
        var users = DatabaseManager.getAllUsers()
        Assert.assertEquals(users.size, 0)
    }

    // Test: should return 1 since size of list
    @Test
    fun getAllUsersTestList1() {
        addStudent("stu", "stu")

        var users = DatabaseManager.getAllUsers()
        Assert.assertEquals(users.size, 1)
    }

    // Test: should return 3 since size of list
    @Test
    fun getAllUsersTestList3() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")

        var users = DatabaseManager.getAllUsers()
        Assert.assertEquals(users.size, 3)
    }

    // Test: should return 5 since size of list
    @Test
    fun getAllUsersTestList5() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")
        addTutor("tu2", "tu")
        addTutor("tu3", "tu")

        var users = DatabaseManager.getAllUsers()
        Assert.assertEquals(users.size, 5)
    }

    // Test: should return empty list since no tutors in db
    @Test
    fun getAllTutorsTestEmpty() {
        var users = DatabaseManager.getAllTutors()
        Assert.assertEquals(users.size, 0)
    }

    // Test: should return 1 since size of list
    @Test
    fun getAllTutorsTestList1() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")

        var users = DatabaseManager.getAllTutors()
        Assert.assertEquals(users.size, 1)
    }

    // Test: should return 3 since size of list
    @Test
    fun getAllTutorsTestList3() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")
        addTutor("tu2", "tu")
        addTutor("tu3", "tu")

        var users = DatabaseManager.getAllTutors()
        Assert.assertEquals(users.size, 3)
    }

    // Test: should return empty list since no tutors in db
    @Test
    fun getAllAdminsTestEmpty() {
        var users = DatabaseManager.getAllAdmins()
        Assert.assertEquals(users.size, 0)
    }

    // Test: should return 1 since size of list
    @Test
    fun getAllAdminsTestList1() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")

        var users = DatabaseManager.getAllAdmins()
        Assert.assertEquals(users.size, 1)
    }

    // Test: should return 3 since size of list
    @Test
    fun getAllAdminsTestList3() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")
        addAdmin("ad2", "ad")
        addAdmin("ad3", "ad")

        var users = DatabaseManager.getAllAdmins()
        Assert.assertEquals(users.size, 3)
    }

    // Test: should return empty list since no tutors in db
    @Test
    fun getAllStudentsTestEmpty() {
        var users = DatabaseManager.getAllStudents()
        Assert.assertEquals(users.size, 0)
    }

    // Test: should return 1 since size of list
    @Test
    fun getAllStudentsTestList1() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")

        var users = DatabaseManager.getAllStudents()
        Assert.assertEquals(users.size, 1)
    }

    // Test: should return 3 since size of list
    @Test
    fun getAllStudentsTestList3() {
        addStudent("stu", "stu")
        addTutor("tu", "tu")
        addAdmin("ad", "ad")
        addStudent("stu2", "stu")
        addStudent("stu3", "stu")

        var users = DatabaseManager.getAllStudents()
        Assert.assertEquals(users.size, 3)
    }

    // Test: should insert new User into database
    @Test
    fun insertUserTest() {
        addStudent("stu", "stu")
        var users = DatabaseManager.getAllUsers()

        Assert.assertEquals(users.size, 1)
        Assert.assertEquals(users[0].userId, "stu")
        Assert.assertEquals(users[0].userName, "stu")
        Assert.assertEquals(users[0].userLevel, STUDENT_LEVEL)
    }

    // Test: delete successfully deletes user from db
    @Test
    fun deleteTest() {
        var student = StudentUser("stu", "stu")
        DatabaseManager.insertUser(student)
        Assert.assertEquals(DatabaseManager.getUserById("stu")!!.userId, "stu")

        DatabaseManager.deleteUser(student)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 0)
    }

    // Test: update user in db successfully
    @Test
    fun updateTest() {
        var student = StudentUser("stu", "stu2")
        DatabaseManager.insertUser(student)
        Assert.assertEquals(DatabaseManager.getUserById("stu")!!.userName, "stu2")

        DatabaseManager.updateUser("stu", "newName")
        Assert.assertEquals(DatabaseManager.getUserById("stu")!!.userName, "newName")
    }

    // Test: should return all Tutor Schedules in the database; list length of 0
    @Test
    fun getAllTutorSchedulesTestList0() {
        var tutorSchedules = DatabaseManager.getAllTutorSchedules()

        Assert.assertEquals(tutorSchedules.size, 0)
    }

    // Test: should return all Tutor Schedules in the database; list length of 1
    @Test
    fun getAllTutorSchedulesTestList1() {
        addTutor("new_tutor", "tu")

        addSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        var tutorSchedules = DatabaseManager.getAllTutorSchedules()

        Assert.assertEquals(tutorSchedules.size, 1)
        Assert.assertEquals(tutorSchedules[0].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[0].schedule.day, "Monday")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftStart, "10:00:00")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftEnd, "11:30:00")
    }

    // Test: should return all Tutor Schedules in the database; list length of 3
    @Test
    fun getAllTutorSchedulesTestList3() {
        addTutor("new_tutor", "tu")
        addTutor("new_tutor2", "tu")
        addTutor("new_tutor3", "tu")

        addSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        addSchedule("new_tutor2", "Tuesday", "11:00:00", "12:30:00")
        addSchedule("new_tutor3", "Thursday", "14:00:00", "15:30:00")
        var tutorSchedules = DatabaseManager.getAllTutorSchedules()

        Assert.assertEquals(tutorSchedules.size, 3)
        Assert.assertEquals(tutorSchedules[0].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[0].schedule.day, "Monday")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftStart, "10:00:00")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftEnd, "11:30:00")
        Assert.assertEquals(tutorSchedules[1].tutorId, "new_tutor2")
        Assert.assertEquals(tutorSchedules[1].schedule.day, "Tuesday")
        Assert.assertEquals(tutorSchedules[1].schedule.shiftStart, "11:00:00")
        Assert.assertEquals(tutorSchedules[1].schedule.shiftEnd, "12:30:00")
        Assert.assertEquals(tutorSchedules[2].tutorId, "new_tutor3")
        Assert.assertEquals(tutorSchedules[2].schedule.day, "Thursday")
        Assert.assertEquals(tutorSchedules[2].schedule.shiftStart, "14:00:00")
        Assert.assertEquals(tutorSchedules[2].schedule.shiftEnd, "15:30:00")
    }

    // Test: should return all Tutor Schedules for tutor in the database; list length of 0
    @Test
    fun getAllSchedulesByTutorIdTestList0() {
        addSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        var tutorSchedules = DatabaseManager.getSchedulesByTutorId("tutor_id")

        Assert.assertEquals(tutorSchedules.size, 0)
    }

    // Test: should return all Tutor Schedules for tutor in the database; list length of 1
    @Test
    fun getAllSchedulesByTutorIdTestList1() {
        addTutor("new_tutor", "tu")

        addSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        var tutorSchedules = DatabaseManager.getSchedulesByTutorId("new_tutor")

        Assert.assertEquals(tutorSchedules.size, 1)
        Assert.assertEquals(tutorSchedules[0].day, "Monday")
        Assert.assertEquals(tutorSchedules[0].shiftStart, "10:00:00")
        Assert.assertEquals(tutorSchedules[0].shiftEnd, "11:30:00")
    }

    // Test: should return all Tutor Schedules for tutor in the database; list length of 3
    @Test
    fun getAllSchedulesByTutorIdTestList3() {
        addTutor("new_tutor", "tu")

        addSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        addSchedule("new_tutor", "Tuesday", "11:00:00", "12:30:00")
        addSchedule("new_tutor", "Thursday", "14:00:00", "15:30:00")
        var tutorSchedules = DatabaseManager.getAllTutorSchedules()

        Assert.assertEquals(tutorSchedules.size, 3)
        Assert.assertEquals(tutorSchedules[0].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[0].schedule.day, "Monday")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftStart, "10:00:00")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftEnd, "11:30:00")
        Assert.assertEquals(tutorSchedules[1].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[1].schedule.day, "Tuesday")
        Assert.assertEquals(tutorSchedules[1].schedule.shiftStart, "11:00:00")
        Assert.assertEquals(tutorSchedules[1].schedule.shiftEnd, "12:30:00")
        Assert.assertEquals(tutorSchedules[2].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[2].schedule.day, "Thursday")
        Assert.assertEquals(tutorSchedules[2].schedule.shiftStart, "14:00:00")
        Assert.assertEquals(tutorSchedules[2].schedule.shiftEnd, "15:30:00")
    }

    // Test: should insert new tutor schedule into database and then delete it
    @Test
    fun insertAndDeleteTutorScheduleTest() {
        addTutor("new_tutor", "tu")

        var schedule = Schedule("Thursday", "14:00:00", "15:30:00")
        DatabaseManager.insertTutorSchedule("new_tutor", schedule)
        var tutorSchedules = DatabaseManager.getAllTutorSchedules()

        Assert.assertEquals(tutorSchedules.size, 1)
        Assert.assertEquals(tutorSchedules[0].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[0].schedule.day, "Thursday")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftStart, "14:00:00")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftEnd, "15:30:00")

        DatabaseManager.deleteTutorSchedule("new_tutor", schedule)

        tutorSchedules = DatabaseManager.getAllTutorSchedules()
        Assert.assertEquals(tutorSchedules.size, 0)
    }

    // Test: should delete tutor schedule from database given values of schedule
    @Test
    fun deleteTutorSchedulesGivenValuesTest() {
        addTutor("new_tutor", "tu")

        addSchedule("new_tutor", "Thursday", "14:00:00", "15:30:00")
        var tutorSchedules = DatabaseManager.getAllTutorSchedules()

        Assert.assertEquals(tutorSchedules.size, 1)
        Assert.assertEquals(tutorSchedules[0].tutorId, "new_tutor")
        Assert.assertEquals(tutorSchedules[0].schedule.day, "Thursday")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftStart, "14:00:00")
        Assert.assertEquals(tutorSchedules[0].schedule.shiftEnd, "15:30:00")

        DatabaseManager.deleteTutorSchedule(
            "new_tutor",
            "Thursday",
            "14:00:00",
            "15:30:00"
        )

        tutorSchedules = DatabaseManager.getAllTutorSchedules()
        Assert.assertEquals(tutorSchedules.size, 0)
    }

    /**
     * Helper function to add student
     * @param id        String id of student
     * @param name      String name of student
     * @return void
     */
    private fun addStudent(id: String, name: String) {
        DatabaseManager.insertUser(StudentUser(id, name))
    }

    /**
     * Helper function to add tutor
     * @param id        String id of tutor
     * @param name      String name of tutor
     * @return void
     */
    private fun addTutor(id: String, name: String) {
        DatabaseManager.insertUser(TutorUser(id, name))
    }

    /**
     * Helper function to add admin
     * @param id        String id of admin
     * @param name      String name of admin
     * @return void
     */
    private fun addAdmin(id: String, name: String) {
        DatabaseManager.insertUser(AdminUser(id, name))
    }

    /**
     * Helper function to add tutor schedule
     * @param id        String id of tutor
     * @param name      String day of shift
     * @param start     String time shift starts
     * @param end       String time shift ends
     * @return void
     */
    private fun addSchedule(id: String, day: String, start: String, end: String) {
        DatabaseManager.insertTutorSchedule(id, Schedule(day, start, end))
    }
}