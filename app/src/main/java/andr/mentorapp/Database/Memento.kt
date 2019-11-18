package andr.mentorapp.Database

import andr.mentorapp.User
import andr.mentorapp.Course

/**
 * This class acts as the "history" of the most recently deleted User from the Database.
 *
 * @author Pratik Bhusal
 * @date 11/14/19
 */
class Memento {
    data class RestoredMemento(val user: User, val courses: List<Course>?, val schedules: List<Schedule>?)

    var savedUser: User? = null
    var savedTutorCourses: List<Course>? = null
    var savedTutorSchedules: List<Schedule>? = null


    fun save(user: User) {
        save(user, null, null)
    }

    /**
     * Save the most recently deleted user and their associated information in the memento.
     *
     * @param user The user's general information.
     * @param tutorSchedules List of shifts that the user is associated with (eg: the working periods of a tutor)
     * @param tutorCourses List of courses that the user is associated with (eg: courses a tutor teaches)
     */
    fun save(user: User, tutorCourses: List<Course>? = null, tutorSchedules: List<Schedule>? = null) {
        savedUser = user
        savedTutorCourses = tutorCourses
        savedTutorSchedules = tutorSchedules
    }

    /**
     * Retrieve the most recently stored user info in the memento. It also resets the memento for
     * the next time information should.
     *
     * @return A RestoredMemento data class. It is a glorified tuple that returns the user info,
     * the user's associated courses, and the user's associated schedules.
     */
    fun restore() : RestoredMemento {
        val tempUser = savedUser!!
        val tempCourses = savedTutorCourses
        val tempSchedules = savedTutorSchedules

        clear()

        return RestoredMemento(tempUser, tempCourses, tempSchedules)

    }

    /**
     * Clears all the saved user information in the memento.
     */
    fun clear() {
        savedUser = null
        savedTutorCourses = null
        savedTutorSchedules = null
    }

}