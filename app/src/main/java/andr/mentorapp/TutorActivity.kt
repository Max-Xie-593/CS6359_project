package andr.mentorapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutor.*

var checkedInTutors = ArrayList<String>()

class TutorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        tutorMessage.setText("Welcome to the Tutor page " + intent.getStringExtra("name") + "!")

        if (checkedInTutors.contains(intent.getStringExtra("id"))) {
            check_out_button.setVisibility(View.VISIBLE)
            check_in_button.setVisibility(View.GONE)
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

    }

    fun checkInTutor(id: String) {
        tutorMessage.setText("You are now checked in, " + intent.getStringExtra("name") + "!")

        check_in_button.setVisibility(View.GONE)
        check_out_button.setVisibility(View.VISIBLE)

        checkedInTutors.add(id)
    }

    fun checkOutTutor(id: String) {
        tutorMessage.setText("You are now checked out, " + intent.getStringExtra("name") + "!")

        check_in_button.setVisibility(View.VISIBLE)
        check_out_button.setVisibility(View.GONE)

        checkedInTutors.remove(id)
    }

}
