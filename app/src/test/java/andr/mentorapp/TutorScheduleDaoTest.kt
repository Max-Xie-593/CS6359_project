package andr.mentorapp

import andr.mentorapp.Database.TutorScheduleDao
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/*
 * This class tests the database queries associated with the TutorScheduleDao
 */
@RunWith(RobolectricTestRunner::class)
class TutorScheduleDaoTest {
    private lateinit var tutorScheduleDao : TutorScheduleDao
    private var db : MentorAppDatabase? = null

    // Before test: setup database
    @Before
    fun setup() {
        MentorAppDatabase.TEST_MODE = true
        var context = ApplicationProvider.getApplicationContext<Context>()
        db = MentorAppDatabase.invoke(context)
        tutorScheduleDao = db!!.tutorScheduleDao()
    }

    // After test: close database instance
    @After
    fun after() {
        db!!.close()
    }


    // Test: should return all Tutor Schedules in the database
    @Test
    fun getAllTutorSchedulesTest() {
        var tutorSchedules = tutorScheduleDao.getAll()
        assertEquals(tutorSchedules.size, 0)
    }

    // Test: should return list of all TutorSchedules in database for Tutor with id 'tutorId'
    @Test
    fun findTutorSchedulesByIdTest() {
        var newSchedule = TutorSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        tutorScheduleDao.insert(newSchedule)
        var list = tutorScheduleDao.findTutorSchedulesByIdFromdDB("new_tutor")

        assertEquals(list.size, 1)
        assertEquals(list[0].tutorId, "new_tutor")
        assertEquals(list[0].day, "Monday")
        assertEquals(list[0].shiftStart, "10:00:00")
        assertEquals(list[0].shiftEnd, "11:30:00")
    }

    // Test: should insert new TutorSchedule into database
    @Test
    fun insertScheduleTest() {
        var newSchedule1 = TutorSchedule("new_tutor", "Monday", "10:00:00", "11:30:00")
        var newSchedule2 = TutorSchedule("new_tutor", "Tuesday", "12:00:00", "13:30:00")
        tutorScheduleDao.insert(newSchedule1)
        tutorScheduleDao.insert(newSchedule2)
        var list = tutorScheduleDao.findTutorSchedulesByIdFromdDB("new_tutor")

        assertEquals(list.size, 2)
        assertEquals(list[0].tutorId, "new_tutor")
        assertEquals(list[0].day, "Monday")
        assertEquals(list[0].shiftStart, "10:00:00")
        assertEquals(list[0].shiftEnd, "11:30:00")
        assertEquals(list[1].tutorId, "new_tutor")
        assertEquals(list[1].day, "Tuesday")
        assertEquals(list[1].shiftStart, "12:00:00")
        assertEquals(list[1].shiftEnd, "13:30:00")
    }
}