package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_course.*

/**
 *  Add Course Page
 *
 *  This class creates the add course screen to allow the admin to add a new course to the database
 *
 *  @author Max Xie
 *  @date 11/09/19
 */
class AddCourseActivity : AppCompatActivity() {

    /**
     *  creates the add course page
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course) // set the layout to the particular xml file


        this.displayCourseList()
    }

    /**
     *  displays the list of courses from the Course Table
     */
    protected fun displayCourseList() {

        val intent = getIntent() // retrieve the data from the preceding request
        val newIntent: Intent = Intent() // create new request

        val context = this
        val courses = DatabaseManager.getAllCourses() // retrieves all the courses from the Course Table
        val expertCourses = DatabaseManager.getCoursesByTutorId(intent.getStringExtra("tutorId"))
        val displayCourses = arrayListOf<Course>()
        var dontadd = false
        for (course in courses)
        {
            for (expertCourse in expertCourses){
                if (expertCourse.courseId == course.courseId){
                    dontadd = true
                    break
                }
            }
            if (!dontadd) {
                displayCourses.add(course)
            }
            dontadd = false

        }

        for (course in displayCourses) {
            val row = TableRow(context)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT) // set the params for the row
            val courseName = TextView(context)

            row.layoutParams = params
            courseName.text = course.courseName // get the name of the course
            row.addView(courseName,0)

            val addCourseButton = Button(context) // create the button to add the course to courses tutor can teach
            addCourseButton.text = "ADD"
            addCourseButton.setOnClickListener {
                newIntent.putExtra("tutorId",intent.getStringExtra("tutorId")) // add data from preceding request
                newIntent.putExtra("courseId",course.courseId) // add data entered in this screen
                setResult(Activity.RESULT_OK,newIntent) // set result
                finish() // exit the screen
            }
            row.addView(addCourseButton,1)
            courseList.addView(row)
        }
    }
}