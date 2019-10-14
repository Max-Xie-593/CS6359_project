package andr.mentorapp

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class MainActivityTest {
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

    @Test
    fun testPopulateData() {
        populateData()
        Assert.assertEquals(STUDENT_LEVEL, getUserLevel("mugdha"))
        Assert.assertEquals("Mugdha", getUserName("mugdha"))
    }

    @Test
    fun testAddStudent() {
        val id = "newStudent"
        val name = "Abby"
        addStudent(id, name)
        Assert.assertEquals(STUDENT_LEVEL, getUserLevel(id))
        Assert.assertEquals(name, getUserName(id))
    }

    @Test
    fun testAddTutor() {
        val id = "newTutor"
        val name = "Abby"
        addTutor(id, name)
        Assert.assertEquals(TUTOR_LEVEL, getUserLevel(id))
        Assert.assertEquals(name, getUserName(id))
    }

    @Test
    fun testAddAdmin() {
        val id = "newAdmin"
        val name = "Abby"
        addAdmin(id, name)
        Assert.assertEquals(ADMIN_LEVEL, getUserLevel(id))
        Assert.assertEquals(name, getUserName(id))
    }

    @Test
    fun testGetUserLevel(){
        val id = "newStudent"
        userLevel.put(id, 5)
        Assert.assertEquals(5, getUserLevel(id))
    }

    @Test
    fun testGetUserName(){
        val id = "newStudent"
        userName.put(id, "Abby")
        Assert.assertEquals("Abby", getUserName(id))
    }
}
