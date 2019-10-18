package andr.mentorapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UserTest{
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = User("test", "User", 1)

    @Test
    fun UserConstructorTest(){
        val user2 = User("constructorTest", "Construct", 1)
        assertNull(user.getIntent(context))
    }

    @Test
    fun getUserIdTest(){
        assertEquals("test", user.userId)
    }

    @Test
    fun getUserNameTest(){
        assertEquals("User", user.userName)
    }

    @Test
    fun getuserLevelTest(){
        assertEquals(1, user.userLevel)
    }
}