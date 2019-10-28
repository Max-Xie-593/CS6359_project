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

@RunWith(RobolectricTestRunner::class)
class AdminUserTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = AdminUser("admin_test", "Admin User")

    @Test
    fun AdminUserConstructorTest(){
        assertNotNull(user.getIntent(context))
    }

    @Test
    fun getAdminUserIdTest(){
        assertEquals("admin_test", user.userId)
    }

    @Test
    fun getAdminUserNameTest(){
        assertEquals("Admin User", user.userName)
    }

    @Test
    fun getAdminUserLevelTest(){
        assertEquals(ADMIN_LEVEL, user.userLevel)
    }
}