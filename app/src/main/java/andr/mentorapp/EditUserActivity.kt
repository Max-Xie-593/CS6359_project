package andr.mentorapp

import andr.mentorapp.Database.ADMIN_LEVEL
import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.TUTOR_LEVEL
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_user.*

/**
 *  A Edit User Screen
 *
 *  This class creates a edit user screen to let the admin edit attributes of tutors
 *
 *  @author Max Xie
 *  @date 10/26/2019
 */
class EditUserActivity : AppCompatActivity() {

    var database_level: Int = TUTOR_LEVEL

    /**
     *  creates the edit screen and holds data to change a attribute of the tutor
     *
     *  @param savedInstanceState data brought from previous request
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user) // sets the layout to the particular xml file

        this.database_level = intent.getIntExtra("level", TUTOR_LEVEL)
        val intent = getIntent() // retrieve the data from the preceding request
        val newIntent: Intent = Intent() // create new request

        edit_user_button.setOnClickListener {
            newIntent.putExtra("id", intent.getStringExtra("id")) // add data from the previous request
            DatabaseManager.updateUser(intent.getStringExtra("id"),new_user_name_input.text.toString())
            setResult(Activity.RESULT_OK,newIntent) // set the result
            finish() // exit the screen
        }

        // Button's purpose is dependent on what type of user is being edited
        if (database_level == ADMIN_LEVEL) // Admin user do not have courses they teach, therefore button should not exist
            edit_course_button.visibility = View.GONE
        else
            edit_course_button.setOnClickListener {
                newIntent.setClass(this,EditCourseActivity::class.java) // set intent to courses List
                newIntent.putExtra("tutorId", intent.getStringExtra("id")) // send id of tutor being edited
                startActivity(newIntent) // start screen to view courses tutor is allowed to teach
            }
    }
}
