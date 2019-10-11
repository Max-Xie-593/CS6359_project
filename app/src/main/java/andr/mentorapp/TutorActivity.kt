package andr.mentorapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView


class TutorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor)

        var textView = findViewById(R.id.tutorMessage) as TextView
        textView.setText("Welcome to the Tutor page " + intent.getStringExtra("name") + "!")

    }

}
