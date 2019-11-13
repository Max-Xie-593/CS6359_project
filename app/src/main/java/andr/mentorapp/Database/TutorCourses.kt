package andr.mentorapp

import androidx.room.Entity

/**
 * TutorCourses Class
 *
 * Encapsulates info for the courses the tutor is allowed to teach in the app
 *
 * @author Max Xie
 * @date 11/9/19
 */
@Entity(tableName = "tutor_courses", primaryKeys = ["tutorId","course"])
open class TutorCourses (
    var tutorId : String, // ID of the Tutor

    var course: String // course Tutor can teach
)