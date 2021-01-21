package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Begin and commit the transaction
 *
 * @param func      FragmentTransaction func to execute
 * @return void
 */
fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

/**
 * Add Fragment to activity
 *
 * @param frameId   Int id of item to add fragment as child to
 * @param fragment  Fragment to add
 * @return void
 */
fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

/**
 * Object for holding common functions used in mentorapp
 *
 * @author Nymisha Jahagirdar
 * @date 10/14/19
 */
object GetHelpController{
    var checkedInTutors = HashSet<TutorUser>() // set of checked in tutors
    var availableTutors = ConcurrentLinkedQueue<TutorUser>() // queue of available tutors
    var studentQueue = ConcurrentLinkedQueue<Pair<StudentUser, Course>>() // queue of waiting students with the course they're looking for
    var tutorSessions = HashSet<Triple<TutorUser, StudentUser, Course>>() // active tutor sessions
    var availableExpertCourses = HashMap<String, HashSet<String>>() //mapping from course to the expert tutors that are currently checked in

    /**
     * Retrieve the first available tutor
     *
     * @return TutorUser?   the found Tutor or null
     */
    fun firstAvailableTutor() : TutorUser? {
        val firstTutor = availableTutors.firstOrNull()
        if(firstTutor != null){
            availableTutors.remove(firstTutor)
        }
        return firstTutor
    }

    /**
     * Retrieve the first available tutor for a given course
     *
     * @return TutorUser?   the found Tutor or null
     */
    fun firstAvailableTutorForCourse(course: Course) : TutorUser? {
        val expertTutors = availableExpertCourses.get(course.courseId)
        if (!expertTutors.isNullOrEmpty()) {

            for (tutor in availableTutors) {
                if (expertTutors.contains(tutor.userId)) {
                    availableTutors.remove(tutor)
                    return tutor
                }

            }
        }

        return null

    }

    /**
     * Match student to available tutor if possible
     *
     * @param student       StudentUser to match with Tutor
     * @return Boolean      true if success, else false
     */
    fun matchStudentTutor(student: StudentUser, courseGiven: Course) : Boolean {
        var course = courseGiven
        val expertTutors = availableExpertCourses.get(course.courseId)
        val tutor : TutorUser?
        var tutorStateContext = TutorStateContext()

        if (expertTutors.isNullOrEmpty()) {
            tutor = firstAvailableTutor()
            course = Course("other", "General Help")
            if (tutor == null)
                tutorStateContext.setState(NoExpertCheckedInNoGenTutorAvailState())
            else
                tutorStateContext.setState(NoExpertCheckedInGenTutorAvailState())

        }
        else {
            tutor = firstAvailableTutorForCourse(course)
            if (tutor == null)
                tutorStateContext.setState(ExpertCheckedInButNotAvailState())
            else
                tutorStateContext.setState(ExpertCheckedInAndAvailState())
        }

        return tutorStateContext.getHelp(tutor, student, course)
    }

    /**
     * End session between tutor and student
     *
     * @param tutor         TutorUser to end session for
     * @return void
     */
    fun finishSession(tutor: TutorUser) {

        for (session in tutorSessions) {
            if (session.first.userId == tutor.userId) {
                availableTutors.add(session.first)
                tutorSessions.remove(session)
                break
            }
        }
        updateQueue(tutor)
    }

    /**
     * Updates the queue given the tutor that is now available
     *
     * @param tutor         TutorUser to match a student off the queue with
     * @return void
     */
    fun updateQueue(tutor: TutorUser){
        if (!studentQueue.isEmpty()) {
            for (waitingStudent in studentQueue) {
                if (waitingStudent.second.courseId == "other" || availableExpertCourses.get(waitingStudent.second.courseId)!!.contains(tutor.userId)) {
                    tutorSessions.add(Triple(tutor, waitingStudent.first, waitingStudent.second))
                    studentQueue.remove(waitingStudent)
                    for (availTutor in availableTutors){
                        if(availTutor.userId == tutor.userId){
                            availableTutors.remove(availTutor)
                            return
                        }
                    }
                    return
                }
            }
        }
    }

    /**
     * student leaves queue
     *
     * @param student       User that is leaving queue
     * @return Boolean      true if success, else false
     */
    fun leaveQueue(student: User) : Boolean{
        for(waitingStudent in studentQueue) {
            if (waitingStudent.first.userId == student.userId) {
                studentQueue.remove(waitingStudent)
                return true
            }
        }
        return false
    }
}
