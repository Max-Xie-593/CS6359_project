package andr.mentorapp

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
    val schedule = TutorSchedule("tutor_test", "Monday", "13:00:00", "14:30:00")

    // Test: constructor of TutorSchedule
    @Test
    fun tutorScheduleConstructorTest(){
        assertNotNull(schedule.tutorId)
        assertNotNull(schedule.day)
        assertNotNull(schedule.shiftStart)
        assertNotNull(schedule.shiftEnd)
    }

    // Test: retrieval of tutor Id corresponding with tutor schedule
    @Test
    fun getScheduleTutorIdTest(){
        assertEquals("tutor_test", schedule.tutorId)
    }

    // Test: retrieval of tutor schedule day
    @Test
    fun getScheduleDayTest(){
        assertEquals("Monday", schedule.day)
    }

    // Test: retrieval of tutor schedule start time
    @Test
    fun getScheduleStartTest(){
        assertEquals("13:00:00", schedule.shiftStart)
    }

    // Test: retrieval of tutor schedule end time
    @Test
    fun getScheduleEndTest(){
        assertEquals("14:30:00", schedule.shiftEnd)
    }
}
