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

    @Query("SELECT * FROM course WHERE courseId IS :courseId")
    fun findCourseByIdFromDB(courseId : String) : Course?


    /**
     * Insert new Course into db
     *
     * @param course  Course to insert into db
     * @return void
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(course: Course)

    /**
     * Delete course from db
     *
     * @param course    Course to remove from db
     */
    @Delete
    fun delete(course: Course)

    /**
     * get Course from db using the given courseId
     *
     * @param courseId courseId to use
     */
    @Query("SELECT * from course WHERE courseId IS :courseId")
    fun getCourseById(courseId: String): Course

}
