package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
    /**
     *  creates the edit screen and holds data to change a attribute of the tutor
     *
     *  @param savedInstanceState data brought from previous request
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user) // sets the layout to the particular xml file

        edit_user_button.setOnClickListener {
            val intent = getIntent() // retrieve the data from the preceding request
            val newIntent: Intent = Intent() // create new request
            newIntent.putExtra("id", intent.getStringExtra("id")) // add data from the previous request
            DatabaseManager.updateUser(intent.getStringExtra("id"),new_user_name_input.text.toString())
            setResult(Activity.RESULT_OK,newIntent) // set the result
            finish() // exit the screen
        }
    }
}
