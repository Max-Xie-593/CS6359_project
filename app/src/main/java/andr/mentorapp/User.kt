package andr.mentorapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var userId : String,

    var userName : String,

    var userLevel : Int
)