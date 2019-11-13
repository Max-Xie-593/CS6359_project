package andr.mentorapp.Database

import andr.mentorapp.Course
import androidx.room.*

@Dao
interface TutorCourseJoinDao {
    @Insert
    fun insert(tutorCourseJoin: TutorCourseJoin)

    @Query("""
           SELECT * FROM user 
           INNER JOIN tutor_course_join
           ON user.userId=tutor_course_join.tutorId
           WHERE tutor_course_join.courseId=:courseId AND userLevel IS $TUTOR_LEVEL
           """)
    fun getTutorsForCourse(courseId: String): List<TutorUser>

    @Query("""
           SELECT * FROM course
           INNER JOIN tutor_course_join
           ON course.courseId=tutor_course_join.courseId
           WHERE tutor_course_join.tutorId=:tutorId
           """)
    fun getCoursesForTutor(tutorId: String): List<Course>

    @Delete
    fun delete(tutorCourseJoin: TutorCourseJoin)

}