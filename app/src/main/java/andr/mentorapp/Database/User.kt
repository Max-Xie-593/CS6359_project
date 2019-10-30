package andr.mentorapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.lang.Class
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
open class User(
    @PrimaryKey
    var userId : String,

    var userName : String,

    var userLevel : Int
){
    open fun getIntent(context: Context) : Intent? {return null}

    open fun setIntent(context: Context, nameClass: Class<EditUserActivity>) : Intent {
        val intent = Intent()
        intent.setClass(context, nameClass)
        return intent
    }
}
