package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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
        if(requestCode == GET_NEW_USER_NAME_RESULT && resultCode == Activity.RESULT_OK && data != null){
            GlobalScope.launch {
                db.userDao().insert(User(unique_id_input.text.toString(), data.getStringExtra("name"), STUDENT_LEVEL))
                sign_in_button.callOnClick()
            }
        }
    }
}