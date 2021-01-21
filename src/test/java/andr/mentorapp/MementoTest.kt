package andr.mentorapp

import andr.mentorapp.Database.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MementoTest {

    private lateinit var memento: Memento

    // Setup clean memento Object
    @Before
    fun setup() {
        memento = Memento()
    }

    @After
    fun cleanup() {
        memento.clear()
    }

    @Test
    fun saveStudentUserTest() {
        val testStudent = StudentUser("student", "Student")

        testSaveToMemento(testStudent)
    }

    @Test
    fun restoreStudentUserTest() {
        val testStudent = StudentUser("student", "Student")

        testRestoreFromMemento(testStudent)
    }

    @Test
    fun saveAdminUserTest() {
        val testAdmin = AdminUser("admin", "Admin")

        testSaveToMemento(testAdmin)
    }

    @Test
    fun restoreAdminUserTest() {
        val testAdmin = AdminUser("admin", "Admin")

        testRestoreFromMemento(testAdmin)
    }

    @Test
    fun saveTutorUserTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        testSaveToMemento(testTutor)
    }

    @Test
    fun restoreTutorUserTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        testRestoreFromMemento(testTutor)
    }

    @Test
    fun saveTutorSchedulesTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        val testSchedules: List<Schedule> = listOf(
            Schedule("Monday", "1:00", "2:15"),
            Schedule("Wednesday", "1:00", "2:15")
        )

        testSaveToMemento(testTutor, testSchedules = testSchedules)
    }

    @Test
    fun restoreTutorSchedulesTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        val testSchedules: List<Schedule> = listOf(
            Schedule("Monday", "1:00", "2:15"),
            Schedule("Wednesday", "1:00", "2:15")
        )

        testRestoreFromMemento(testTutor, testSchedules = testSchedules)
    }

    @Test
    fun saveTutorCoursesTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        val testCourses: List<Course> = listOf(
            Course("cs6359", "OOAD")
        )

        testSaveToMemento(testTutor, testCourses = testCourses)
    }

    @Test
    fun restoreTutorCoursesTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        val testCourses: List<Course> = listOf(
            Course("cs6359", "OOAD")
        )

        testRestoreFromMemento(testTutor, testCourses = testCourses)
    }

    @Test
    fun saveAllTutorInfoTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        val testSchedules: List<Schedule> = listOf(
            Schedule("Monday", "13:00", "14:15"),
            Schedule("Wednesday", "13:00", "14:15")
        )

        val testCourses: List<Course> = listOf(
            Course("cs6359", "OOAD")
        )

        testSaveToMemento(testTutor, testSchedules = testSchedules, testCourses = testCourses)
    }

    @Test
    fun restoreAllTutorInfoTest() {
        val testTutor = TutorUser("tutor", "Tutor")

        val testSchedules: List<Schedule> = listOf(
            Schedule("Monday", "13:00", "14:15"),
            Schedule("Wednesday", "13:00", "14:15")
        )

        val testCourses: List<Course> = listOf(
            Course("cs6359", "OOAD")
        )

        testRestoreFromMemento(testTutor, testSchedules = testSchedules, testCourses = testCourses)
    }

    private fun testSaveToMemento(testUser: User, testCourses: List<Course>? = null, testSchedules: List<Schedule>? = null) {
        memento.save(testUser, tutorSchedules = testSchedules, tutorCourses = testCourses)

        Assert.assertEquals(memento.savedUser!!.userId, testUser.userId)
        Assert.assertEquals(memento.savedUser!!.userName, testUser.userName)
        Assert.assertEquals(memento.savedUser!!.userLevel, testUser.userLevel)

        Assert.assertEquals(memento.savedTutorCourses, testCourses)

        Assert.assertEquals(memento.savedTutorSchedules, testSchedules)
    }

    private fun testRestoreFromMemento(testUser: User, testCourses: List<Course>? = null, testSchedules: List<Schedule>? = null) {
        testSaveToMemento(testUser, testSchedules = testSchedules, testCourses = testCourses)

        val (restoredUser, restoredCoures, restoredSchedules) = memento.restore()

        Assert.assertEquals(restoredUser.userId, testUser.userId)
        Assert.assertEquals(restoredCoures, testCourses)
        Assert.assertEquals(restoredSchedules, testSchedules)
    }

}