package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import org.junit.Test
import org.junit.Assert.*
import andr.mentorapp.GetHelpController.availableTutors
import andr.mentorapp.GetHelpController.checkedInTutors
import andr.mentorapp.GetHelpController.studentQueue
import andr.mentorapp.GetHelpController.tutorSessions
import andr.mentorapp.GetHelpController.finishSession
import andr.mentorapp.GetHelpController.firstAvailableTutor
import andr.mentorapp.GetHelpController.leaveQueue
import andr.mentorapp.GetHelpController.matchStudentTutor
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
    val name = "New"
    val tutor = TutorUser(id, name)
    val student = StudentUser(stuId, name)
    var tutAct = TutorActivity()

    // Before each test, make sure queues, hashset and hashmap are empty
    @Before
    fun init() {
        checkedInTutors.clear()
        availableTutors.clear()
        studentQueue.clear()
        tutorSessions.clear()
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

    // Test: student and tutor not matched when none available
    @Test
    fun matchStudentTutorFalse() {
        assert(!matchStudentTutor(student))
        assert(studentQueue.contains(student))
    }

    // Test: student and tutor matched when tutor available
    @Test
    fun matchStudentTutorTrue() {
        tutAct.addTutor(tutor)

        assert(matchStudentTutor(student))
        assertEquals(tutorSessions.get(tutor), student)
        assert(checkedInTutors.contains(tutor))
        assert(!availableTutors.contains(tutor))
    }

    // Test: finish session and match tutor with waiting student
    @Test
    fun finishSessionStudentInQueue() {
        val newId = "stu2"
        val student2 = StudentUser(newId, name)

        tutAct.addTutor(tutor)
        matchStudentTutor(student)
        matchStudentTutor(student2)
        finishSession(tutor)

        assertEquals(tutorSessions.get(tutor), student2)
    }

    // Test: finish session and tutor doesn't match anyone
    @Test
    fun finishSessionEmptyQueue() {
        tutAct.addTutor(tutor)
        matchStudentTutor(student)
        finishSession(tutor)

        assertNull(tutorSessions.get(tutor))
        assert(!tutorSessions.contains(tutor))
        assert(availableTutors.contains(tutor))
    }

    // Test: student doesn't leave queue since not in queue
    @Test
    fun leaveQueueFalse() {
        assert(!leaveQueue(student))
    }

    // Test: student leaves queue successfully
    @Test
    fun leaveQueueTrue() {
        matchStudentTutor(student)

        assert(leaveQueue(student))
        assert(!studentQueue.contains(student))
    }
}
