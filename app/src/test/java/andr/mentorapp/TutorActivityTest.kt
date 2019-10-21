package andr.mentorapp

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class TutorActivityTest {
    private val tutorActivity = TutorActivity()

    @Test
    fun testCheckInTutor() {
        val id = "newStudent"
        tutorActivity.addTutor(id)
        assert(checkedInTutors.contains(id))
        tutorActivity.removeTutor(id)
    }

    @Test fun testCheckOutTutor() {
        val id = "newStudent"
        tutorActivity.addTutor(id)
        tutorActivity.removeTutor(id)
        assert(!checkedInTutors.contains(id))
    }
}