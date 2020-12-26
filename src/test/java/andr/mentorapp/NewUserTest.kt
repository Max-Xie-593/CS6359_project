package andr.mentorapp

import andr.mentorapp.Database.NewUser
import andr.mentorapp.Database.USER_DOES_NOT_EXIST
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Class to test the NewUser
 *
 * @author Courtney Erbes
 * @date 10/28/19
 */
@RunWith(RobolectricTestRunner::class)
class NewUserTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = NewUser("new_test")

    // Test: successful constructor
    @Test
    fun NewUserConstructorTest(){
        Assert.assertNotNull(user.getIntent(context))
    }

    // Test: id set correctly
    @Test
    fun getNewUserIdTest(){
        Assert.assertEquals("new_test", user.userId)
    }

    // Test: user name set correctly
    @Test
    fun getNewUserNameTest(){
        Assert.assertEquals("", user.userName)
    }

    // Test: user level set correctly
    @Test
    fun getNewUserLevelTest(){
        Assert.assertEquals(USER_DOES_NOT_EXIST, user.userLevel)
    }
}
