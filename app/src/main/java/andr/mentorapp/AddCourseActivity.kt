package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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

        val intent = getIntent() // retrieve the data from the preceding request
        val newIntent: Intent = Intent() // create new request

        add_new_course_button.setOnClickListener {
            newIntent.putExtra("tutorId",intent.getStringExtra("tutorId")) // add data from preceding request
            newIntent.putExtra("courseName",new_course_input.text.toString()) // add data entered in this screen
            setResult(Activity.RESULT_OK,newIntent) // set result
            finish() // exit the screen
        }
    }
}