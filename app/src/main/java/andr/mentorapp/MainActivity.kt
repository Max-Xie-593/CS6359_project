package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "MainActivityDebugTag"
const val ACTION_SIGN_IN_STUDENT = "andr.mentorapp.SIGNINSTUDENT"
const val ACTION_SIGN_IN_ADMIN = "andr.mentorapp.SIGNINADMIN"
const val ACTION_SIGN_IN_TUTOR = "andr.mentorapp.SIGNINTUTOR"
const val ACTION_CREATE_NEW_PROFILE = "andr.mentorapp.CREATENEWPROFILE"
const val ACTION_SIGN_OUT = "andr.mentorapp.SIGNOUT"

const val USER_DOES_NOT_EXIST : Int = -1
const val STUDENT_LEVEL : Int = 0
const val TUTOR_LEVEL : Int = 1
const val ADMIN_LEVEL : Int = 2

const val GET_NEW_USER_NAME_RESULT : Int = 0

var currNewUser : String = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var editUniqueId = findViewById(R.id.unique_id_input) as EditText
        val signInButton = findViewById(R.id.sign_in_button) as Button
        val db = MentorAppDatabase(this)

        signInButton.setOnClickListener {
            val context  = this
            GlobalScope.launch {
                val id = editUniqueId.text.toString()
                val user = db.userDao().findUserById(id)
                val currUserLevel : Int = user?.userLevel ?: USER_DOES_NOT_EXIST
                val currUserName : String = user?.userName ?: ""

                when(currUserLevel){
                    ADMIN_LEVEL -> {
                        intent = Intent(context, AdminActivity::class.java)
                        intent.setAction(ACTION_SIGN_IN_ADMIN)
                    }
                    TUTOR_LEVEL -> {
                        intent = Intent(context, TutorActivity::class.java)
                        intent.setAction(ACTION_SIGN_IN_TUTOR)
                    }
                    STUDENT_LEVEL -> {
                        intent = Intent(context, StudentActivity::class.java)
                        intent.setAction(ACTION_SIGN_IN_STUDENT)
                    }
                    USER_DOES_NOT_EXIST -> {
                        currNewUser = id
                        intent = Intent(context, NewStudentActivity::class.java)
                        intent.setAction(ACTION_CREATE_NEW_PROFILE)
                    }
                }

                if(intent != null && currUserLevel != USER_DOES_NOT_EXIST) {
                    intent.putExtra("name", currUserName)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }
                else if(intent != null){
                    startActivityForResult(intent, GET_NEW_USER_NAME_RESULT)
                }


            }

        }


    }


    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        val db = MentorAppDatabase(this)
        if(requestCode == GET_NEW_USER_NAME_RESULT && resultCode == Activity.RESULT_OK && currNewUser != "" && data != null){
            GlobalScope.launch {
                db.userDao().insert(User(currNewUser, data.getStringExtra("name"), STUDENT_LEVEL))
                currNewUser = ""
                findViewById<Button>(R.id.sign_in_button).callOnClick()
            }
        }
    }
}