package andr.mentorapp

import andr.mentorapp.Database.Schedule
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Class model for the TutorSchedule entity in the database
 *
 * @author Courtney Erbes
 * @date 10/15/19
 */
@Entity(tableName = "tutor_schedule",
    primaryKeys = ["tutorId", "day", "shiftStart", "shiftEnd"],
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("userId"),
            childColumns = arrayOf("tutorId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TutorSchedule(
    var tutorId : String, // ID of the Tutor

    @Embedded
    var schedule: Schedule
)
