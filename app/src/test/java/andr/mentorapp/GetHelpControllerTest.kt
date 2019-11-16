package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import andr.mentorapp.GetHelpController.availableExpertCourses
import org.junit.Test
import org.junit.Assert.*
import andr.mentorapp.GetHelpController.availableTutors
import andr.mentorapp.GetHelpController.checkedInTutors
import andr.mentorapp.GetHelpController.studentQueue
import andr.mentorapp.GetHelpController.tutorSessions
import andr.mentorapp.GetHelpController.finishSession
import andr.mentorapp.GetHelpController.firstAvailableTutor
import andr.mentorapp.GetHelpController.firstAvailableTutorForCourse
import andr.mentorapp.GetHelpController.leaveQueue
import andr.mentorapp.GetHelpController.matchStudentTutor
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Class to test the GetHelpController
 *
 * @author Courtney Erbes
 * @date 10/25/19
 */
@RunWith(RobolectricTestRunner::class)
class GetHelpControllerTest {
    val id = "newtutor"
    val stuId = "newstudent"
    val courseId = "cs100"
    val courseName = "Computer Science I"
    val name = "New"
    val tutor = TutorUser(id, name)
    val student = StudentUser(stuId, name)
    val course = Course(courseId, name)
    var tutAct = TutorActivity()

    // Before each test, make sure queues, hashset and hashmap are empty
    @Before
    fun init() {
        checkedInTutors.clear()
        availableTutors.clear()
        studentQueue.clear()
        tutorSessions.clear()
        availableExpertCourses.clear()
        MentorAppDatabase.TEST_MODE = true
        var context = ApplicationProvider.getApplicationContext<Context>()
        DatabaseManager.init(context)
    }

    @After
    fun after() {
        var context = ApplicationProvider.getApplicationContext<Context>()
        MentorAppDatabase.invoke(context).close()
    }

    // Test: if no available tutors, return null
    @Test
    fun firstAvailableTutorNull() {
        assertNull(firstAvailableTutor())
    }

    // Test: return tutor if tutor  available
    @Test
    fun firstAvailableTutorNotNull() {
        tutAct.addTutor(tutor)
        assertEquals(firstAvailableTutor(), tutor)

        assert(checkedInTutors.contains(tutor))
        assert(!availableTutors.contains(tutor))
    }

    // Test: if no available tutors for course, return null
    @Test
    fun firstAvailableTutorForCourseNull() {
        assertNull(firstAvailableTutorForCourse(course))
    }

    // Test: return tutor if tutor available for course
    @Test
    fun firstAvailableTutorForCourseNotNull() {

        val tutors = HashSet<String>()
        tutors.add(tutor.userId)
        availableExpertCourses.put(courseId, tutors)
        tutAct.addTutor(tutor)
        assertEquals(firstAvailableTutorForCourse(course), tutor)

        assert(checkedInTutors.contains(tutor))
        assert(!availableTutors.contains(tutor))
    }

    // Test: student and tutor not matched when none available
    @Test
    fun matchStudentTutorFalse() {
        assert(!matchStudentTutor(student, course))
        assert(studentQueue.contains(Pair(student,course)))
    }

    // Test: student and tutor matched when tutor available
    @Test
    fun matchStudentTutorTrue() {
        tutAct.addTutor(tutor)

        assert(matchStudentTutor(student, course))
        assert(tutorSessions.contains(Triple(tutor,student, course)))
        assert(checkedInTutors.contains(tutor))
        assert(!availableTutors.contains(tutor))
    }

    // Test: finish session and match tutor with waiting student
    @Test
    fun finishSessionStudentInQueue() {
        val newId = "stu2"
        val student2 = StudentUser(newId, name)

        tutAct.addTutor(tutor)
        matchStudentTutor(student, course)
        matchStudentTutor(student2, course)
        finishSession(tutor)

        assert(tutorSessions.contains(Triple(tutor,student2, course)))
    }

    // Test: finish session and tutor doesn't match anyone
    @Test
    fun finishSessionEmptyQueue() {
        tutAct.addTutor(tutor)
        matchStudentTutor(student, course)
        finishSession(tutor)
        assert(!tutorSessions.contains(Triple(tutor,student, course)))
        assert(availableTutors.contains(tutor))
    }

    // Test: tutor finished a session, but queue is empty so no need to update
    @Test
    fun updateQueueEmpty() {
        tutAct.addTutor(tutor)
        matchStudentTutor(student, course)
        finishSession(tutor)
        assert(!tutorSessions.contains(Triple(tutor,student, course)))
        assert(availableTutors.contains(tutor))
    }

    // Test: tutor finished a session, tutor gets matched with a student off the queue
    @Test
    fun updateQueueNotEmpty() {
        val newId = "stu2"
        val student2 = StudentUser(newId, name)
        tutAct.addTutor(tutor)
        matchStudentTutor(student, course)
        matchStudentTutor(student2, course)

        assert(studentQueue.contains(Pair(student2,course)))
        finishSession(tutor)
        assert(tutorSessions.contains(Triple(tutor,student2, course)))
        assert(!availableTutors.contains(tutor))
    }

    // Test: student doesn't leave queue since not in queue
    @Test
    fun leaveQueueFalse() {
        assert(!leaveQueue(student))
    }

    // Test: student leaves queue successfully
    @Test
    fun leaveQueueTrue() {
        matchStudentTutor(student, course)

        assert(leaveQueue(student))
        assert(!studentQueue.contains(Pair(student,course)))
    }
}
