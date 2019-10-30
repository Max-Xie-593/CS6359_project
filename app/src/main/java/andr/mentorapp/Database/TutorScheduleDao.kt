package andr.mentorapp.Database

import andr.mentorapp.TutorSchedule
import androidx.room.*

/**
 * Class for handling the data accession for TutorSchedule form the database
 *
 * @author Courtney Erbes
 * @date 10/13/19
 */
@Dao
interface TutorScheduleDao {
    /**
     * Return list of all TutorSchedules in database
     *
     * @return List<TutorSchedule>  all schedules in the db
     */
    @Query("SELECT * FROM tutor_schedule")
    fun getAll() : List<TutorSchedule>

    /**
     * Return list of all TutorSchedules in database for Tutor with id 'tutorId'
     *
     * @param tutorId               id of tutor to get schedules for
     * @return List<TutorSchedule>  all schedules for the tutorId in the db
     */
    @Query("SELECT * FROM tutor_schedule WHERE tutorId IS :tutorId")
    fun findTutorSchedulesByIdFromdDB(tutorId : String) : List<TutorSchedule>

    /**
     * Insert new TutorSchedule into db
     *
     * @param schedule  TutorSchedule to insert into db
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(schedule: TutorSchedule)
}
