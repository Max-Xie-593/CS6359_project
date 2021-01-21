package andr.mentorapp

import andr.mentorapp.Database.Schedule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Class to test the TutorSchedule model
 *
 * @author Courtney Erbes
 * @date 10/13/19
 */
@RunWith(RobolectricTestRunner::class)
class TutorScheduleTest {
    val ts = TutorSchedule("tutor_test", Schedule("Monday", "13:00:00", "14:30:00"))

    // Test: constructor of TutorSchedule
    @Test
    fun tutorScheduleConstructorTest(){
        assertNotNull(ts.tutorId)
        assertNotNull(ts.schedule.day)
        assertNotNull(ts.schedule.shiftStart)
        assertNotNull(ts.schedule.shiftEnd)
    }

    // Test: retrieval of tutor Id corresponding with tutor schedule
    @Test
    fun getScheduleTutorIdTest(){
        assertEquals("tutor_test", ts.tutorId)
    }

    // Test: retrieval of tutor schedule day
    @Test
    fun getScheduleDayTest(){
        assertEquals("Monday", ts.schedule.day)
    }

    // Test: retrieval of tutor schedule start time
    @Test
    fun getScheduleStartTest(){
        assertEquals("13:00:00", ts.schedule.shiftStart)
    }

    // Test: retrieval of tutor schedule end time
    @Test
    fun getScheduleEndTest(){
        assertEquals("14:30:00", ts.schedule.shiftEnd)
    }
}
