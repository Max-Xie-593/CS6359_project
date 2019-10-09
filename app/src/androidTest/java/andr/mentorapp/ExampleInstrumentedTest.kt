package andr.mentorapp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("andr.mentorapp", appContext.packageName)
    }

    @Before
    fun init(){
        userLevel.clear()
        userName.clear()
    }

    @After
    fun finalize(){
        userLevel.clear()
        userName.clear()
    }

    @Test fun testPopulateData() {
        populateData()
        assertEquals(STUDENT_LEVEL, getUserLevel("mugdha"))
        assertEquals("Mugdha", getUserName("mugdha"))
    }

    @Test fun testAddStudent() {
        val id = "newStudent"
        val name = "Abby"
        addStudent(id, name)
        assertEquals(STUDENT_LEVEL, getUserLevel(id))
        assertEquals(name, getUserName(id))
    }

    @Test fun testAddTutor() {
        val id = "newTutor"
        val name = "Abby"
        addTutor(id, name)
        assertEquals(TUTOR_LEVEL, getUserLevel(id))
        assertEquals(name, getUserName(id))
    }

    @Test fun testAddAdmin() {
        val id = "newAdmin"
        val name = "Abby"
        addAdmin(id, name)
        assertEquals(ADMIN_LEVEL, getUserLevel(id))
        assertEquals(name, getUserName(id))
    }

    @Test fun testGetUserLevel(){
        val id = "newStudent"
        userLevel.put(id, 5)
        assertEquals(5, getUserLevel(id))
    }

    @Test fun testGetUserName(){
        val id = "newStudent"
        userName.put(id, "Abby")
        assertEquals("Abby", getUserName(id))
    }

}
