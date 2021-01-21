package andr.mentorapp

import andr.mentorapp.Database.TUTOR_LEVEL
import andr.mentorapp.Database.TutorUser
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Class to test TutorUser
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
@RunWith(RobolectricTestRunner::class)
class TutorUserTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = TutorUser("tutor_test", "Tutor User")

    // Test: constructor successful
    @Test
    fun TutorUserConstructorTest(){
        assertNotNull(user.getIntent(context))
    }

    // Test: id set successfully
    @Test
    fun getTutorUserIdTest(){
        assertEquals("tutor_test", user.userId)
    }

    // Test: user name set successfully
    @Test
    fun getTutorUserNameTest(){
        assertEquals("Tutor User", user.userName)
    }

    // Test: user level set successfully
    @Test
    fun getTutorUserLevelTest(){
        assertEquals(TUTOR_LEVEL, user.userLevel)
    }
}