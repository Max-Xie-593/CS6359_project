package andr.mentorapp.Database

import andr.mentorapp.StudentActivity
import andr.mentorapp.User
import android.content.Context
import android.content.Intent

const val ACTION_SIGN_IN_STUDENT = "andr.mentorapp.SIGNINSTUDENT"

/**
 * Encapsulates info for the Student using the app
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
class StudentUser(userId: String, userName: String) : User(userId, userName, STUDENT_LEVEL) {
    /**
     * Generates intent for users of type StudentUser
     *
     * @param context   Context for the intent
     * @return Intent   used to update view
     */
    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, StudentActivity::class.java)
        intent.action = ACTION_SIGN_IN_STUDENT
        intent.putExtra("id", userId)
        intent.putExtra("name", userName)
        return intent
    }
}
