package andr.mentorapp.Database


import andr.mentorapp.Course
import androidx.room.*

/**
 * Class for handling the data accession for Courses form the database
 *
 * @author Nymisha Jahagirdar
 * @date 11/06/19
 */
@Dao
interface CourseDao {
    /**
     * Return list of all Courses in database
     *
     * @return List<Course>  all courses in the db
     */
    @Query("SELECT * FROM course")
    fun getAll() : List<Course>


    /**
     * Insert new Course into db
     *
     * @param course  Course to insert into db
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(course: Course)
}
