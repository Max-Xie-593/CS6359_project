package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser
import andr.mentorapp.GetHelpController.studentQueue

class NoExpertCheckedInNoGenTutorAvailState : TutorState {
    override fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course) : Boolean {
        studentQueue.add(Pair(studentUser, course))
        return false
    }
}