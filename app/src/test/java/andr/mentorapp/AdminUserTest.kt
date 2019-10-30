package andr.mentorapp

import andr.mentorapp.Database.ADMIN_LEVEL
import andr.mentorapp.Database.AdminUser
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Class to test the AdminUser
 *
 * @author Mugdha Gupta
 * @date 10/15/19
 */
@RunWith(RobolectricTestRunner::class)
class AdminUserTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = AdminUser("admin_test", "Admin User")

    // Test: constructor is successful
    @Test
    fun AdminUserConstructorTest(){
        assertNotNull(user.getIntent(context))
    }

    // Test: id set correctly
    @Test
    fun getAdminUserIdTest(){
        assertEquals("admin_test", user.userId)
    }

    // Test: userName set correctly
    @Test
    fun getAdminUserNameTest(){
        assertEquals("Admin User", user.userName)
    }

    // Test: user level set correctly
    @Test
    fun getAdminUserLevelTest(){
        assertEquals(ADMIN_LEVEL, user.userLevel)
    }
}
