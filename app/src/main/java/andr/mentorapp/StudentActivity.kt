package andr.mentorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_student.*
import android.view.View


class StudentActivity : AppCompatActivity() {

    var tutorid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        studentMessage.text = "Welcome to the Student page " + intent.getStringExtra("name") + "!"

        studentDone.setVisibility(View.GONE)

        // TODO: Implement comparison/compareTo function to find pair in constant time via HashSets.
        for (match in tutorSessions){
            if (match.studentUser == intent.getStringExtra("id")){
                studentMessage.setText("You're getting help from " + match.tutorUser)
                studentGetHelpButton.setVisibility(View.GONE)
                studentDone.setVisibility(View.VISIBLE)
                break
            }
        }


        studentGetHelpButton.setOnClickListener {
            this.matchStudentTutor(intent.getStringExtra("id"))
        }

        studentDone.setOnClickListener{
            this.finishSession();
        }
    }

    fun matchStudentTutor(studentid: String) {

        if (!availableTutors.isEmpty()) {
            for (obj in checkedInTutors) {
                tutorid = obj
                availableTutors.remove(obj)
                tutorSessions.add(StudentTutorMatch(studentid, tutorid))
                break
            }

            for (match in tutorSessions){
                if (match.studentUser == studentid){
                    studentMessage.setText("You're getting help from " + match.tutorUser)
                    break
                }

            }



        }
        else {
            studentQueue.add(studentid)
            studentMessage.setText("No tutors are available right now, you are in position " + studentQueue.size + " in the queue")
        }

        studentGetHelpButton.setVisibility(View.GONE)
        studentDone.setVisibility(View.VISIBLE)

    }

    fun finishSession(){
        studentDone.setVisibility(View.GONE)
        studentGetHelpButton.setVisibility(View.VISIBLE)
        studentMessage.setText("Welcome to the Student page " + intent.getStringExtra("name") + "!")
        for (match in tutorSessions){
            if (match.studentUser == intent.getStringExtra("id")){
                tutorSessions.remove(match)
                availableTutors.add(match.tutorUser)
                break
            }
        }

    }

}
