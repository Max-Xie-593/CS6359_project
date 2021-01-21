package andr.mentorapp.Database

import andr.mentorapp.AdminActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_SIGN_IN_ADMIN = "andr.mentorapp.SIGNINADMIN"

/**
 * Encapsulates info for the Admin using the app
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
class AdminUser(userId: String, userName: String) : User(userId, userName, ADMIN_LEVEL){
    /**
     * Generates intent for users of type Admin
     *
     * @param context   Context for the intent
     * @return Intent   used to update view
     */
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, AdminActivity::class.java)
        intent.action = ACTION_SIGN_IN_ADMIN
        intent.putExtra("id", userId)
        intent.putExtra("name", userName)
        return intent
    }
}
