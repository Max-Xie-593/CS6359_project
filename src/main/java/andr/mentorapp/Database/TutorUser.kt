package andr.mentorapp.Database

import andr.mentorapp.TutorActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_SIGN_IN_TUTOR = "andr.mentorapp.SIGNINTUTOR"

/**
 * Encapsulates info for the Tutor using the app
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
class TutorUser(userId: String, userName: String) : User(userId, userName, TUTOR_LEVEL) {
    /**
     * Generates intent for users of type Tutor
     *
     * @param context   Context for the intent
     * @return Intent   used to update view
     */
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, TutorActivity::class.java)
        intent.action = ACTION_SIGN_IN_TUTOR
        intent.putExtra("id", userId)
        intent.putExtra("name", userName)
        return intent
    }
}
