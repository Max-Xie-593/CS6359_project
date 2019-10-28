package andr.mentorapp.Database

import andr.mentorapp.AdminActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_SIGN_IN_ADMIN = "andr.mentorapp.SIGNINADMIN"

class AdminUser(userId: String, userName: String) : User(userId, userName, ADMIN_LEVEL){
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, AdminActivity::class.java)
        intent.action = ACTION_SIGN_IN_ADMIN
        intent.putExtra("id", userId)
        intent.putExtra("name", userName)
        return intent
    }
}