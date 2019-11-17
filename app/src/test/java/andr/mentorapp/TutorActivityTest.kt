package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.TutorUser
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Class to test the TutorActivity
 *
 * @author Courtney Erbes
 * @date 11/10/19
 */
@RunWith(RobolectricTestRunner::class)
class TutorActivityTest {
    var tutAct = TutorActivity()
    var tutor = TutorUser("id", "name")

    // Before test: setup database
    @Before
    fun setup() {
        GetHelpController.checkedInTutors.clear()
        GetHelpController.availableTutors.clear()
        GetHelpController.studentQueue.clear()
        GetHelpController.tutorSessions.clear()
        GetHelpController.availableExpertCourses.clear()

        MentorAppDatabase.TEST_MODE = true
        var context = ApplicationProvider.getApplicationContext<Context>()
        DatabaseManager.init(context)
    }

    // After test: close database instance
    @After
    fun after() {
        var context = ApplicationProvider.getApplicationContext<Context>()
        MentorAppDatabase.invoke(context).close()
    }

    // Test: check in tutor to system
    @Test
    fun testCheckInTutor() {
        tutAct.addTutor(tutor)

        assert(GetHelpController.checkedInTutors.contains(tutor))
        assert(GetHelpController.availableTutors.contains(tutor))
    }

    // Test: check out tutor from system
    @Test
    fun testCheckOutTutor() {
        tutAct.addTutor(tutor)
        assert(GetHelpController.checkedInTutors.contains(tutor))
        assert(GetHelpController.availableTutors.contains(tutor))

        tutAct.removeTutor(tutor)

        assert(!GetHelpController.availableTutors.contains(tutor))
        assert(!GetHelpController.checkedInTutors.contains(tutor))
    }
}
