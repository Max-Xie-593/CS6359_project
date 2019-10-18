package andr.mentorapp

import android.content.Context
import android.content.Intent
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
}
