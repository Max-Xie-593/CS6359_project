package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser

/**
 * class that will provide the tutorState and allow getHelpController to call getHelp
 *
 * @author Mugdha Gupta
 * @date 11/16/2019
 */
class TutorStateContext {
    private lateinit var tutorState : TutorState

    /**
     * constructor that will set an initial state
     */
    fun TutorStateContext() {
        tutorState = NoExpertCheckedInNoGenTutorAvailState()
    }

    /**
     * will set the state to the given TutorState
     *
     * @param newState         TutorState to match the student with
     * @return void
     */
    fun setState(newState: TutorState){
        tutorState = newState
    }

    /**
     * get Help function which will call the get help function for whatever TutorState we are
     * currently in
     *
     * @param tutorUser         TutorUser? to match the student with
     * @param studentUser       StudentUser to match with the tutor off the queue with
     * @param course            Course that will be the subject of the turor session
     * @return Boolean
     */
    fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course) : Boolean{
        return tutorState.getHelp(tutorUser, studentUser, course);
    }
}