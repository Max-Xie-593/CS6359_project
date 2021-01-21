package andr.mentorapp

import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.USER_DOES_NOT_EXIST
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "MainActivityDebugTag"
const val ACTION_SIGN_OUT = "andr.mentorapp.SIGNOUT"

const val GET_NEW_USER_NAME_RESULT : Int = 0

/**
 * Main page/ launcher page for the app
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
class MainActivity : AppCompatActivity() {
    /**
     *  Creates the launcher screen
     *  @param savedInstanceState data brought from previous request
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DatabaseManager.init(this)

        sign_in_button.setOnClickListener {
            val context = this
            val id = unique_id_input.text.toString()
            val user = DatabaseManager.getUserById(id)

            intent = user.getIntent(context)

            if(user.userLevel == USER_DOES_NOT_EXIST)
                startActivityForResult(intent, GET_NEW_USER_NAME_RESULT)
            else
                startActivity(intent)
        }
    }

    /**
     *  Create new Student on result of generating intent
     *
     *  @param requestCode      Int code result for request
     *  @param resultCode       Int code result from the result
     *  @param data             Intent? generated to use to create new user
     */
    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        if(requestCode == GET_NEW_USER_NAME_RESULT && resultCode == Activity.RESULT_OK && data != null){
            DatabaseManager.insertUser(StudentUser(unique_id_input.text.toString(), data.getStringExtra("name")))
            sign_in_button.callOnClick()
        }
    }
}
