package andr.mentorapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * This class tests the database queries associated with the User
 *
 * @author Courtney Erbes
 * @date 10/27/19
 */
@RunWith(RobolectricTestRunner::class)
class UserTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = User("test", "User", 1)

    // Test: constructor success
    @Test
    fun UserConstructorTest(){
        assertNull(user.getIntent(context))
    }

    // Test: id set successfully
    @Test
    fun getUserIdTest(){
        assertEquals("test", user.userId)
    }

    // Test: user name set successfully
    @Test
    fun getUserNameTest(){
        assertEquals("User", user.userName)
    }

    // Test: user level set successfully
    @Test
    fun getUserLevelTest(){
        assertEquals(1, user.userLevel)
    }
}
