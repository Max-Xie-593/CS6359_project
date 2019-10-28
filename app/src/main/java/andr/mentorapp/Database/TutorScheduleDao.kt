package andr.mentorapp.Database

import andr.mentorapp.TutorSchedule
import androidx.room.*

/*
 * Class for handling the data accession for TutorSchedule form the database
 */
@Dao
interface TutorScheduleDao {
    // Return list of all TutorSchedules in database
    @Query("SELECT * FROM tutor_schedule")
    fun getAll() : List<TutorSchedule>

    // Return list of all TutorSchedules in database for Tutor with id 'tutorId'
    @Query("SELECT * FROM tutor_schedule WHERE tutorId IS :tutorId")
    fun findTutorSchedulesByIdFromdDB(tutorId : String) : List<TutorSchedule>

    // Insert new TutorSchedule into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(schedule: TutorSchedule)
}