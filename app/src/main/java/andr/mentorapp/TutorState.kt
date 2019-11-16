package andr.mentorapp

import andr.mentorapp.Database.StudentUser
import andr.mentorapp.Database.TutorUser

/**
 * TutorState interface which declares a getHelp function to be implemented for each individual state
 *
 * @author Mugdha Gupta
 * @date 11/16/2019
 */
interface TutorState {
    /**
     * getHelp function template that will be overriden
     *
     * @param tutorUser         TutorUser? to match the student with
     * @param studentUser       StudentUser to match with the tutor off the queue with
     * @param course            Course that will be the subject of the turor session
     * @return Boolean
     */
    fun getHelp(tutorUser: TutorUser?, studentUser: StudentUser, course: Course) : Boolean
}