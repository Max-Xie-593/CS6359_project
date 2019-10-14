package andr.mentorapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class StudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        var textView = findViewById(R.id.studentMessage) as TextView
        textView.setText("Welcome to the Student page " + intent.getStringExtra("name") + "!")
    }

}
