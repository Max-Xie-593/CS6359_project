package andr.mentorapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor.*


class TutorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        tutorMessage.setText("Welcome to the Tutor page " + intent.getStringExtra("name") + "!")

        if (checkedInTutors.contains(intent.getStringExtra("id"))) {
            check_out_button.setVisibility(View.VISIBLE)
            check_in_button.setVisibility(View.GONE)
            for (match in tutorSessions){
                if (match.tutorUser == intent.getStringExtra("id")){
                    tutorMessage.setText("You're currently helping " + match.studentUser)
                    check_out_button.setVisibility(View.GONE)
                    tutorDoneButton.setVisibility(View.VISIBLE)
                    break
                }
            }
        } else {
            check_in_button.setVisibility(View.VISIBLE)
            check_out_button.setVisibility(View.GONE)
        }

        check_in_button.setOnClickListener {
            this.checkInTutor(intent.getStringExtra("id"))
        }

        check_out_button.setOnClickListener {
            this.checkOutTutor(intent.getStringExtra("id"))
        }

        tutorDoneButton.setOnClickListener{
            this.finishSession()

        }

    }

    fun checkInTutor(id: String) {
        tutorMessage.setText("You are now checked in, " + intent.getStringExtra("name") + "!")

        check_in_button.setVisibility(View.GONE)
        check_out_button.setVisibility(View.VISIBLE)

        addTutor(id)
    }

    fun checkOutTutor(id: String) {
        tutorMessage.setText("You are now checked out, " + intent.getStringExtra("name") + "!")

        check_in_button.setVisibility(View.VISIBLE)
        check_out_button.setVisibility(View.GONE)

        removeTutor(id)
    }

    fun finishSession(){

        for (match in tutorSessions){
            if (match.tutorUser == intent.getStringExtra("id")){
                tutorSessions.remove(match)
                break
            }
        }
        if (!studentQueue.isEmpty()){
            val studentid = studentQueue.poll()
            tutorSessions.add(StudentTutorMatch(studentid, intent.getStringExtra("id")))
            tutorMessage.setText("Thanks for helping, now you are tutoring " + studentid)
        }
        else {
            tutorMessage.setText("Welcome to the Tutor page " + intent.getStringExtra("name") + "!")
            availableTutors.add(intent.getStringExtra("id"))

            tutorDoneButton.setVisibility(View.GONE)
            check_out_button.setVisibility(View.VISIBLE)
        }

    }

    fun addTutor(id: String) {
        checkedInTutors.add(id)
        availableTutors.add(id)
    }

    fun removeTutor(id: String) {
        if (availableTutors.contains(id)){
            availableTutors.remove(id)
            checkedInTutors.remove(id)
        }
        else
            tutorMessage.setText("Cannot Checkout while helping a student")

    }

}
