package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser

class TutorStateContext {
    private lateinit var tutorState : TutorState

    fun TutorStateContext() {
        tutorState = NoExpertCheckedInNoGenTutorAvailState()
    }

    fun setState(newState: TutorState){
        tutorState = newState
    }

    fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course) : Boolean{
        return tutorState.getHelp(tutorUser, studentUser, course);
    }
}