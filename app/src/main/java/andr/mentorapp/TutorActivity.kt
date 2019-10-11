package andr.mentorapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

var checkedInTutors = ArrayList<String>()

class TutorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        var textView = findViewById(R.id.tutorMessage) as TextView
        textView.setText("Welcome to the Tutor page " + intent.getStringExtra("name") + "!")

        var checkInButton = findViewById(R.id.check_in_button) as Button

        checkInButton.setOnClickListener {
            if (checkedInTutors.contains(intent.getStringExtra("id"))) {
                textView.setText("You are now checked out, " + intent.getStringExtra("name") + "!")
                checkInButton.setText("Check in")
                this.checkOutTutor(intent.getStringExtra("id"))
            } else {
                textView.setText("You are now checked in, " + intent.getStringExtra("name") + "!")
                checkInButton.setText("Check out")
                this.checkInTutor(intent.getStringExtra("id"))
            }
        }

    }

    fun checkInTutor(id: String) {
        checkedInTutors.add(id)
    }

    fun checkOutTutor(id: String) {
        checkedInTutors.remove(id)
    }

}
