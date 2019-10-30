package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import org.junit.Test
import org.junit.Assert.*
import andr.mentorapp.ActivityCommonUtil.addTutor
import andr.mentorapp.ActivityCommonUtil.availableTutors
import andr.mentorapp.ActivityCommonUtil.checkedInTutors
import andr.mentorapp.ActivityCommonUtil.studentQueue
import andr.mentorapp.ActivityCommonUtil.tutorSessions
import andr.mentorapp.ActivityCommonUtil.finishSession
import andr.mentorapp.ActivityCommonUtil.firstAvailableTutor
import andr.mentorapp.ActivityCommonUtil.leaveQueue
import andr.mentorapp.ActivityCommonUtil.matchStudentTutor
import andr.mentorapp.ActivityCommonUtil.removeTutor
import org.junit.Before

/**
 * Class to test the ActivityCommonUtil
 *
 * @author Courtney Erbes
 * @date 10/25/19
 */
class ActivityCommonUtilTest {
    val id = "newtutor"
    val stuId = "newstudent"
    val name = "New"
    val tutor = TutorUser(id, name)
    val student = StudentUser(stuId, name)

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
        addTutor(tutor)
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
        addTutor(tutor)

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

        addTutor(tutor)
        matchStudentTutor(student)
        matchStudentTutor(student2)
        finishSession(tutor)

        assertEquals(tutorSessions.get(tutor), student2)
    }

    // Test: finish session and tutor doesn't match anyone
    @Test
    fun finishSessionEmptyQueue() {
        addTutor(tutor)
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

    // Test: check in tutor to system
    @Test
    fun testCheckInTutor() {
        addTutor(tutor)

        assert(checkedInTutors.contains(tutor))
        assert(availableTutors.contains(tutor))
    }

    // Test: check out tutor from system
    @Test
    fun testCheckOutTutor() {
        addTutor(tutor)
        removeTutor(tutor)

        assert(!checkedInTutors.contains(tutor))
        assert(!availableTutors.contains(tutor))
    }
}
