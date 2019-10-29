package andr.mentorapp

import andr.mentorapp.ActivityMainUtil.addTutor
import andr.mentorapp.ActivityMainUtil.availableTutors
import andr.mentorapp.ActivityMainUtil.checkedInTutors
import andr.mentorapp.ActivityMainUtil.finishSession
import andr.mentorapp.ActivityMainUtil.removeTutor
import andr.mentorapp.ActivityMainUtil.tutorSessions
import andr.mentorapp.Database.TutorUser
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor.*


class TutorActivity : AppCompatActivity() {

    lateinit var thisTutorID : String
    lateinit var tutorUser : TutorUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)
      
        // add sign out fragment to view
        if (savedInstanceState == null) {
            addFragment(activity_tutor_parent.id, SignOutFragment.newInstance())
        }

        val db = MentorAppDatabase.invoke(this).userDao()

        thisTutorID = intent.getStringExtra("id")

        tutorUser = db.findUserById(thisTutorID) as TutorUser

        check_in_button.setVisibility(View.VISIBLE)
        check_out_button.setVisibility(View.GONE)

        for(checkedInTutor in checkedInTutors) {
            if (checkedInTutor.userId == tutorUser.userId) {
                tutorMessage.setText("Welcome to the Tutor page " + tutorUser.userName + "!")
                check_out_button.setVisibility(View.VISIBLE)
                check_in_button.setVisibility(View.GONE)
                for ((matchTutor, matchStudent) in tutorSessions) {

                    if (matchTutor.userId == tutorUser.userId) {
                        tutorMessage.setText("You're currently helping " + tutorSessions.get(matchTutor)!!.userName)
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
            intent.setClass(this, TutorScheduleActivity::class.java)
            intent.putExtra("tutorId", intent.getStringExtra("id"))
            intent.putExtra("tutorName", intent.getStringExtra("name"))
            startActivity(intent)
        }

    }

    fun checkInTutor(tutorUser: TutorUser) {
        tutorMessage.setText("You are now checked in, " + tutorUser.userName + "!")
        check_in_button.setVisibility(View.GONE)
        check_out_button.setVisibility(View.VISIBLE)

        addTutor(tutorUser)
    }

    fun checkOutTutor(tutorUser: TutorUser) {

        for(availableTutor in availableTutors) {
            if (availableTutor.userId == tutorUser.userId) {
                removeTutor(availableTutor)
                check_in_button.setVisibility(View.VISIBLE)
                check_out_button.setVisibility(View.GONE)

                tutorMessage.setText("You are now checked out, " + tutorUser.userName + "!")
            } else {
                tutorMessage.setText("Cannot checkout while helping someone")
            }
        }

    }
  
    /*
    * This is the function called when the "Done" button is clicked
    *
    * Calls the finishSession function from ActivityCommonUtil to end the session
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
        for ((matchTutor, matchStudent) in tutorSessions) {
            if (matchTutor.userId == tutorUser.userId) {
                tutorMessage.setText("Thanks for helping, now you are tutoring " + tutorSessions.get(tutorUser)!!.userName)
                tutorDoneButton.setVisibility(View.VISIBLE)
                check_out_button.setVisibility(View.GONE)
            }
        }
    }
}
