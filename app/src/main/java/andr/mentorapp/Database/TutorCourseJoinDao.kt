package andr.mentorapp.Database

import andr.mentorapp.Course
import androidx.room.*

/**
 *  Class for handling the data accession for Courses from the database
 *
 *  @author Max Xie, Nymisha Jahagirdar
 *  @date 11/13/19
 */
@Dao
interface TutorCourseJoinDao {

    /**
     *  inserts new tutorCourseJoin into the Database
     *
     *  @param tutorCourseJoin tutorCourseJoin Object to add to Database
     */
    @Insert
    fun insert(tutorCourseJoin: TutorCourseJoin)

    /**
     *  retrieve all the tutorCourseJoin Objects from the Database
     */
    @Query("SELECT * FROM tutor_course_join")
    fun getAll() : List<TutorCourseJoin>

    /**
     *  retrieve all the tutors who can teach that specific course
     *
     *  @param courseId id to use to grab all the tutors who can teach that course
     */
    @Query("""
           SELECT * FROM user 
           INNER JOIN tutor_course_join
           ON user.userId=tutor_course_join.tutorId
           WHERE tutor_course_join.courseId=:courseId AND userLevel IS $TUTOR_LEVEL
           """)
    fun getTutorsForCourse(courseId: String): List<TutorUser>

    /**
     *  retrieve all the courses that the specific tutor can teach
     *
     *  @param tutorId id to use to grab all the courses that tutor can teach
     */
    @Query("""
           SELECT * FROM course
           INNER JOIN tutor_course_join
           ON course.courseId=tutor_course_join.courseId
           WHERE tutor_course_join.tutorId=:tutorId
           """)
    fun getCoursesForTutor(tutorId: String): List<Course>

    /**
     *  delete the tutorCourseJoin Object from the database
     *
     *  @param tutorCourseJoin Object to remove from the database
     */
    @Delete
    fun delete(tutorCourseJoin: TutorCourseJoin)

    /**
     *  find the specific tutorCourseJoin Object from the database with the given tutorId and courseId
     *
     *  @param tutorId id of tutor to grab in the Table
     *  @param courseId id of course to grab in the Table
     */
    @Query("SELECT * FROM tutor_course_join WHERE tutorId IS :tutorId AND courseId IS :courseId")
    fun findTutorCoursesByIdFromDB(tutorId : String, courseId: String) : TutorCourseJoin
}