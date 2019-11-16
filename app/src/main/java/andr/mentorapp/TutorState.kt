package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser

interface TutorState {
    fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course) : Boolean
}