package andr.mentorapp

import andr.mentorapp.GetHelpController.availableTutors
import andr.mentorapp.GetHelpController.checkedInTutors
import andr.mentorapp.GetHelpController.finishSession
import andr.mentorapp.GetHelpController.tutorSessions
import andr.mentorapp.Database.DatabaseManager
import andr.mentorapp.Database.TutorUser
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor.*

/**
 *  Tutor Home Page
 *
 *  This class creates the Tutor home screen after signing in as a Tutor to the app
 *
 *  @author Mugdha Gupta, Courtney Erbes
 *  @date 10/26/2019
 */
class TutorActivity : AppCompatActivity() {

    /**
     *  creates the tutor home page screen
     *
     *  @param savedInstanceState data most recently provided
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)
      
        // add sign out fragment to view
        if (savedInstanceState == null) {
            addFragment(activity_tutor_parent.id, SignOutFragment.newInstance())
        }

        var thisTutorID = intent.getStringExtra("id")

        var tutorUser = DatabaseManager.getUserById(thisTutorID) as TutorUser

        check_in_button.setVisibility(View.VISIBLE)
        check_out_button.setVisibility(View.GONE)

        for(checkedInTutor in checkedInTutors) {
            if (checkedInTutor.userId == tutorUser.userId) {
                tutorMessage.setText("Welcome to the Tutor page " + tutorUser.userName + "!")
                check_out_button.setVisibility(View.VISIBLE)
                check_in_button.setVisibility(View.GONE)
              
                for (session in tutorSessions) {
                    if (session.first.userId == tutorUser.userId) {
                        tutorMessage.setText("You're currently helping " + session.second.userName + " in " + session.third.courseName)
                        check_out_button.setVisibility(View.GONE)
                        tutorDoneButton.setVisibility(View.VISIBLE)
                        break
                    }
                }

            }
        }

        check_in_button.setOnClickListener {
            this.checkInTutor(tutorUser)
        }

        check_out_button.setOnClickListener {
            this.checkOutTutor(tutorUser)
        }

        tutorDoneButton.setOnClickListener{
            this.finishTutorSession(tutorUser)
        }
      
        view_schedule_button.setOnClickListener {
            var vsIntent = ViewScheduleIntent(TutorViewScheduleStrategy())
            startActivity(vsIntent.generateIntent(this, intent))
        }

    }

    /**
     *  checks in tutor and updates view
     *
     *  @param tutorUser    TutorUser checking in
     */
    fun checkInTutor(tutorUser: TutorUser) {
        tutorMessage.setText("You are now checked in, " + tutorUser.userName + "!")
        check_in_button.setVisibility(View.GONE)
        check_out_button.setVisibility(View.VISIBLE)

        addTutor(tutorUser)
        for (session in tutorSessions) {
            if (session.first.userId == tutorUser.userId) {
                tutorMessage.setText("You're currently helping " + session.second.userName + " in " + session.third.courseName)
                check_out_button.setVisibility(View.GONE)
                tutorDoneButton.setVisibility(View.VISIBLE)
                break
            }
        }
    }

    /**
     *  checks out tutor and updates view
     *
     *  @param tutorUser    TutorUser checking out
     */
    fun checkOutTutor(tutorUser: TutorUser) {
        for(availableTutor in availableTutors) {
            if (availableTutor.userId == tutorUser.userId) {
                removeTutor(availableTutor)
                check_in_button.setVisibility(View.VISIBLE)
                check_out_button.setVisibility(View.GONE)

                tutorMessage.setText("You are now checked out, " + tutorUser.userName + "!")
                break
            } else {
                tutorMessage.setText("Cannot checkout while helping someone")
            }
        }

    }
  
    /**
    * This is the function called when the "Done" button is clicked
    *
    * Calls the finishSession function from GetHelpController to end the session
    *
    * Check if the tutor has been matched with a new student off the queue
    *   and if so, stay on the "helping" view page
    * otherwise display the home view page again
    *
    * @param tutorUser    the object of the tutor using this page
    * @return void
    */
    fun finishTutorSession(tutorUser: TutorUser){
        finishSession(tutorUser)
        tutorMessage.setText("Welcome to the Tutor page " + tutorUser.userName + "!")
        tutorDoneButton.setVisibility(View.GONE)
        check_out_button.setVisibility(View.VISIBLE)

        for (session in tutorSessions) {
            if (session.first.userId == tutorUser.userId) {
                tutorMessage.setText("Thanks for helping, now you are tutoring " + session.second.userName + " in " + session.third.courseName)
                tutorDoneButton.setVisibility(View.VISIBLE)
                check_out_button.setVisibility(View.GONE)
            }
        }
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
        val expertCourses = DatabaseManager.getCoursesByTutorId(tutorUser.userId)
        for (course in expertCourses) {
            val tutorQueue = GetHelpController.availableExpertCourses.getOrDefault(course.courseId, HashSet())
            tutorQueue.add(tutorUser.userId)
            GetHelpController.availableExpertCourses.put(course.courseId, tutorQueue)
        }
        GetHelpController.updateQueue(tutorUser)
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
                val expertCourses = DatabaseManager.getCoursesByTutorId(availableTutor.userId)
                for (course in expertCourses) {
                    val tutorQueue = GetHelpController.availableExpertCourses.getOrDefault(course.courseId, HashSet())
                    tutorQueue.remove(tutorUser.userId)
                    GetHelpController.availableExpertCourses.put(course.courseId, tutorQueue)
                }
                return
            }
        }
    }
}
