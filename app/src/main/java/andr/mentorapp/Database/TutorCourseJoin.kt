package andr.mentorapp.Database

import andr.mentorapp.Course
import andr.mentorapp.User
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * TutorCourseJoin Class
 *
 * Encapsulates info for the courses the tutor is allowed to teach in the app
 *
 * @author Nymisha
 * @date 11/9/19
 */
@Entity(tableName = "tutor_course_join", primaryKeys = arrayOf("tutorId","courseId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("tutorId")),
        ForeignKey(entity = Course::class,
            parentColumns = arrayOf("courseId"),
            childColumns = arrayOf("courseId"))
    )
)
data class TutorCourseJoin(
    val tutorId: String,
    val courseId: String
)