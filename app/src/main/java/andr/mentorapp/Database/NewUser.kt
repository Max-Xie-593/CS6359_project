package andr.mentorapp.Database

import andr.mentorapp.NewStudentActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_CREATE_NEW_PROFILE = "andr.mentorapp.CREATENEWPROFILE"

class NewUser(userId: String) : User(userId, "", USER_DOES_NOT_EXIST){
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, NewStudentActivity::class.java)
        intent.setAction(ACTION_CREATE_NEW_PROFILE)
        return intent
    }
}