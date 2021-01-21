package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import andr.mentorapp.GetHelpController.tutorSessions

/**
 * TutorState when there is an expert for a given course checked in and available
 *
 * @author Mugdha Gupta
 * @date 11/16/2019
 */
class ExpertCheckedInAndAvailState : TutorState {
    /**
     * Overrides getHelp function to add tutor student course triple to the tutor sessions and returns true
     * if a new tutorSession was sucessfully made
     *
     * @param tutorUser         TutorUser? to match the student with
     * @param studentUser       StudentUser to match with the tutor off the queue with
     * @param course            Course that will be the subject of the turor session
     * @return Boolean
     */
    override fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course): Boolean {
        if(tutorUser != null){
            tutorSessions.add(Triple(tutorUser, studentUser, course))
            return true
        }
        return false
    }

}