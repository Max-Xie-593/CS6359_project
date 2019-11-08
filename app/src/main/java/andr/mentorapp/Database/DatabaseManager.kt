package andr.mentorapp.Database

import andr.mentorapp.MentorAppDatabase
import andr.mentorapp.TutorSchedule
import andr.mentorapp.User
import andr.mentorapp.UserDao
import android.content.Context

/**
 * This class acts as a Bridge for the app to use the database
 *
 * @author Courtney Erbes
 * @date 11/05/19
 */
class DatabaseManager {
    companion object {
        private var userDao : UserDao? = null
        private var scheduleDao : TutorScheduleDao? = null

        /**
         * Initialize DatabaseManager singleton
         *
         * @param context       Context required to create the db
         */
        fun init(context: Context) {
            var db = MentorAppDatabase.invoke(context)
            userDao = db.userDao()
            scheduleDao = db.tutorScheduleDao()
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
    }
}
