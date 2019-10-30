package andr.mentorapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.lang.Class
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Encapsulates info for the User using the app and is extended by other specific User types
 *
 * @author Mugdha Gupta
 * @date 9/28/19
 */
@Entity(tableName = "user")
open class User(
    @PrimaryKey
    var userId : String, // ID of the User

    var userName : String, // Name of the User

    var userLevel : Int // UserLevel of the User
){
    /**
     * Generates intent for users of type User, overridden by children of User
     *
     * @param context   Context for the intent
     * @return Intent?   used to update view
     */
    open fun getIntent(context: Context) : Intent? {return null}

    /**
     * Sets intent of user for what class to go to next
     *
     * @param context   Context for the intent
     * @param nameClass Class want to go to next
     * @return Intent?   used to update view
     */
    open fun setIntent(context: Context, nameClass: Class<EditUserActivity>) : Intent {
        val intent = Intent()
        intent.setClass(context, nameClass)
        return intent
    }
}
