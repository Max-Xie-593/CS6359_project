package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.Schedule
import andr.mentorapp.Database.TutorScheduleDao
import andr.mentorapp.Database.TutorUser
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * This class tests the database queries associated with the TutorScheduleDao
 *
 * @author Courtney Erbes
 * @date 10/15/19
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

    // Test: should return Tutor Schedule matching the given values
    @Test
    fun getTutorScheduleGivenValuesTest() {
        var id = "new_tutor"
        var day = "Monday"
        var start = "10:00:00"
        var end = "11:30:00"
        db!!.userDao().insert(TutorUser(id, "tu"))

        var newSchedule = TutorSchedule(id, Schedule(day, start, end))
        tutorScheduleDao.insert(newSchedule)
        var schedule = tutorScheduleDao.getSchedule(
            id,
            day,
            start,
            end
        )

        assertEquals(schedule.tutorId, id)
        assertEquals(schedule.schedule.day, day)
        assertEquals(schedule.schedule.shiftStart, start)
        assertEquals(schedule.schedule.shiftEnd, end)
    }

    // Test: should return list of all TutorSchedules in database for Tutor with id 'tutorId'
    @Test
    fun findTutorSchedulesByIdTest() {
        db!!.userDao().insert(TutorUser("new_tutor", "tu"))

        var newSchedule = TutorSchedule("new_tutor", Schedule("Monday", "10:00:00", "11:30:00"))
        tutorScheduleDao.insert(newSchedule)
        var list = tutorScheduleDao.findTutorSchedulesByIdFromdDB("new_tutor")

        assertEquals(list.size, 1)
        assertEquals(list[0].day, "Monday")
        assertEquals(list[0].shiftStart, "10:00:00")
        assertEquals(list[0].shiftEnd, "11:30:00")
    }

    // Test: should insert new TutorSchedule into database
    @Test
    fun insertScheduleTest() {
        db!!.userDao().insert(TutorUser("new_tutor", "tu"))

        var newSchedule1 = TutorSchedule("new_tutor", Schedule("Monday", "10:00:00", "11:30:00"))
        var newSchedule2 = TutorSchedule("new_tutor", Schedule("Tuesday", "12:00:00", "13:30:00"))
        tutorScheduleDao.insert(newSchedule1)
        tutorScheduleDao.insert(newSchedule2)
        var list = tutorScheduleDao.findTutorSchedulesByIdFromdDB("new_tutor")

        assertEquals(list.size, 2)
        assertEquals(list[0].day, "Monday")
        assertEquals(list[0].shiftStart, "10:00:00")
        assertEquals(list[0].shiftEnd, "11:30:00")
        assertEquals(list[1].day, "Tuesday")
        assertEquals(list[1].shiftStart, "12:00:00")
        assertEquals(list[1].shiftEnd, "13:30:00")
    }

    // Test: delete schedule from db
    @Test
    fun deleteScheduleTest() {
        var id = "new_tutor"
        var day = "Monday"
        var start = "10:00:00"
        var end = "11:30:00"
        db!!.userDao().insert(TutorUser(id, "tu"))

        var newSchedule = TutorSchedule(id, Schedule(day, start, end))
        tutorScheduleDao.insert(newSchedule)

        var tutorSchedules = tutorScheduleDao.getAll()
        assertEquals(tutorSchedules.size, 1)

        tutorScheduleDao.delete(newSchedule)

        tutorSchedules = tutorScheduleDao.getAll()
        assertEquals(tutorSchedules.size, 0)
    }
}
