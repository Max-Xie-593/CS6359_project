package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_courses.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val GET_NEW_COURSE_RESULT : Int = 3

/**
 *  Courses List Screen
 *
 *  creates the list of courses the tutor can teach that the admin can modify as needed
 *
 *  @author Max Xie
 *  @date 11/09/19
 */
class EditCourseActivity : AppCompatActivity() {

    /**
     * creates the courses list screen
     * @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_courses) // set the layout to the particular xml file

        listMessageCourse.text = "COURSES LIST" // set the text of the screen

        // set the add button to allow admin to add courses to the tutor
        add_course_button.setOnClickListener {
            val newIntent: Intent = Intent()
            newIntent.setClass(this,AddCourseActivity::class.java)
            newIntent.putExtra("tutorId",intent.getStringExtra("tutorId"))
            startActivityForResult(newIntent,GET_NEW_COURSE_RESULT)
        }
        this.displayCourseList()
    }

    /**
     *  code to create the list of courses the tutor can teach
     */
    private fun displayCourseList() {
        val context = this

        // grab all the courses the tutor is able to teach from the database
        val courses =
            DatabaseManager.getCoursesByTutorId(intent.getStringExtra("tutorId"))

        // add a row containing a delete button for each course in the list
        for (course in courses) {
            val row = TableRow(context)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT) // set the params for the row
            val courseName = TextView(context)

            row.layoutParams = params
            courseName.text = course.courseName // grab the name of the course
            row.addView(courseName,0)

            //TODO: fix everything to use TutorCourseJoin instead of TutorCourses
//            val deleteCourseButton = Button(context) // create the delete button to remove the course if needed
//            deleteCourseButton.text = "DELETE"
//            deleteCourseButton.setOnClickListener {
//                DatabaseManager.deleteCourse(courseName) // delete the course from the database
//                recreate() // update the screen
//            }
//            row.addView(deleteCourseButton,1)
            courseListTable.addView(row)
        }
    }

    /**
     *  override the method to update the screen after certain actions
     */
    override fun recreate() {
        courseListTable.removeAllViews()
        this.displayCourseList()
    }

    /**
     *  function that is called from any results from the function [startActivityForResult] in this class
     *  @param requestCode code from the param in initialization of [startActivityForResult]
     *  @param resultCode code that is return from the finished execution of [startActivityForResult]
     *  @param data information received from the finished execution of [startActivityForResult]
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == GET_NEW_COURSE_RESULT && resultCode == Activity.RESULT_OK && data != null) {
            GlobalScope.launch {
                DatabaseManager.insertTutorCourse(TutorCourses(data.getStringExtra("tutorId"),data.getStringExtra("courseName")))
            }
            recreate()
        }
    }
}