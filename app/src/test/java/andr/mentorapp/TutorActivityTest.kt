package andr.mentorapp

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class TutorActivityTest {
    private val tutorActivity = TutorActivity()

    @Before
    fun init(){
        userLevel.clear()
        userName.clear()
    }

    @After
    fun finalize(){
        userLevel.clear()
        userName.clear()
    }

    @Test
    fun testCheckInTutor() {
        val id = "newStudent"
        tutorActivity.checkInTutor(id)
        assert(checkedInTutors.contains(id))
    }

    @Test fun testCheckOutTutor() {
        val id = "newStudent"
        tutorActivity.checkOutTutor(id)
        assert(!checkedInTutors.contains(id))
    }
}