package andr.mentorapp

import androidx.room.Entity

/**
 * Class model for the TutorSchedule entity in the database
 *
 * @author Courtney Erbes
 * @date 10/15/19
 */
@Entity(tableName = "tutor_schedule", primaryKeys = ["tutorId", "shiftStart", "shiftEnd"])
class TutorSchedule(
    var tutorId : String, // ID of the Tutor

    var day : String, // Day of the Tutor's shift

    var shiftStart : String, // Start time of the Tutor's shift in format "hh:mm:ss"

    var shiftEnd : String // End time of the Tutor's shift in format "hh:mm:ss"
)
