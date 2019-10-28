package andr.mentorapp

import andr.mentorapp.Database.STUDENT_LEVEL
import andr.mentorapp.Database.StudentUser
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StudentUserTest {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val user = StudentUser("student_test", "Student User")

    @Test
    fun StudentUserConstructorTest(){
        assertNotNull(user.getIntent(context))
    }

    @Test
    fun getStudentUserIdTest(){
        assertEquals("student_test", user.userId)
    }

    @Test
    fun getStudentUserNameTest(){
        assertEquals("Student User", user.userName)
    }

    @Test
    fun getStudentUserLevelTest(){
        assertEquals(STUDENT_LEVEL, user.userLevel)
    }
}