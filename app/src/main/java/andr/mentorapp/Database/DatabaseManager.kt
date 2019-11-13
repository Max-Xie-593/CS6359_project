package andr.mentorapp.Database

import andr.mentorapp.*
import android.content.Context

/**
 * This class acts as a Bridge for the app to use the database
 *
 * @author Courtney Erbes, Max Xie
 * @date 11/09/19
 */
class DatabaseManager {
    companion object {
        private var userDao : UserDao? = null
        private var scheduleDao : TutorScheduleDao? = null
        private var coursesDao : TutorCoursesDao? = null
        private var courseDao : CourseDao? = null
        private var tutorCourseJoinDao : TutorCourseJoinDao? = null

        /**
         * Initialize DatabaseManager singleton
         *
         * @param context       Context required to create the db
         */
        fun init(context: Context) {
            val db = MentorAppDatabase.invoke(context)
            userDao = db.userDao()
            scheduleDao = db.tutorScheduleDao()
            coursesDao = db.tutorCoursesDao()
            courseDao = db.courseDao()
            tutorCourseJoinDao = db.tutorCourseJoinDao()
        }

        /**
         * Return user by Id
         *
         * @param id        String to find user by
         * @return User     retrieved from db
         */
        fun getUserById(id: String) : User {
            return userDao!!.findUserById(id)
        }

        /**
         * Get all users in db
         *
         * @return List<User>     all users retrieved from db
         */
        fun getAllUsers() : List<User> {
            return userDao!!.getAll()
        }

        /**
         * Get all tutors in db
         *
         * @return List<User>     all tutors retrieved from db
         */
        fun getAllTutors() : List<User> {
            return userDao!!.getUsersByLevel(TUTOR_LEVEL)
        }

        /**
         * Get all admins in db
         *
         * @return List<User>     all admins retrieved from db
         */
        fun getAllAdmins() : List<User> {
            return userDao!!.getUsersByLevel(ADMIN_LEVEL)
        }

        /**
         * Get all students in db
         *
         * @return List<User>     all students retrieved from db
         */
        fun getAllStudents() : List<User> {
            return userDao!!.getUsersByLevel(STUDENT_LEVEL)
        }

        /**
         * Add new User to db
         *
         * @param user     User to add to db
         */
        fun insertUser(user: User) {
            userDao!!.insert(user)
        }

        /**
         * Update User in db
         *
         * @param id     String id of user to update
         * @param name   String of new name for user
         */
        fun updateUser(id: String, name: String) {
            userDao!!.update(id, name)
        }

        /**
         * Delete User from db
         *
         * @param user     User to delete from db
         */
        fun deleteUser(user: User) {
            userDao!!.delete(user)
        }

        /**
         * Get all tutor schedules in db
         *
         * @return List<TutorSchedule>     all schedules retrieved from db
         */
        fun getAllTutorSchedules() : List<TutorSchedule> {
            return scheduleDao!!.getAll()
        }

        /**
         * Get all schedules associated with user id in db
         *
         * @return List<TutorSchedules>     all schedules for user retrieved from db
         */
        fun getSchedulesByTutorId(id: String) : List<TutorSchedule> {
            return scheduleDao!!.findTutorSchedulesByIdFromdDB(id)
        }

        /**
         * Add new TutorSchedule to db
         *
         * @param schedule     TutorSchedule to add to db
         */
        fun insertTutorSchedule(schedule: TutorSchedule) {
            scheduleDao!!.insert(schedule)
        }

        /**

         * Get all TutorCourses from the database
         *
         * @return List<TutorCourses> List of all the TutorCourses
         */
        fun getAllTutorCourses() : List<TutorCourses> {
            return coursesDao!!.getAll()
        }

        /**
         * get all TutorCourses from the database with the given id
         *
         * @return List<TutorCourses> List of all the courses the tutor with the id can teach
         */
//        fun getCoursesByTutorId(id: String) : List<TutorCourses> {
//            return coursesDao!!.findTutorCoursesByIdFromDB(id)
//        }

        /**
         * get all TutorCourseJoins from the database with the given id
         *
         * @return List<TutorCourses> List of all the courses the tutor with the id can teach
         */
        fun getCoursesByTutorId(tutorId: String) : List<Course> {
            return tutorCourseJoinDao!!.getCoursesForTutor(tutorId)
        }

        /**
         * insert a new TutorCourse into the database
         *
         * @param course new TutorCourse to add into the database
         */
        fun insertTutorCourse(course: TutorCourses) {
            coursesDao!!.insert(course)
        }

        /**
         * delete a TutorCourse from the database
         *
         * @param courses TutorCourse to remove from the database
         */
        fun deleteCourse(courses: TutorCourses) {
            coursesDao!!.delete(courses)
        }

        fun getAllCourses() : List<Course> {
            return courseDao!!.getAll()
        }

        fun insertCourse(course: Course) {
            courseDao!!.insert(course)
        }

         * Delete TutorSchedule from db
         *
         * @param schedule     TutorSchedule to delete from db
         */
        fun deleteTutorSchedule(schedule: TutorSchedule) {
            scheduleDao!!.delete(schedule)
        }

        /**
         * Delete TutorSchedule from db given day, start time, and end time
         *
         * @param schedule     TutorSchedule to delete from db
         */
        fun deleteTutorSchedule(tutorId: String, day: String, start: String, end: String) {
            val schedule = scheduleDao!!.getSchedule(tutorId, day, start, end)
            scheduleDao!!.delete(schedule)

        }
    }
}
