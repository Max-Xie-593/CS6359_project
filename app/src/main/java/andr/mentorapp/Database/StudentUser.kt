package andr.mentorapp.Database

import andr.mentorapp.StudentActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_SIGN_IN_STUDENT = "andr.mentorapp.SIGNINSTUDENT"

class StudentUser(userId: String, userName: String) : User(userId, userName, STUDENT_LEVEL) {
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, StudentActivity::class.java)
        intent.action = ACTION_SIGN_IN_STUDENT
        intent.putExtra("id", userId)
        intent.putExtra("name", userName)
        return intent
    }

}