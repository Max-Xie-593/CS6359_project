package andr.mentorapp

import andr.mentorapp.Database.TutorUser
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

        assert(!GetHelpController.checkedInTutors.contains(tutor))
        assert(!GetHelpController.availableTutors.contains(tutor))
    }
}
