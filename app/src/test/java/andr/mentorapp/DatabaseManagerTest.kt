package andr.mentorapp

import andr.mentorapp.Database.*
import android.content.Context
import androidx.room.Database
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
        Assert.assertEquals(DatabaseManager.getUserById("stu").userId, "stu")

        DatabaseManager.deleteUser(student)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 0)
    }

    // Test: restore successfully restores only the last non-tutor user from db
    @Test
    fun restoreOnlyLastNonTutorTest() {
        val student1 = StudentUser("stu1", "stu1")
        DatabaseManager.insertUser(student1)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 1)

        val student2 = StudentUser("stu2", "stu2")
        DatabaseManager.insertUser(student2)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 2)

        DatabaseManager.deleteUser(student1)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 1)

        DatabaseManager.deleteUser(student2)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 0)

        DatabaseManager.restoreLastUser()
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 1)
        Assert.assertEquals(DatabaseManager.getUserById("stu2").userId, student2.userId)
    }

    // Test: restore successfully restores only the last non-tutor user from db
    @Test
    fun restoreOnlyLastTutorTest() {
        val tutor1 = TutorUser("tut1", "tut1")
        val tutor1Courses : List<Course> = listOf(
            Course(courseId = "cs6359", courseName = "OOAD"),
            Course(courseId = "cs6363", courseName = "Algs")
        )
        val tutor1Schedules: List<Schedule> = listOf(
            Schedule("Monday", "13:00", "14:15"),
            Schedule("Wednesday", "13:00", "14:15")
        )

        DatabaseManager.insertUser(tutor1)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 1)
        for (course in tutor1Courses) {
            addCourse(course.courseId, course.courseName)
            addTutorCourseJoin(tutor1.userId, course.courseId)
        }
        Assert.assertEquals(DatabaseManager.getCoursesByTutorId(tutor1.userId).size, 2)

        for (schedule in tutor1Schedules) {
            addSchedule(id = tutor1.userId, day = schedule.day, start = schedule.shiftStart, end = schedule.shiftEnd)
        }
        Assert.assertEquals(DatabaseManager.getSchedulesByTutorId(tutor1.userId).size, 2)


        val tutor2 = TutorUser("tut2", "tut2")
        val tutor2Courses : List<Course> = listOf(
            Course(courseId = "cs6359", courseName = "OOAD"),
            Course(courseId = "cs6363", courseName = "Algs")
        )
        val tutor2Schedules: List<Schedule> = listOf(
            Schedule("Monday", "13:00", "14:15"),
            Schedule("Wednesday", "13:00", "14:15")
        )

        DatabaseManager.insertUser(tutor2)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 2)
        for (course in tutor2Courses) {
            addCourse(course.courseId, course.courseName)
            addTutorCourseJoin(tutor2.userId, course.courseId)
        }
        Assert.assertEquals(DatabaseManager.getCoursesByTutorId(tutor2.userId).size, 2)
        for (schedule in tutor2Schedules) {
            addSchedule(id = tutor2.userId, day = schedule.day, start = schedule.shiftStart, end = schedule.shiftEnd)
        }
        Assert.assertEquals(DatabaseManager.getSchedulesByTutorId(tutor2.userId).size, 2)


        DatabaseManager.deleteUser(tutor1)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 1)
        Assert.assertEquals(DatabaseManager.getCoursesByTutorId(tutor1.userId).size, 0)
        Assert.assertEquals(DatabaseManager.getSchedulesByTutorId(tutor1.userId).size, 0)

        DatabaseManager.deleteUser(tutor2)
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 0)
        Assert.assertEquals(DatabaseManager.getCoursesByTutorId(tutor2.userId).size, 0)
        Assert.assertEquals(DatabaseManager.getSchedulesByTutorId(tutor2.userId).size, 0)

        DatabaseManager.restoreLastUser()
        Assert.assertEquals(DatabaseManager.getAllUsers().size, 1)
        Assert.assertEquals(DatabaseManager.getUserById("tut2").userId, tutor2.userId)
        Assert.assertEquals(DatabaseManager.getUserById("tut2").userLevel, TUTOR_LEVEL)
        Assert.assertEquals(DatabaseManager.getCoursesByTutorId(tutor2.userId).size, 2)
        Assert.assertEquals(DatabaseManager.getSchedulesByTutorId(tutor2.userId).size, 2)
    }

    // Test: update user in db successfully
    @Test
    fun updateTest() {
        var student = StudentUser("stu", "stu2")
        DatabaseManager.insertUser(student)
        Assert.assertEquals(DatabaseManager.getUserById("stu").userName, "stu2")

        DatabaseManager.updateUser("stu", "newName")
        Assert.assertEquals(DatabaseManager.getUserById("stu").userName, "newName")
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

    // Test: inserts 0 courses and checks if all of them exists
    @Test
    fun insertCourseTest0() {
        val courses = DatabaseManager.getAllCourses()
        Assert.assertEquals(courses.size,0)
    }

    // Test: inserts 1 courses and checks if all of them exists
    @Test
    fun insertCourseTest1() {
        addCourse("cs6359","OOAD")
        val courses = DatabaseManager.getAllCourses()
        Assert.assertEquals(courses.size,1)
        Assert.assertEquals(courses[0].courseId,"cs6359")
        Assert.assertEquals(courses[0].courseName,"OOAD")
    }

    // Test: inserts 3 courses and checks if all of them exists
    @Test
    fun insertCourseTest3() {
        addCourse("cs6359","OOAD")
        addCourse("cs6375","ML")
        addCourse("cs6363","Algs")
        val courses = DatabaseManager.getAllCourses()
        Assert.assertEquals(courses.size,3)
        Assert.assertEquals(courses[0].courseId,"cs6359")
        Assert.assertEquals(courses[0].courseName,"OOAD")
        Assert.assertEquals(courses[1].courseId,"cs6375")
        Assert.assertEquals(courses[1].courseName,"ML")
        Assert.assertEquals(courses[2].courseId,"cs6363")
        Assert.assertEquals(courses[2].courseName,"Algs")
    }

    // Test: tests the deletecourse function
    @Test
    fun deleteCourseTest() {
        val course = Course("cs6359","OOAD")
        DatabaseManager.insertCourse(course)
        Assert.assertEquals(DatabaseManager.getCoursebyId("cs6359").courseId,"cs6359")

        DatabaseManager.deleteCourse(course)
        Assert.assertEquals(DatabaseManager.getAllCourses().size,0)
    }

    // Test: inserts nothing into the TutorCourseJoin Table and checks if there is nothing
    @Test
    fun insertTutorCourseJoinTest0() {
        val tutorCourseTeach = DatabaseManager.getAllTutorCourseJoins()
        Assert.assertEquals(tutorCourseTeach.size,0)
    }

    // Test: inserts 1 tutorCourseJoin entry and checks if all of them exist
    @Test
    fun insertTutorCourseJoinTest1() {
        addTutor("tu","tu")
        addCourse("cs6359","OOAD")
        addTutorCourseJoin("tu","cs6359")

        val tutorCourseTeach = DatabaseManager.getAllTutorCourseJoins()
        Assert.assertEquals(tutorCourseTeach.size,1)
        Assert.assertEquals(tutorCourseTeach[0].tutorId,"tu")
        Assert.assertEquals(tutorCourseTeach[0].courseId,"cs6359")
    }

    // Test: inserts 3 tutorCourseJoin entries and checks if all of them exist
    @Test
    fun insertTutorCourseJoinTest3() {
        addTutor("tu","tu")
        addCourse("cs6359","OOAD")
        addCourse("cs6375","ML")
        addCourse("cs6363","Algs")
        addTutorCourseJoin("tu","cs6359")
        addTutorCourseJoin("tu","cs6375")
        addTutorCourseJoin("tu","cs6363")
        val tutorCourseTeach = DatabaseManager.getAllTutorCourseJoins()
        Assert.assertEquals(tutorCourseTeach.size,3)
        Assert.assertEquals(tutorCourseTeach[0].tutorId,"tu")
        Assert.assertEquals(tutorCourseTeach[0].courseId,"cs6359")
        Assert.assertEquals(tutorCourseTeach[1].tutorId,"tu")
        Assert.assertEquals(tutorCourseTeach[1].courseId,"cs6375")
        Assert.assertEquals(tutorCourseTeach[2].tutorId,"tu")
        Assert.assertEquals(tutorCourseTeach[2].courseId,"cs6363")
    }

    // Tests the delete function of the TutorCourseJoin table
    @Test
    fun deleteTutorCourseJoin() {
        addTutor("tu","tu")
        addCourse("cs6359","OOAD")
        val tutorCourseJoinHold = TutorCourseJoin("tu","cs6359")

        DatabaseManager.insertTutorCourseJoin("tu", "cs6359")
        Assert.assertEquals(DatabaseManager.getCoursefromTutorCourseJoin("tu","cs6359").tutorId,"tu")
        Assert.assertEquals(DatabaseManager.getCoursefromTutorCourseJoin("tu","cs6359").courseId,"cs6359")

        DatabaseManager.deleteTutorCourseJoin(tutorCourseJoinHold)
        Assert.assertEquals(DatabaseManager.getAllTutorCourseJoins().size,0)
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

    /**
     * Helper function to add Course
     * @param id        String id of course
     * @param name      String name of Course
     */
    private fun addCourse(id: String, name: String) {
        DatabaseManager.insertCourse(Course(id,name))
    }

    /**
     * Helper function to add TutorCourseJoin
     * @param tutorId   String id of tutor
     * @param courseId  String id of course
     */
    private fun addTutorCourseJoin(tutorId: String,courseId:String) {
        DatabaseManager.insertTutorCourseJoin(tutorId, courseId)
    }
}
