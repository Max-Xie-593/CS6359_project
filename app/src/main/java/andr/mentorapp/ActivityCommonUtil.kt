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

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}


object ActivityCommonUtil{
    var checkedInTutors = HashSet<TutorUser>()
    var availableTutors = ConcurrentLinkedQueue<TutorUser>()
    var studentQueue: ConcurrentLinkedQueue<StudentUser> = ConcurrentLinkedQueue<StudentUser>()
    var tutorSessions = HashMap<TutorUser, StudentUser>()


    fun firstAvailableForSubject(subjectID : String) : TutorUser? {
        val firstTutor = checkedInTutors.firstOrNull { it.userName == subjectID }
        if(firstTutor != null){
            checkedInTutors.remove(firstTutor)
        }
        return firstTutor
    }

    fun firstAvailableTutor() : TutorUser? {
        val firstTutor = availableTutors.firstOrNull()
        if(firstTutor != null){
            availableTutors.remove(firstTutor)
        }
        return firstTutor
    }

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

    fun finishSession(tutor: TutorUser){

        for ((matchTutor, matchStudent) in tutorSessions){
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

    fun leaveQueue(student: User) : Boolean{

        for(waitingStudent in studentQueue) {
            if (waitingStudent.userId == student.userId) {
                studentQueue.remove(waitingStudent)
                return true
            }
        }
        return false
    }

    fun addTutor(tutorUser: TutorUser) {

        checkedInTutors.add(tutorUser)
        availableTutors.add(tutorUser)
    }

    fun removeTutor(tutorUser: TutorUser) {

        for(availableTutor in availableTutors) {
            if (availableTutor.userId == tutorUser.userId) {
                availableTutors.remove(availableTutor)
                checkedInTutors.remove(availableTutor)
            }
        }

    }
}
