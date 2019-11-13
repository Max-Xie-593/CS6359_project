package andr.mentorapp.Database

import andr.mentorapp.TutorCourses
import androidx.room.*

/**
 *  TutorCoursesDao class
 *
 *  Class for handling the data accession for TutorCourses from the database
 *
 *  @author Max Xie
 *  @date 11/9/2019
 */
@Dao
interface TutorCoursesDao {

    /**
     * Return list of all TutorCourses in database
     *
     * @return List<TutorCourses> all Courses in the db
     */
    @Query("SELECT * FROM tutor_courses")
    fun getAll() : List<TutorCourses>

    /**
     * Return list of all TutorCourses in database for Tutor with id 'tutorId'
     *
     * @param tutorId               id of tutor to get courses for
     * @return List<TutorCourses>   all courses for the tutorId in the db
     */
    @Query("SELECT * FROM tutor_courses WHERE tutorId IS :tutorId")
    fun findTutorCoursesByIdFromDB(tutorId : String) : List<TutorCourses>

    /**
     * Insert new TutorCourse into db
     *
     * @param course TutorCourse to add into the database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(course: TutorCourses)

    /**
     * delete TutorCourse from the db
     * @param tutorCourses TutorCourse to delete from the db
     */
    @Delete
    fun delete(tutorCourses: TutorCourses)
}