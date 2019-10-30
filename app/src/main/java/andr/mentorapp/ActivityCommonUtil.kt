package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.collections.HashSet
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*

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
object ActivityCommonUtil{
    var checkedInTutors = HashSet<TutorUser>() // set of checked in tutors
    var availableTutors = ConcurrentLinkedQueue<TutorUser>() // queue of available tutors
    var studentQueue: ConcurrentLinkedQueue<StudentUser> = ConcurrentLinkedQueue<StudentUser>() // queue of waiting students
    var tutorSessions = HashMap<TutorUser, StudentUser>() // active tutor sessions

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
     * Match student to available tutor if possible
     *
     * @param student       StudentUser to match with Tutor
     * @return Boolean      true if success, else false
     */
    fun matchStudentTutor(student: StudentUser) : Boolean {
        val tutor = firstAvailableTutor()
        if (tutor !=null) {
            tutorSessions.put(tutor, student)
            return true;
        }
        else {
            studentQueue.add(student)
            return false;
        }

    }

    /**
     * End session between tutor and student
     *
     * @param tutor         TutorUser to end session for
     * @return void
     */
    fun finishSession(tutor: TutorUser){

        for ((matchTutor, _) in tutorSessions){
            if (matchTutor.userId == tutor.userId) {
                if (!studentQueue.isEmpty()) {
                    tutorSessions.put(matchTutor, studentQueue.poll())
                } else {
                    tutorSessions.remove(matchTutor)
                    availableTutors.add(matchTutor)
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
            if (waitingStudent.userId == student.userId) {
                studentQueue.remove(waitingStudent)
                return true
            }
        }
        return false
    }

    /**
     * Tutor is checking in and must be noted as checked in and available
     *
     * @param tutorUser       TutorUser to add
     * @return void
     */
    fun addTutor(tutorUser: TutorUser) {

        checkedInTutors.add(tutorUser)
        availableTutors.add(tutorUser)
    }

    /**
     * Tutor is checking out and must be noted as checked out and not available
     *
     * @param tutorUser       TutorUser to remove
     * @return void
     */
    fun removeTutor(tutorUser: TutorUser) {

        for(availableTutor in availableTutors) {
            if (availableTutor.userId == tutorUser.userId) {
                availableTutors.remove(availableTutor)
                checkedInTutors.remove(availableTutor)
            }
        }

    }
}
