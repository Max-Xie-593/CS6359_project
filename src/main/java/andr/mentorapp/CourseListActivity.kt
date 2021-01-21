package andr.mentorapp

import andr.mentorapp.GetHelpController.matchStudentTutor
import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.DatabaseManager.Companion.getUserById
import andr.mentorapp.Database.StudentUser
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course_list.*

/**
 * This class handles the page listing the courses in the system
 * in order to allow Student Users to select which Course they wish to be tutored in
 *
 *
 * @author Nymisha Jahagidar
 * @date 11/13/19
 */
class CourseListActivity : AppCompatActivity() {
    /**
     *  When the Activity is created, set title and display list of courses
     *
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        courseListMessage.setText("List of Courses:")

        this.displayList()
    }

    /**
     * Helper function to display list of courses to the User
     */
    fun displayList() {
        val studentUser = getUserById(intent.getStringExtra("studentId")) as StudentUser
        val courses = DatabaseManager.getAllCourses()

        //add a row containing a button for each Course
        for (course in courses) {
            val row = TableRow(this)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            val name = Button(this)

            row.layoutParams = params
            name.text = course.courseName
            name.setOnClickListener {
                createMatchForCourse(studentUser, course)
            }
            row.addView(name, 0)
            courseListTable.addView(row)
        }
    }

    /**
     * This is the function called when the button for a specific course is clicked
     *
     * Calls the matchStudentTutor function from GetHelpController to create a tutor session
     * and returns the sutdent back to the student home page
     *
     * @return void
     */
    fun createMatchForCourse(studentUser: StudentUser, course: Course){
        matchStudentTutor(studentUser, course)
        intent.setClass(this, StudentActivity::class.java)
        startActivity(intent)
    }

}
