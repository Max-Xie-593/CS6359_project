package andr.mentorapp.Database

import andr.mentorapp.NewStudentActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_CREATE_NEW_PROFILE = "andr.mentorapp.CREATENEWPROFILE"

/**
 * Encapsulates info for a new user using the app
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
class NewUser(userId: String) : User(userId, "", USER_DOES_NOT_EXIST){
    /**
     * Generates intent for users of type NewUser
     *
     * @param context   Context for the intent
     * @return Intent   used to update view
     */
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, NewStudentActivity::class.java)
        intent.action = ACTION_CREATE_NEW_PROFILE
        return intent
    }
}
