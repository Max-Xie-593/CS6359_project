package andr.mentorapp

import androidx.room.Entity
import androidx.room.Index


@Entity(tableName = "course", primaryKeys = ["courseId"],
    indices = [Index("courseId")])
class Course(
    var courseId : String, // ID of the Course

    var courseName : String // Name of the Course

)
