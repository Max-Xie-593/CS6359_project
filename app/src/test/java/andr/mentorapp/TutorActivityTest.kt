package andr.mentorapp

import org.junit.Test

/**
 * Class to test the TutorActivity
 */

class TutorActivityTest {
    private val tutorActivity = TutorActivity()

    // Test: check in tutor to system
    @Test
    fun testCheckInTutor() {
        val id = "newStudent"
        tutorActivity.addTutor(id)
        assert(checkedInTutors.contains(id))
        tutorActivity.removeTutor(id)
    }

    // Test: check out tutor from system
    @Test
    fun testCheckOutTutor() {
        val id = "newStudent"
        tutorActivity.addTutor(id)
        tutorActivity.removeTutor(id)
        assert(!checkedInTutors.contains(id))
    }
}