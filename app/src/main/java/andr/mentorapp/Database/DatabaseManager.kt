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
         * @return List<TutorSchedule> all schedules retrieved from db
         */
        fun getAllTutorSchedules() : List<TutorSchedule> {
            return scheduleDao!!.getAll()
        }

        /**
         * Get all schedules associated with user id in db
         *
         * @return List<Schedule>     all schedules for user retrieved from db
         */
        fun getSchedulesByTutorId(id: String) : List<Schedule> {
            return scheduleDao!!.findTutorSchedulesByIdFromdDB(id)
        }

        /**
         * Add new TutorSchedule to db
         *
         * @param tutorId      String of tutor ID to add schedule for
         * @param schedule     Schedule to add for tutor
         */
        fun insertTutorSchedule(tutorId: String, schedule: Schedule) {
            var user = userDao!!.findUserByIdFromdDB(tutorId)
            if (user != null) {
                var tutorSchedule = TutorSchedule(tutorId, schedule)
                scheduleDao!!.insert(tutorSchedule)            }
        }

        /**
         * Get all TutorCourseJoin entries from the database
         *
         * @return List<TutorCoursesJoin> List of all the TutorCourses
         */
        fun getAllTutorCourseJoins() : List<TutorCourseJoin> {
            return tutorCourseJoinDao!!.getAll()
        }

        /**
         * Get all courses that the given Tutor is experienced in with user id in db
         *
         * @return List<Course>     all courses for tutoruser retrieved from db
         */
        fun getCoursesByTutorId(tutorId: String) : List<Course> {
            return tutorCourseJoinDao!!.getCoursesForTutor(tutorId)
        }


        /**
         * Get all tutors that are experienced in the given course by id in db
         *
         * @return List<TutorUser>     all tutors for course retrieved from db
         */
        fun getTutorsbyCourseId(courseId: String) : List<TutorUser> {
            return tutorCourseJoinDao!!.getTutorsForCourse(courseId)
        }

      

        /**
         * grab the course from the TutorCourseJoin Table using the given tutorId and courseId
         *
         * @param tutorId Id of tutor to grab in the Table
         * @param courseId id of course to grab in the Table
         * @return TutorCourseJoin Object that contains both tutorId and courseId
         */
        fun getCoursefromTutorCourseJoin(tutorId: String,courseId: String) : TutorCourseJoin {
            return tutorCourseJoinDao!!.findTutorCoursesByIdFromDB(tutorId, courseId)
        }

        /**
         * insert a new TutorCourseJoin the database using the tutorId and courseId
         *
         * @param tutorId id to use to create the tutorCourseJoin Object
         * @param courseId id to use to create the tutorCourseJoin Object
         */
        fun insertTutorCourseJoin(tutorId: String,courseId: String) {
            tutorCourseJoinDao!!.insert(TutorCourseJoin(tutorId,courseId))
        }

        /**
         * delete a TutorCourseJoin from the database
         *
         * @param course TutorCourseJoin to remove from the database
         */
        fun deleteTutorCourseJoin(course: TutorCourseJoin) {
            tutorCourseJoinDao!!.delete(course)
        }

        /**
         * gets all the courses from the Course Table
         * 
         * @return List<Course> List of all the Courses
         */
        fun getAllCourses() : List<Course> {
            return courseDao!!.getAll()
        }

        /**
         * Return course by Id
         *
         * @param id        String to find course by
         * @return Course     retrieved from db
         */
        fun getCourseById(id: String) : Course? {
            return courseDao!!.findCourseByIdFromDB(id)
        }

        /**
         * inserts a course into the Course Table
         *
         * @param course course to insert into the Course Table
         */
        fun insertCourse(course: Course) {
            courseDao!!.insert(course)
        }

        /**
         * deletes a course from the Course Table
         *
         * @param course Course to delete from the Course Table
         */
        fun deleteCourse(course: Course) {
            courseDao!!.delete(course)
        }

        /**
         * finds a course from the Course Table with the given courseID
         *
         * @param id    String id of course to find in Course Table
         * @return Course Object to return with the given id
         */
        fun getCoursebyId(id: String): Course {
            return courseDao!!.getCourseById(id)
        }

        /** Delete TutorSchedule from db
         *
         * @param tutorId      String of tutor ID to delete schedule for
         * @param schedule     TutorSchedule to delete from db
         */
        fun deleteTutorSchedule(tutorId: String, schedule: Schedule) {
            var tutorSchedule = TutorSchedule(tutorId, schedule)
            scheduleDao!!.delete(tutorSchedule)
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
