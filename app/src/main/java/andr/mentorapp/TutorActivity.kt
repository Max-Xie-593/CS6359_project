package andr.mentorapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

var checkedInTutors = ArrayList<String>()

class TutorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        var textView = findViewById(R.id.tutorMessage) as TextView
        textView.setText("Welcome to the Tutor page BLAH " + intent.getStringExtra("name") + "!")

        var checkInButton = findViewById(R.id.check_in_button) as Button
        var checkOutButton = findViewById(R.id.check_out_button) as Button
        checkOutButton.setVisibility(View.GONE)

        checkInButton.setOnClickListener {
            this.checkInTutor(intent.getStringExtra("id"))
        }

        checkOutButton.setOnClickListener {
            this.checkOutTutor(intent.getStringExtra("id"))
        }

    }

    fun checkInTutor(id: String) {
        var checkInButton = findViewById(R.id.check_in_button) as Button
        var checkOutButton = findViewById(R.id.check_out_button) as Button
        var textView = findViewById(R.id.tutorMessage) as TextView

        textView.setText("You are now checked in, " + intent.getStringExtra("name") + "!")

        checkInButton.setVisibility(View.GONE)
        checkOutButton.setVisibility(View.VISIBLE)

        checkedInTutors.add(id)
    }

    fun checkOutTutor(id: String) {
        var checkInButton = findViewById(R.id.check_in_button) as Button
        var checkOutButton = findViewById(R.id.check_out_button) as Button
        var textView = findViewById(R.id.tutorMessage) as TextView

        textView.setText("You are now checked out, " + intent.getStringExtra("name") + "!")

        checkInButton.setVisibility(View.VISIBLE)
        checkOutButton.setVisibility(View.GONE)

        checkedInTutors.remove(id)
    }

}
