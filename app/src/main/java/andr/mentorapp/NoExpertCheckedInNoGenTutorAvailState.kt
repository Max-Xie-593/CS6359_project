package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import andr.mentorapp.GetHelpController.studentQueue

/**
 * TutorState when there is not no expert for a given course checked in and no gerneral tutor either
 *
 * @author Mugdha Gupta
 * @date 11/16/2019
 */
class NoExpertCheckedInNoGenTutorAvailState : TutorState {
    /**
     * Overrides getHelp function to add student course pair to the waiting queue and returns false
     * since the student has not yet gotten help
     *
     * @param tutorUser         TutorUser? to match the student with
     * @param studentUser       StudentUser to match with the tutor off the queue with
     * @param course            Course that will be the subject of the turor session
     * @return Boolean
     */
    override fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course) : Boolean {
        studentQueue.add(Pair(studentUser, course))
        return false
    }
}