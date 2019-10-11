package andr.mentorapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

const val TAG = "MainActivityDebugTag"
const val ACTION_SIGN_IN_STUDENT = "andr.mentorapp.SIGNINSTUDENT"
const val ACTION_SIGN_IN_ADMIN = "andr.mentorapp.SIGNINADMIN"
const val ACTION_SIGN_IN_TUTOR = "andr.mentorapp.SIGNINTUTOR"
const val ACTION_CREATE_NEW_PROFILE = "andr.mentorapp.CREATENEWPROFILE"

const val USER_DOES_NOT_EXIST : Int = -1
const val STUDENT_LEVEL : Int = 0
const val TUTOR_LEVEL : Int = 1
const val ADMIN_LEVEL : Int = 2

const val GET_NEW_USER_NAME_RESULT : Int = 0

var userLevel = HashMap<String, Int>()
var userName = HashMap<String, String>()
var currNewUser : String = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var editUniqueId = findViewById(R.id.unique_id_input) as EditText
        val signInButton = findViewById(R.id.sign_in_button) as Button

        populateData()

        signInButton.setOnClickListener {
            val id = editUniqueId.text.toString()
            val currUserLevel : Int = getUserLevel(id)
            val currUserName : String = getUserName(id)

            when(currUserLevel){
                ADMIN_LEVEL -> {
                    intent = Intent(this, AdminActivity::class.java)
                    intent.setAction(ACTION_SIGN_IN_ADMIN)
                }
                TUTOR_LEVEL -> {
                    intent = Intent(this, TutorActivity::class.java)
                    intent.setAction(ACTION_SIGN_IN_TUTOR)
                }
                STUDENT_LEVEL -> {
                    intent = Intent(this, StudentActivity::class.java)
                    intent.setAction(ACTION_SIGN_IN_STUDENT)
                }
                USER_DOES_NOT_EXIST -> {
                    currNewUser = id
                    intent = Intent(this, NewStudentActivity::class.java)
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


    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        if(requestCode == GET_NEW_USER_NAME_RESULT && resultCode == Activity.RESULT_OK && currNewUser != "" && data != null){
            addStudent(currNewUser, data.getStringExtra("name"))
            currNewUser = ""
            findViewById<Button>(R.id.sign_in_button).callOnClick()
        }
    }
}

fun getUserLevel(id: String) : Int{
    return userLevel.getOrDefault(id, -1)
}

fun getUserName(id: String) : String{
    return userName.getOrDefault(id, "")
}

fun populateData(){
    addStudent("mugdha", "Mugdha")
    addTutor("courtney", "Courtney")
    addAdmin("nymisha", "Nymisha")
}


fun addStudent(id: String, name : String){
    if(userLevel.containsKey(id))
        return
    userLevel.put(id, STUDENT_LEVEL)
    userName.put(id, name)
}

fun addTutor(id: String, name : String){
    if(userLevel.containsKey(id))
        return
    userLevel.put(id, TUTOR_LEVEL)
    userName.put(id, name)
}

fun addAdmin(id: String, name : String){
    if(userLevel.containsKey(id))
        return
    userLevel.put(id, ADMIN_LEVEL)
    userName.put(id, name)
}


