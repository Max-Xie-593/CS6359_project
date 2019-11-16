package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import andr.mentorapp.GetHelpController.tutorSessions

class NoExpertCheckedInGenTutorAvailState : TutorState {
    override fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course): Boolean {
        if (tutorUser != null){
            tutorSessions.add(Triple(tutorUser, studentUser, course))
            return true
        }
        return false
    }
}