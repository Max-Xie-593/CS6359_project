package andr.mentorapp


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_user.*

/**
 *  Add User Page
 *
 *  This class creates the add user screen to allow the admin to add a new tutor to the database
 *
 *  @author Max Xie
 *  @date 10/26/2019
 */
class AddUserActivity : AppCompatActivity() {

    /**
     *  creates the add user page
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user) // set the layout to the particular xml file

        add_user_button.setOnClickListener {
            val newIntent: Intent = Intent() // create new request
            newIntent.putExtra("id", unique_id_input.text.toString()) // add data entered in this screen
            newIntent.putExtra("name", user_name_input.text.toString()) // add data enter in this screen
            setResult(Activity.RESULT_OK,newIntent) // set result
            finish() // exit the screen
        }

    }

}