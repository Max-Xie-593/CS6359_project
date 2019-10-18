package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.USER_DOES_NOT_EXIST
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "MainActivityDebugTag"
const val ACTION_SIGN_OUT = "andr.mentorapp.SIGNOUT"

const val GET_NEW_USER_NAME_RESULT : Int = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = MentorAppDatabase(this)

        sign_in_button.setOnClickListener {
            val context  = this
            GlobalScope.launch {
                val id = unique_id_input.text.toString()
                val user = db.userDao().findUserById(id)

                intent = user.getIntent(context)

                if(user.userLevel == USER_DOES_NOT_EXIST)
                    startActivityForResult(intent, GET_NEW_USER_NAME_RESULT)
                else
                    startActivity(intent)

            }
        }
    }


    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        val db = MentorAppDatabase(this)
        
        if(requestCode == GET_NEW_USER_NAME_RESULT && resultCode == Activity.RESULT_OK && data != null){
            GlobalScope.launch {
                db.userDao().insert(StudentUser(unique_id_input.text.toString(), data.getStringExtra("name")))
                sign_in_button.callOnClick()
            }
        }
    }
}