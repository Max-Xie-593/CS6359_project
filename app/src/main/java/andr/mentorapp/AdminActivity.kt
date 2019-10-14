package andr.mentorapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        var textView = findViewById(R.id.adminMessage) as TextView
        textView.setText("Welcome to the Admin page " + intent.getStringExtra("name") + "!")
    }

}
